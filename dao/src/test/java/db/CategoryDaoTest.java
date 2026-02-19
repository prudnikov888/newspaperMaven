package db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pojos.Category;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CategoryDaoTest {

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @InjectMocks
    private CategoryDao categoryDao;

    private Category testCategory;

    @Before
    public void setUp() {
        testCategory = new Category();
        testCategory.setCategoryId(1);
        testCategory.setCategoryName("Technology");

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
    public void testGet() {
        // Arrange
        Integer categoryId = 1;
        when(session.get(Category.class, categoryId)).thenReturn(testCategory);

        // Act
        Category result = categoryDao.get(categoryId);

        // Assert
        assertNotNull(result);
        assertEquals(testCategory, result);
        verify(session).get(Category.class, categoryId);
    }

    @Test
    public void testGet_NotFound_ReturnsNull() {
        // Arrange
        Integer categoryId = 999;
        when(session.get(Category.class, categoryId)).thenReturn(null);

        // Act
        Category result = categoryDao.get(categoryId);

        // Assert
        assertNull(result);
        verify(session).get(Category.class, categoryId);
    }

    @Test
    public void testLoad() {
        // Arrange
        Integer categoryId = 1;
        when(session.load(Category.class, categoryId)).thenReturn(testCategory);

        // Act
        Category result = categoryDao.load(categoryId);

        // Assert
        assertNotNull(result);
        assertEquals(testCategory, result);
        verify(session).load(Category.class, categoryId);
    }

    @Test
    public void testDelete() {
        // Act
        categoryDao.delete(testCategory);

        // Assert
        verify(sessionFactory).getCurrentSession();
        verify(session).delete(testCategory);
    }
}
