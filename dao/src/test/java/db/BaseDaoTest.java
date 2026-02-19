package db;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pojos.Category;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Tests BaseDao functionality through CategoryDao (generic type is preserved only in subclasses).
 */
@RunWith(MockitoJUnitRunner.class)
public class BaseDaoTest {

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    private CategoryDao categoryDao;

    private Category testCategory;

    @Before
    public void setUp() {
        categoryDao = new CategoryDao(sessionFactory);
        testCategory = new Category();
        testCategory.setCategoryId(1);
        testCategory.setCategoryName("Test Category");

        when(sessionFactory.getCurrentSession()).thenReturn(session);
    }

    @Test
    public void testSaveOrUpdate() {
        // Act
        categoryDao.saveOrUpdate(testCategory);

        // Assert
        verify(sessionFactory).getCurrentSession();
        verify(session).saveOrUpdate(testCategory);
    }

    @Test
    public void testSaveOrUpdate_WithException() {
        // Arrange
        doThrow(new HibernateException("Test exception")).when(session).saveOrUpdate(testCategory);

        // Act
        categoryDao.saveOrUpdate(testCategory);

        // Assert - метод должен обработать исключение без проброса
        verify(session).saveOrUpdate(testCategory);
    }

    @Test
    public void testGet() {
        // Arrange
        Integer id = 1;
        when(session.get(Category.class, id)).thenReturn(testCategory);

        // Act
        Category result = categoryDao.get(id);

        // Assert
        assertNotNull(result);
        assertEquals(testCategory, result);
        verify(sessionFactory).getCurrentSession();
        verify(session).get(Category.class, id);
    }

    @Test
    public void testGet_NotFound_ReturnsNull() {
        // Arrange
        Integer id = 999;
        when(session.get(Category.class, id)).thenReturn(null);

        // Act
        Category result = categoryDao.get(id);

        // Assert
        assertNull(result);
        verify(session).get(Category.class, id);
    }

    @Test
    public void testGet_WithException() {
        // Arrange
        Integer id = 1;
        when(session.get(Category.class, id)).thenThrow(new HibernateException("Test exception"));

        // Act
        Category result = categoryDao.get(id);

        // Assert - метод должен обработать исключение и вернуть null
        assertNull(result);
        verify(session).get(Category.class, id);
    }

    @Test
    public void testLoad() {
        // Arrange
        Integer id = 1;
        when(session.load(Category.class, id)).thenReturn(testCategory);

        // Act
        Category result = categoryDao.load(id);

        // Assert
        assertNotNull(result);
        assertEquals(testCategory, result);
        verify(sessionFactory).getCurrentSession();
        verify(session).load(Category.class, id);
        verify(session).isDirty();
    }

    @Test
    public void testLoad_WithException() {
        // Arrange
        Integer id = 1;
        when(session.load(Category.class, id)).thenThrow(new HibernateException("Test exception"));

        // Act
        Category result = categoryDao.load(id);

        // Assert - метод должен обработать исключение и вернуть null
        assertNull(result);
        verify(session).load(Category.class, id);
    }

    @Test
    public void testDelete() {
        // Act
        categoryDao.delete(testCategory);

        // Assert
        verify(sessionFactory).getCurrentSession();
        verify(session).delete(testCategory);
    }

    @Test
    public void testDelete_WithException() {
        // Arrange
        doThrow(new HibernateException("Test exception")).when(session).delete(testCategory);

        // Act
        categoryDao.delete(testCategory);

        // Assert - метод должен обработать исключение без проброса
        verify(session).delete(testCategory);
    }

    @Test
    public void testCurrentSession() {
        // Act
        Session result = categoryDao.currentSession();

        // Assert
        assertNotNull(result);
        assertEquals(session, result);
        verify(sessionFactory).getCurrentSession();
    }
}
