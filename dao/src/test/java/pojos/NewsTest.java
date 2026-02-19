package pojos;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NewsTest {

    private News news1;
    private News news2;
    private News news3;
    private Category category;
    private Users user;

    @Before
    public void setUp() {
        category = new Category();
        category.setCategoryId(1);
        category.setCategoryName("Technology");

        user = new Users();
        user.setUserId(1);
        user.setName("Test");
        user.setSurname("User");
        user.setEmail("test@example.com");
        user.setPass("password");

        news1 = new News();
        news1.setNewsId(1);
        news1.setTitle("Test Title");
        news1.setTitle4ann("Test Annotation");
        news1.setAuthor("Test Author");
        news1.setPostDay("2025-01-01");
        news1.setMainText("Test Main Text");
        news1.setCategory(category);
        news1.setUser(user);

        news2 = new News();
        news2.setNewsId(1);
        news2.setTitle("Test Title");
        news2.setTitle4ann("Test Annotation");
        news2.setAuthor("Test Author");
        news2.setPostDay("2025-01-01");
        news2.setMainText("Test Main Text");

        news3 = new News();
        news3.setNewsId(2);
        news3.setTitle("Different Title");
        news3.setTitle4ann("Different Annotation");
        news3.setAuthor("Different Author");
        news3.setPostDay("2025-01-02");
        news3.setMainText("Different Main Text");
    }

    @Test
    public void testGettersAndSetters() {
        News news = new News();

        news.setNewsId(10);
        assertEquals(10, news.getNewsId());

        news.setTitle("Title");
        assertEquals("Title", news.getTitle());

        news.setTitle4ann("Annotation");
        assertEquals("Annotation", news.getTitle4ann());

        news.setAuthor("Author");
        assertEquals("Author", news.getAuthor());

        news.setPostDay("2025-01-01");
        assertEquals("2025-01-01", news.getPostDay());

        news.setMainText("Main Text");
        assertEquals("Main Text", news.getMainText());

        news.setCategory(category);
        assertEquals(category, news.getCategory());

        news.setUser(user);
        assertEquals(user, news.getUser());
    }

    @Test
    public void testEquals_SameObject() {
        assertTrue(news1.equals(news1));
    }

    @Test
    public void testEquals_EqualObjects() {
        assertTrue(news1.equals(news2));
    }

    @Test
    public void testEquals_DifferentObjects() {
        assertFalse(news1.equals(news3));
    }

    @Test
    public void testEquals_Null() {
        assertFalse(news1.equals(null));
    }

    @Test
    public void testEquals_DifferentClass() {
        assertFalse(news1.equals("Not a News object"));
    }

    @Test
    public void testEquals_DifferentNewsId() {
        News news4 = new News();
        news4.setNewsId(999);
        news4.setTitle("Test Title");
        news4.setTitle4ann("Test Annotation");
        news4.setAuthor("Test Author");
        news4.setPostDay("2025-01-01");
        news4.setMainText("Test Main Text");

        assertFalse(news1.equals(news4));
    }

    @Test
    public void testEquals_DifferentTitle() {
        News news4 = new News();
        news4.setNewsId(1);
        news4.setTitle("Different Title");
        news4.setTitle4ann("Test Annotation");
        news4.setAuthor("Test Author");
        news4.setPostDay("2025-01-01");
        news4.setMainText("Test Main Text");

        assertFalse(news1.equals(news4));
    }

    @Test
    public void testHashCode() {
        assertEquals(news1.hashCode(), news2.hashCode());
        assertTrue(news1.hashCode() != news3.hashCode());
    }

    @Test
    public void testHashCode_Consistency() {
        int hashCode1 = news1.hashCode();
        int hashCode2 = news1.hashCode();
        assertEquals(hashCode1, hashCode2);
    }
}
