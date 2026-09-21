package db;

import jakarta.persistence.EntityManager;
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
    private EntityManager entityManager;

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
    }

    @Test
    public void testSaveOrUpdate() {
        // Act
        permissionsDao.saveOrUpdate(testPermission);

        // Assert
        verify(entityManager).merge(testPermission);
    }

    @Test
    public void testGet() {
        // Arrange
        Integer permissionId = 1;
        when(entityManager.find(Permissions.class, permissionId)).thenReturn(testPermission);

        // Act
        Permissions result = permissionsDao.get(permissionId);

        // Assert
        assertNotNull(result);
        assertEquals(testPermission, result);
        verify(entityManager).find(Permissions.class, permissionId);
    }

    @Test
    public void testGet_NotFound_ReturnsNull() {
        // Arrange
        Integer permissionId = 999;
        when(entityManager.find(Permissions.class, permissionId)).thenReturn(null);

        // Act
        Permissions result = permissionsDao.get(permissionId);

        // Assert
        assertNull(result);
    }

    @Test
    public void testLoad() {
        // Arrange
        Integer permissionId = 1;
        when(entityManager.getReference(Permissions.class, permissionId)).thenReturn(testPermission);

        // Act
        Permissions result = permissionsDao.load(permissionId);

        // Assert
        assertNotNull(result);
        assertEquals(testPermission, result);
        verify(entityManager).getReference(Permissions.class, permissionId);
    }

    @Test
    public void testDelete() {
        // Arrange
        when(entityManager.contains(testPermission)).thenReturn(true);

        // Act
        permissionsDao.delete(testPermission);

        // Assert
        verify(entityManager).remove(testPermission);
    }
}
