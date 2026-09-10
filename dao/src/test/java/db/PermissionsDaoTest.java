package db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pojos.Permissions;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PermissionsDaoTest {

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @InjectMocks
    private PermissionsDao permissionsDao;

    private Permissions testPermission;

    @BeforeEach
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
        verify(session).merge(testPermission);
    }

    @Test
    public void testGet() {
        // Arrange
        Integer permissionId = 1;
        when(session.find(Permissions.class, permissionId)).thenReturn(testPermission);

        // Act
        Permissions result = permissionsDao.get(permissionId);

        // Assert
        assertNotNull(result);
        assertEquals(testPermission, result);
        verify(session).find(Permissions.class, permissionId);
    }

    @Test
    public void testGet_NotFound_ReturnsNull() {
        // Arrange
        Integer permissionId = 999;
        when(session.find(Permissions.class, permissionId)).thenReturn(null);

        // Act
        Permissions result = permissionsDao.get(permissionId);

        // Assert
        assertNull(result);
        verify(session).find(Permissions.class, permissionId);
    }

    @Test
    public void testLoad() {
        // Arrange
        Integer permissionId = 1;
        when(session.getReference(Permissions.class, permissionId)).thenReturn(testPermission);

        // Act
        Permissions result = permissionsDao.load(permissionId);

        // Assert
        assertNotNull(result);
        assertEquals(testPermission, result);
        verify(session).getReference(Permissions.class, permissionId);
    }

    @Test
    public void testDelete() {
        // Act
        permissionsDao.delete(testPermission);

        // Assert
        verify(sessionFactory).getCurrentSession();
        verify(session).remove(testPermission);
    }
}
