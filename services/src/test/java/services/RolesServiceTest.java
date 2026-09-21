package services;

import db.RolesDao;
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
public class RolesServiceTest {

    @Mock
    private RolesDao rolesDao;

    @InjectMocks
    private RolesService rolesService;

    private Roles testRole;

    @BeforeEach
    public void setUp() {
        testRole = new Roles();
        testRole.setRoleId(1);
        testRole.setRoleType("admin");
    }

    @Test
    public void testSaveOrUpdate() {
        // Act
        rolesService.saveOrUpdate(testRole);

        // Assert
        verify(rolesDao).saveOrUpdate(testRole);
    }

    @Test
    public void testGet() {
        // Arrange
        Integer roleId = 1;
        when(rolesDao.get(roleId)).thenReturn(testRole);

        // Act
        Roles result = rolesService.get(roleId);

        // Assert
        assertNotNull(result);
        assertEquals(testRole, result);
        verify(rolesDao).get(roleId);
    }

    @Test
    public void testGet_NotFound_ReturnsNull() {
        // Arrange
        Integer roleId = 999;
        when(rolesDao.get(roleId)).thenReturn(null);

        // Act
        Roles result = rolesService.get(roleId);

        // Assert
        assertNull(result);
        verify(rolesDao).get(roleId);
    }

    @Test
    public void testDelete() {
        // Act
        rolesService.delete(testRole);

        // Assert
        verify(rolesDao).delete(testRole);
    }

    @Test
    public void testLoad() {
        // Arrange
        Integer roleId = 1;
        when(rolesDao.load(roleId)).thenReturn(testRole);

        // Act
        Roles result = rolesService.load(roleId);

        // Assert
        assertNotNull(result);
        assertEquals(testRole, result);
        verify(rolesDao).load(roleId);
    }

    @Test
    public void testFindByType() {
        // Arrange
        when(rolesDao.findByType("admin")).thenReturn(testRole);

        // Act
        Roles result = rolesService.findByType("admin");

        // Assert
        assertNotNull(result);
        assertEquals(testRole, result);
        verify(rolesDao).findByType("admin");
    }

    @Test
    public void testFindByType_NotFound_ReturnsNull() {
        // Arrange
        when(rolesDao.findByType("missing")).thenReturn(null);

        // Act
        Roles result = rolesService.findByType("missing");

        // Assert
        assertNull(result);
    }
}
