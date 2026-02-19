package services;

import db.RolesDao;
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
public class RolesServiceTest {

    @Mock
    private RolesDao rolesDao;

    @InjectMocks
    private RolesService rolesService;

    private Roles testRole;

    @Before
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
}
