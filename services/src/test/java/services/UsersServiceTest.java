package services;

import db.UsersDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pojos.Users;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UsersServiceTest {

    @Mock
    private UsersDao usersDao;

    @InjectMocks
    private UsersService usersService;

    private Users testUser;

    @Before
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
}
