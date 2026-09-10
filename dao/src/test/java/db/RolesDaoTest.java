package db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pojos.Roles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RolesDaoTest {

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @InjectMocks
    private RolesDao rolesDao;

    private Roles testRole;

    @BeforeEach
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
        verify(session).merge(testRole);
    }

    @Test
    public void testGet() {
        // Arrange
        Integer roleId = 1;
        when(session.find(Roles.class, roleId)).thenReturn(testRole);

        // Act
        Roles result = rolesDao.get(roleId);

        // Assert
        assertNotNull(result);
        assertEquals(testRole, result);
        verify(session).find(Roles.class, roleId);
    }

    @Test
    public void testGet_NotFound_ReturnsNull() {
        // Arrange
        Integer roleId = 999;
        when(session.find(Roles.class, roleId)).thenReturn(null);

        // Act
        Roles result = rolesDao.get(roleId);

        // Assert
        assertNull(result);
        verify(session).find(Roles.class, roleId);
    }

    @Test
    public void testLoad() {
        // Arrange
        Integer roleId = 1;
        when(session.getReference(Roles.class, roleId)).thenReturn(testRole);

        // Act
        Roles result = rolesDao.load(roleId);

        // Assert
        assertNotNull(result);
        assertEquals(testRole, result);
        verify(session).getReference(Roles.class, roleId);
    }

    @Test
    public void testDelete() {
        // Act
        rolesDao.delete(testRole);

        // Assert
        verify(sessionFactory).getCurrentSession();
        verify(session).remove(testRole);
    }
}
