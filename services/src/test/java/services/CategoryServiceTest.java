package services;

import db.CategoryDao;
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
public class CategoryServiceTest {

    @Mock
    private CategoryDao categoryDao;

    @InjectMocks
    private CategoryService categoryService;

    private Category testCategory;

    @Before
    public void setUp() {
        testCategory = new Category();
        testCategory.setCategoryId(1);
        testCategory.setCategoryName("Technology");
    }

    @Test
    public void testSaveOrUpdate() {
        // Act
        categoryService.saveOrUpdate(testCategory);

        // Assert
        verify(categoryDao).saveOrUpdate(testCategory);
    }

    @Test
    public void testGet() {
        // Arrange
        Integer categoryId = 1;
        when(categoryDao.get(categoryId)).thenReturn(testCategory);

        // Act
        Category result = categoryService.get(categoryId);

        // Assert
        assertNotNull(result);
        assertEquals(testCategory, result);
        verify(categoryDao).get(categoryId);
    }

    @Test
    public void testGet_NotFound_ReturnsNull() {
        // Arrange
        Integer categoryId = 999;
        when(categoryDao.get(categoryId)).thenReturn(null);

        // Act
        Category result = categoryService.get(categoryId);

        // Assert
        assertNull(result);
        verify(categoryDao).get(categoryId);
    }

    @Test
    public void testDelete() {
        // Act
        categoryService.delete(testCategory);

        // Assert
        verify(categoryDao).delete(testCategory);
    }

    @Test
    public void testLoad() {
        // Arrange
        Integer categoryId = 1;
        when(categoryDao.load(categoryId)).thenReturn(testCategory);

        // Act
        Category result = categoryService.load(categoryId);

        // Assert
        assertNotNull(result);
        assertEquals(testCategory, result);
        verify(categoryDao).load(categoryId);
    }
}
