package pojos;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class CategoryTest {

    private Category category1;
    private Category category2;
    private Category category3;

    @Before
    public void setUp() {
        category1 = new Category();
        category1.setCategoryId(1);
        category1.setCategoryName("Technology");

        category2 = new Category();
        category2.setCategoryId(1);
        category2.setCategoryName("Technology");

        category3 = new Category();
        category3.setCategoryId(2);
        category3.setCategoryName("Science");
    }

    @Test
    public void testGettersAndSetters() {
        Category category = new Category();

        category.setCategoryId(10);
        assertEquals(10, category.getCategoryId());

        category.setCategoryName("Test Category");
        assertEquals("Test Category", category.getCategoryName());

        Set<News> newsSet = new HashSet<>();
        category.setNews(newsSet);
        assertEquals(newsSet, category.getNews());
    }

    @Test
    public void testEquals_SameObject() {
        assertTrue(category1.equals(category1));
    }

    @Test
    public void testEquals_EqualObjects() {
        assertTrue(category1.equals(category2));
    }

    @Test
    public void testEquals_DifferentObjects() {
        assertFalse(category1.equals(category3));
    }

    @Test
    public void testEquals_Null() {
        assertFalse(category1.equals(null));
    }

    @Test
    public void testEquals_DifferentClass() {
        assertFalse(category1.equals("Not a Category object"));
    }

    @Test
    public void testEquals_DifferentCategoryId() {
        Category category4 = new Category();
        category4.setCategoryId(999);
        category4.setCategoryName("Technology");

        assertFalse(category1.equals(category4));
    }

    @Test
    public void testEquals_DifferentCategoryName() {
        Category category4 = new Category();
        category4.setCategoryId(1);
        category4.setCategoryName("Different");

        assertFalse(category1.equals(category4));
    }

    @Test
    public void testHashCode() {
        assertEquals(category1.hashCode(), category2.hashCode());
        assertTrue(category1.hashCode() != category3.hashCode());
    }

    @Test
    public void testHashCode_Consistency() {
        int hashCode1 = category1.hashCode();
        int hashCode2 = category1.hashCode();
        assertEquals(hashCode1, hashCode2);
    }
}
