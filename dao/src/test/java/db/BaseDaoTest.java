package db;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pojos.Category;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests BaseDao functionality through CategoryDao (generic type is preserved only in subclasses).
 */
@ExtendWith(MockitoExtension.class)
public class BaseDaoTest {

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    private CategoryDao categoryDao;

    private Category testCategory;

    @BeforeEach
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
        verify(session).merge(testCategory);
    }

    @Test
    public void testSaveOrUpdate_WithException() {
        // Arrange
        doThrow(new HibernateException("Test exception")).when(session).merge(testCategory);

        // Act
        categoryDao.saveOrUpdate(testCategory);

        // Assert - метод должен обработать исключение без проброса
        verify(session).merge(testCategory);
    }

    @Test
    public void testGet() {
        // Arrange
        Integer id = 1;
        when(session.find(Category.class, id)).thenReturn(testCategory);

        // Act
        Category result = categoryDao.get(id);

        // Assert
        assertNotNull(result);
        assertEquals(testCategory, result);
        verify(sessionFactory).getCurrentSession();
        verify(session).find(Category.class, id);
    }

    @Test
    public void testGet_NotFound_ReturnsNull() {
        // Arrange
        Integer id = 999;
        when(session.find(Category.class, id)).thenReturn(null);

        // Act
        Category result = categoryDao.get(id);

        // Assert
        assertNull(result);
        verify(session).find(Category.class, id);
    }

    @Test
    public void testGet_WithException() {
        // Arrange
        Integer id = 1;
        when(session.find(Category.class, id)).thenThrow(new HibernateException("Test exception"));

        // Act
        Category result = categoryDao.get(id);

        // Assert - метод должен обработать исключение и вернуть null
        assertNull(result);
        verify(session).find(Category.class, id);
    }

    @Test
    public void testLoad() {
        // Arrange
        Integer id = 1;
        when(session.getReference(Category.class, id)).thenReturn(testCategory);

        // Act
        Category result = categoryDao.load(id);

        // Assert
        assertNotNull(result);
        assertEquals(testCategory, result);
        verify(sessionFactory).getCurrentSession();
        verify(session).getReference(Category.class, id);
        verify(session).isDirty();
    }

    @Test
    public void testLoad_WithException() {
        // Arrange
        Integer id = 1;
        when(session.getReference(Category.class, id)).thenThrow(new HibernateException("Test exception"));

        // Act
        Category result = categoryDao.load(id);

        // Assert - метод должен обработать исключение и вернуть null
        assertNull(result);
        verify(session).getReference(Category.class, id);
    }

    @Test
    public void testDelete() {
        // Act
        categoryDao.delete(testCategory);

        // Assert
        verify(sessionFactory).getCurrentSession();
        verify(session).remove(testCategory);
    }

    @Test
    public void testDelete_WithException() {
        // Arrange
        doThrow(new HibernateException("Test exception")).when(session).remove(testCategory);

        // Act
        categoryDao.delete(testCategory);

        // Assert - метод должен обработать исключение без проброса
        verify(session).remove(testCategory);
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
