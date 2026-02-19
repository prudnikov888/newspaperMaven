package db;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pojos.Users;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UsersDaoTest {

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @Mock
    private Criteria criteria;

    @InjectMocks
    private UsersDao usersDao;

    private Users testUser;

    @Before
    public void setUp() {
        testUser = new Users();
        testUser.setUserId(1);
        testUser.setEmail("test@example.com");
        testUser.setPass("password123");
        testUser.setName("Test");
        testUser.setSurname("User");

        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createCriteria(Users.class)).thenReturn(criteria);
    }

    @Test
    public void testCheckUser_ValidCredentials_ReturnsTrue() {
        // Arrange
        List<Users> usersList = new ArrayList<>();
        usersList.add(testUser);
        when(criteria.list()).thenReturn(usersList);

        // Act
        boolean result = usersDao.checkUser("test@example.com", "password123");

        // Assert
        assertTrue(result);
        verify(sessionFactory).getCurrentSession();
        verify(session).createCriteria(Users.class);
        verify(criteria).add(any(LogicalExpression.class));
        verify(criteria).list();
    }

    @Test
    public void testCheckUser_InvalidCredentials_ReturnsFalse() {
        // Arrange
        List<Users> emptyList = new ArrayList<>();
        when(criteria.list()).thenReturn(emptyList);

        // Act
        boolean result = usersDao.checkUser("wrong@example.com", "wrongpass");

        // Assert
        assertFalse(result);
        verify(criteria).list();
    }

    @Test
    public void testGetUser_ValidCredentials_ReturnsUser() {
        // Arrange
        when(criteria.uniqueResult()).thenReturn(testUser);

        // Act
        Users result = usersDao.getUser("test@example.com", "password123");

        // Assert
        assertNotNull(result);
        assertEquals(testUser, result);
        assertEquals("test@example.com", result.getEmail());
        assertEquals("password123", result.getPass());
        verify(criteria).uniqueResult();
    }

    @Test
    public void testGetUser_InvalidCredentials_ReturnsNull() {
        // Arrange
        when(criteria.uniqueResult()).thenReturn(null);

        // Act
        Users result = usersDao.getUser("wrong@example.com", "wrongpass");

        // Assert
        assertNull(result);
        verify(criteria).uniqueResult();
    }

    @Test
    public void testCheckUser_CriteriaSetup() {
        // Arrange
        List<Users> usersList = new ArrayList<>();
        usersList.add(testUser);
        when(criteria.list()).thenReturn(usersList);

        // Act
        usersDao.checkUser("test@example.com", "password123");

        // Assert
        verify(criteria).add(any(LogicalExpression.class));
    }
}
