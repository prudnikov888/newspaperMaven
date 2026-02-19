package pojos;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class UsersTest {

    private Users user1;
    private Users user2;
    private Users user3;

    @Before
    public void setUp() {
        user1 = new Users();
        user1.setUserId(1);
        user1.setName("John");
        user1.setSurname("Doe");
        user1.setEmail("john@example.com");
        user1.setPass("password123");

        user2 = new Users();
        user2.setUserId(1);
        user2.setName("John");
        user2.setSurname("Doe");
        user2.setEmail("john@example.com");
        user2.setPass("password123");

        user3 = new Users();
        user3.setUserId(2);
        user3.setName("Jane");
        user3.setSurname("Smith");
        user3.setEmail("jane@example.com");
        user3.setPass("password456");
    }

    @Test
    public void testGettersAndSetters() {
        Users user = new Users();

        user.setUserId(10);
        assertEquals(10, user.getUserId());

        user.setName("Test Name");
        assertEquals("Test Name", user.getName());

        user.setSurname("Test Surname");
        assertEquals("Test Surname", user.getSurname());

        user.setEmail("test@example.com");
        assertEquals("test@example.com", user.getEmail());

        user.setPass("testpass");
        assertEquals("testpass", user.getPass());

        Set<News> newsSet = new HashSet<>();
        user.setNews(newsSet);
        assertEquals(newsSet, user.getNews());

        Set<Roles> rolesSet = new HashSet<>();
        user.setRoles(rolesSet);
        assertEquals(rolesSet, user.getRoles());
    }

    @Test
    public void testEquals_SameObject() {
        assertTrue(user1.equals(user1));
    }

    @Test
    public void testEquals_EqualObjects() {
        assertTrue(user1.equals(user2));
    }

    @Test
    public void testEquals_DifferentObjects() {
        assertFalse(user1.equals(user3));
    }

    @Test
    public void testEquals_Null() {
        assertFalse(user1.equals(null));
    }

    @Test
    public void testEquals_DifferentClass() {
        assertFalse(user1.equals("Not a Users object"));
    }

    @Test
    public void testEquals_DifferentUserId() {
        Users user4 = new Users();
        user4.setUserId(999);
        user4.setName("John");
        user4.setSurname("Doe");
        user4.setEmail("john@example.com");
        user4.setPass("password123");

        assertFalse(user1.equals(user4));
    }

    @Test
    public void testEquals_DifferentName() {
        Users user4 = new Users();
        user4.setUserId(1);
        user4.setName("Different");
        user4.setSurname("Doe");
        user4.setEmail("john@example.com");
        user4.setPass("password123");

        assertFalse(user1.equals(user4));
    }

    @Test
    public void testHashCode() {
        assertEquals(user1.hashCode(), user2.hashCode());
        assertTrue(user1.hashCode() != user3.hashCode());
    }

    @Test
    public void testHashCode_Consistency() {
        int hashCode1 = user1.hashCode();
        int hashCode2 = user1.hashCode();
        assertEquals(hashCode1, hashCode2);
    }
}
