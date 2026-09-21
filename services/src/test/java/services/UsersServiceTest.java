package services;

import db.UsersDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pojos.Roles;
import pojos.Users;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsersServiceTest {

    @Mock
    private UsersDao usersDao;

    @Mock
    private RolesService rolesService;

    @InjectMocks
    private UsersService usersService;

    private Users testUser;

    @BeforeEach
    public void setUp() {
        testUser = new Users();
        testUser.setUserId(1);
        testUser.setEmail("test@example.com");
        testUser.setPass("password123");
        testUser.setName("Test");
        testUser.setSurname("User");
    }

    @Test
    public void testSaveOrUpdate() {
        // Act
        usersService.saveOrUpdate(testUser);

        // Assert
        verify(usersDao).saveOrUpdate(testUser);
    }

    @Test
    public void testGet() {
        // Arrange
        Integer userId = 1;
        when(usersDao.get(userId)).thenReturn(testUser);

        // Act
        Users result = usersService.get(userId);

        // Assert
        assertNotNull(result);
        assertEquals(testUser, result);
        verify(usersDao).get(userId);
    }

    @Test
    public void testGet_NotFound_ReturnsNull() {
        // Arrange
        Integer userId = 999;
        when(usersDao.get(userId)).thenReturn(null);

        // Act
        Users result = usersService.get(userId);

        // Assert
        assertNull(result);
        verify(usersDao).get(userId);
    }

    @Test
    public void testDelete() {
        // Act
        usersService.delete(testUser);

        // Assert
        verify(usersDao).delete(testUser);
    }

    @Test
    public void testLoad() {
        // Arrange
        Integer userId = 1;
        when(usersDao.load(userId)).thenReturn(testUser);

        // Act
        Users result = usersService.load(userId);

        // Assert
        assertNotNull(result);
        assertEquals(testUser, result);
        verify(usersDao).load(userId);
    }

    @Test
    public void testFindByEmail() {
        // Arrange
        when(usersDao.findByEmail("test@example.com")).thenReturn(testUser);

        // Act
        Users result = usersService.findByEmail("test@example.com");

        // Assert
        assertNotNull(result);
        assertEquals(testUser, result);
    }

    @Test
    public void testRegisterUser_NewEmail_CreatesAndAssignsRole() {
        // Arrange
        Roles userRole = new Roles();
        userRole.setRoleId(2);
        userRole.setRoleType("user");
        userRole.setUsers(new HashSet<>());
        when(usersDao.findByEmail(testUser.getEmail())).thenReturn(null);
        when(rolesService.findByType("user")).thenReturn(userRole);

        // Act
        Users result = usersService.registerUser(testUser, "user");

        // Assert
        assertNotNull(result);
        assertEquals(testUser, result);
        verify(usersDao).create(testUser);
        assertTrue(userRole.getUsers().contains(testUser));
    }

    @Test
    public void testRegisterUser_EmailTaken_ReturnsNull() {
        // Arrange
        when(usersDao.findByEmail(testUser.getEmail())).thenReturn(testUser);

        // Act
        Users result = usersService.registerUser(testUser, "user");

        // Assert
        assertNull(result);
        verify(usersDao, never()).create(any());
    }

    @Test
    public void testRegisterUser_RoleMissing_StillCreatesUser() {
        // Arrange
        when(usersDao.findByEmail(testUser.getEmail())).thenReturn(null);
        when(rolesService.findByType("user")).thenReturn(null);

        // Act
        Users result = usersService.registerUser(testUser, "user");

        // Assert
        assertNotNull(result);
        verify(usersDao).create(testUser);
    }
}
