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

@ExtendWith(MockitoExtension.class)
public class CategoryDaoTest {

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private CategoryDao categoryDao;

    private Category testCategory;

    @BeforeEach
    public void setUp() {
        testCategory = new Category();
        testCategory.setCategoryId(1);
        testCategory.setCategoryName("Technology");
    }

    @Test
    public void testSaveOrUpdate() {
        // Act
        categoryDao.saveOrUpdate(testCategory);

        // Assert
        verify(entityManager).merge(testCategory);
    }

    @Test
    public void testGet() {
        // Arrange
        Integer categoryId = 1;
        when(entityManager.find(Category.class, categoryId)).thenReturn(testCategory);

        // Act
        Category result = categoryDao.get(categoryId);

        // Assert
        assertNotNull(result);
        assertEquals(testCategory, result);
        verify(entityManager).find(Category.class, categoryId);
    }

    @Test
    public void testGet_NotFound_ReturnsNull() {
        // Arrange
        Integer categoryId = 999;
        when(entityManager.find(Category.class, categoryId)).thenReturn(null);

        // Act
        Category result = categoryDao.get(categoryId);

        // Assert
        assertNull(result);
    }

    @Test
    public void testLoad() {
        // Arrange
        Integer categoryId = 1;
        when(entityManager.getReference(Category.class, categoryId)).thenReturn(testCategory);

        // Act
        Category result = categoryDao.load(categoryId);

        // Assert
        assertNotNull(result);
        assertEquals(testCategory, result);
        verify(entityManager).getReference(Category.class, categoryId);
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
}
