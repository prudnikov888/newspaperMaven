package services;

import db.PermissionsDao;
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
public class PermissionsServiceTest {

    @Mock
    private PermissionsDao permissionsDao;

    @InjectMocks
    private PermissionsService permissionsService;

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
        permissionsService.saveOrUpdate(testPermission);

        // Assert
        verify(permissionsDao).saveOrUpdate(testPermission);
    }

    @Test
    public void testGet() {
        // Arrange
        Integer permissionId = 1;
        when(permissionsDao.get(permissionId)).thenReturn(testPermission);

        // Act
        Permissions result = permissionsService.get(permissionId);

        // Assert
        assertNotNull(result);
        assertEquals(testPermission, result);
        verify(permissionsDao).get(permissionId);
    }

    @Test
    public void testGet_NotFound_ReturnsNull() {
        // Arrange
        Integer permissionId = 999;
        when(permissionsDao.get(permissionId)).thenReturn(null);

        // Act
        Permissions result = permissionsService.get(permissionId);

        // Assert
        assertNull(result);
        verify(permissionsDao).get(permissionId);
    }

    @Test
    public void testDelete() {
        // Act
        permissionsService.delete(testPermission);

        // Assert
        verify(permissionsDao).delete(testPermission);
    }

    @Test
    public void testLoad() {
        // Arrange
        Integer permissionId = 1;
        when(permissionsDao.load(permissionId)).thenReturn(testPermission);

        // Act
        Permissions result = permissionsService.load(permissionId);

        // Assert
        assertNotNull(result);
        assertEquals(testPermission, result);
        verify(permissionsDao).load(permissionId);
    }
}
