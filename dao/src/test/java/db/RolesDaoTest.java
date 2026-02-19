package db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pojos.Roles;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RolesDaoTest {

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @InjectMocks
    private RolesDao rolesDao;

    private Roles testRole;

    @Before
    public void setUp() {
        testRole = new Roles();
        testRole.setRoleId(1);
        testRole.setRoleType("admin");

        when(sessionFactory.getCurrentSession()).thenReturn(session);
    }

    @Test
    public void testSaveOrUpdate() {
        // Act
        rolesDao.saveOrUpdate(testRole);

        // Assert
        verify(sessionFactory).getCurrentSession();
        verify(session).saveOrUpdate(testRole);
    }

    @Test
    public void testGet() {
        // Arrange
        Integer roleId = 1;
        when(session.get(Roles.class, roleId)).thenReturn(testRole);

        // Act
        Roles result = rolesDao.get(roleId);

        // Assert
        assertNotNull(result);
        assertEquals(testRole, result);
        verify(session).get(Roles.class, roleId);
    }

    @Test
    public void testGet_NotFound_ReturnsNull() {
        // Arrange
        Integer roleId = 999;
        when(session.get(Roles.class, roleId)).thenReturn(null);

        // Act
        Roles result = rolesDao.get(roleId);

        // Assert
        assertNull(result);
        verify(session).get(Roles.class, roleId);
    }

    @Test
    public void testLoad() {
        // Arrange
        Integer roleId = 1;
        when(session.load(Roles.class, roleId)).thenReturn(testRole);

        // Act
        Roles result = rolesDao.load(roleId);

        // Assert
        assertNotNull(result);
        assertEquals(testRole, result);
        verify(session).load(Roles.class, roleId);
    }

    @Test
    public void testDelete() {
        // Act
        rolesDao.delete(testRole);

        // Assert
        verify(sessionFactory).getCurrentSession();
        verify(session).delete(testRole);
    }
}
