package db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pojos.Permissions;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PermissionsDaoTest {

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @InjectMocks
    private PermissionsDao permissionsDao;

    private Permissions testPermission;

    @Before
    public void setUp() {
        testPermission = new Permissions();
        testPermission.setRoleId(1);
        testPermission.setAddNews(true);
        testPermission.setDeleteNews(true);
        testPermission.setUpdateNews(true);
        testPermission.setReadNews(true);

        when(sessionFactory.getCurrentSession()).thenReturn(session);
    }

    @Test
    public void testSaveOrUpdate() {
        // Act
        permissionsDao.saveOrUpdate(testPermission);

        // Assert
        verify(sessionFactory).getCurrentSession();
        verify(session).saveOrUpdate(testPermission);
    }

    @Test
    public void testGet() {
        // Arrange
        Integer permissionId = 1;
        when(session.get(Permissions.class, permissionId)).thenReturn(testPermission);

        // Act
        Permissions result = permissionsDao.get(permissionId);

        // Assert
        assertNotNull(result);
        assertEquals(testPermission, result);
        verify(session).get(Permissions.class, permissionId);
    }

    @Test
    public void testGet_NotFound_ReturnsNull() {
        // Arrange
        Integer permissionId = 999;
        when(session.get(Permissions.class, permissionId)).thenReturn(null);

        // Act
        Permissions result = permissionsDao.get(permissionId);

        // Assert
        assertNull(result);
        verify(session).get(Permissions.class, permissionId);
    }

    @Test
    public void testLoad() {
        // Arrange
        Integer permissionId = 1;
        when(session.load(Permissions.class, permissionId)).thenReturn(testPermission);

        // Act
        Permissions result = permissionsDao.load(permissionId);

        // Assert
        assertNotNull(result);
        assertEquals(testPermission, result);
        verify(session).load(Permissions.class, permissionId);
    }

    @Test
    public void testDelete() {
        // Act
        permissionsDao.delete(testPermission);

        // Assert
        verify(sessionFactory).getCurrentSession();
        verify(session).delete(testPermission);
    }
}
