package db;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
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
    private EntityManager entityManager;

    @InjectMocks
    private CategoryDao categoryDao;

    private Category testCategory;

    @BeforeEach
    public void setUp() {
        testCategory = new Category();
        testCategory.setCategoryId(1);
        testCategory.setCategoryName("Test Category");
    }

    @Test
    public void testSaveOrUpdate() {
        // Act
        categoryDao.saveOrUpdate(testCategory);

        // Assert
        verify(entityManager).merge(testCategory);
    }

    @Test
    public void testSaveOrUpdate_WithException() {
        // Arrange
        doThrow(new RuntimeException("Test exception")).when(entityManager).merge(testCategory);

        // Act
        categoryDao.saveOrUpdate(testCategory);

        // Assert - метод должен обработать исключение без проброса
        verify(entityManager).merge(testCategory);
    }

    @Test
    public void testGet() {
        // Arrange
        Integer id = 1;
        when(entityManager.find(Category.class, id)).thenReturn(testCategory);

        // Act
        Category result = categoryDao.get(id);

        // Assert
        assertNotNull(result);
        assertEquals(testCategory, result);
        verify(entityManager).find(Category.class, id);
    }

    @Test
    public void testGet_NotFound_ReturnsNull() {
        // Arrange
        Integer id = 999;
        when(entityManager.find(Category.class, id)).thenReturn(null);

        // Act
        Category result = categoryDao.get(id);

        // Assert
        assertNull(result);
    }

    @Test
    public void testGet_WithException() {
        // Arrange
        Integer id = 1;
        when(entityManager.find(Category.class, id)).thenThrow(new RuntimeException("Test exception"));

        // Act
        Category result = categoryDao.get(id);

        // Assert - метод должен обработать исключение и вернуть null
        assertNull(result);
        verify(entityManager).find(Category.class, id);
    }

    @Test
    public void testLoad() {
        // Arrange
        Integer id = 1;
        when(entityManager.getReference(Category.class, id)).thenReturn(testCategory);

        // Act
        Category result = categoryDao.load(id);

        // Assert
        assertNotNull(result);
        assertEquals(testCategory, result);
        verify(entityManager).getReference(Category.class, id);
    }

    @Test
    public void testLoad_WithException() {
        // Arrange
        Integer id = 1;
        when(entityManager.getReference(Category.class, id)).thenThrow(new RuntimeException("Test exception"));

        // Act
        Category result = categoryDao.load(id);

        // Assert - метод должен обработать исключение и вернуть null
        assertNull(result);
        verify(entityManager).getReference(Category.class, id);
    }

    @Test
    public void testDelete() {
        // Arrange
        when(entityManager.contains(testCategory)).thenReturn(true);

        // Act
        categoryDao.delete(testCategory);

        // Assert
        verify(entityManager).remove(testCategory);
    }

    @Test
    public void testDelete_Detached_MergesBeforeRemove() {
        // Arrange
        Category managed = new Category();
        when(entityManager.contains(testCategory)).thenReturn(false);
        when(entityManager.merge(testCategory)).thenReturn(managed);

        // Act
        categoryDao.delete(testCategory);

        // Assert
        verify(entityManager).merge(testCategory);
        verify(entityManager).remove(managed);
    }

    @Test
    public void testDelete_WithException() {
        // Arrange
        when(entityManager.contains(testCategory)).thenReturn(true);
        doThrow(new RuntimeException("Test exception")).when(entityManager).remove(testCategory);

        // Act
        categoryDao.delete(testCategory);

        // Assert - метод должен обработать исключение без проброса
        verify(entityManager).remove(testCategory);
    }
}
