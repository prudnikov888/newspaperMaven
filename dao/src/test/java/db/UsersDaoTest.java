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

    private static final String FIND_BY_EMAIL_HQL =
            "SELECT DISTINCT u FROM Users u LEFT JOIN FETCH u.roles WHERE u.email = :email";

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

    @Test
    public void testFindByEmail_Found() {
        // Arrange
        when(entityManager.createQuery(FIND_BY_EMAIL_HQL, Users.class)).thenReturn(query);
        when(query.setParameter("email", "test@example.com")).thenReturn(query);
        List<Users> usersList = new ArrayList<>();
        usersList.add(testUser);
        when(query.getResultList()).thenReturn(usersList);

        // Act
        Users result = usersDao.findByEmail("test@example.com");

        // Assert
        assertNotNull(result);
        assertEquals(testUser, result);
        verify(query).setParameter("email", "test@example.com");
    }

    @Test
    public void testFindByEmail_NotFound_ReturnsNull() {
        // Arrange
        when(entityManager.createQuery(FIND_BY_EMAIL_HQL, Users.class)).thenReturn(query);
        when(query.setParameter("email", "missing@example.com")).thenReturn(query);
        when(query.getResultList()).thenReturn(new ArrayList<>());

        // Act
        Users result = usersDao.findByEmail("missing@example.com");

        // Assert
        assertNull(result);
    }

    @Test
    public void testCreate() {
        // Act
        usersDao.create(testUser);

        // Assert
        verify(entityManager).persist(testUser);
    }
}
