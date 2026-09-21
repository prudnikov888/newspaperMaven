package db;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pojos.Users;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsersDaoTest {

    private static final String FIND_BY_EMAIL_AND_PASS_HQL =
            "FROM Users u WHERE u.email = :email AND u.pass = :pass";

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Users> query;

    @InjectMocks
    private UsersDao usersDao;

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

    private void stubQuery(String email, String pass) {
        when(entityManager.createQuery(FIND_BY_EMAIL_AND_PASS_HQL, Users.class)).thenReturn(query);
        when(query.setParameter("email", email)).thenReturn(query);
        when(query.setParameter("pass", pass)).thenReturn(query);
    }

    @Test
    public void testCheckUser_ValidCredentials_ReturnsTrue() {
        // Arrange
        stubQuery("test@example.com", "password123");
        List<Users> usersList = new ArrayList<>();
        usersList.add(testUser);
        when(query.getResultList()).thenReturn(usersList);

        // Act
        boolean result = usersDao.checkUser("test@example.com", "password123");

        // Assert
        assertTrue(result);
        verify(entityManager).createQuery(FIND_BY_EMAIL_AND_PASS_HQL, Users.class);
        verify(query).setParameter("email", "test@example.com");
        verify(query).setParameter("pass", "password123");
        verify(query).getResultList();
    }

    @Test
    public void testCheckUser_InvalidCredentials_ReturnsFalse() {
        // Arrange
        stubQuery("wrong@example.com", "wrongpass");
        when(query.getResultList()).thenReturn(new ArrayList<>());

        // Act
        boolean result = usersDao.checkUser("wrong@example.com", "wrongpass");

        // Assert
        assertFalse(result);
        verify(query).getResultList();
    }

    @Test
    public void testGetUser_ValidCredentials_ReturnsUser() {
        // Arrange
        stubQuery("test@example.com", "password123");
        List<Users> usersList = new ArrayList<>();
        usersList.add(testUser);
        when(query.getResultList()).thenReturn(usersList);

        // Act
        Users result = usersDao.getUser("test@example.com", "password123");

        // Assert
        assertNotNull(result);
        assertEquals(testUser, result);
        assertEquals("test@example.com", result.getEmail());
        assertEquals("password123", result.getPass());
        verify(query).getResultList();
    }

    @Test
    public void testGetUser_InvalidCredentials_ReturnsNull() {
        // Arrange
        stubQuery("wrong@example.com", "wrongpass");
        when(query.getResultList()).thenReturn(new ArrayList<>());

        // Act
        Users result = usersDao.getUser("wrong@example.com", "wrongpass");

        // Assert
        assertNull(result);
        verify(query).getResultList();
    }

    @Test
    public void testCheckUser_QuerySetup() {
        // Arrange
        stubQuery("test@example.com", "password123");
        List<Users> usersList = new ArrayList<>();
        usersList.add(testUser);
        when(query.getResultList()).thenReturn(usersList);

        // Act
        usersDao.checkUser("test@example.com", "password123");

        // Assert
        verify(query).setParameter("email", "test@example.com");
        verify(query).setParameter("pass", "password123");
    }
}
