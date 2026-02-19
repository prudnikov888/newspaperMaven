package pojos;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PermissionsTest {

    private Permissions permission1;
    private Permissions permission2;
    private Permissions permission3;

    @Before
    public void setUp() {
        permission1 = new Permissions();
        permission1.setRoleId(1);
        permission1.setAddNews(true);
        permission1.setDeleteNews(true);
        permission1.setUpdateNews(true);
        permission1.setReadNews(true);

        permission2 = new Permissions();
        permission2.setRoleId(1);
        permission2.setAddNews(true);
        permission2.setDeleteNews(true);
        permission2.setUpdateNews(true);
        permission2.setReadNews(true);

        permission3 = new Permissions();
        permission3.setRoleId(2);
        permission3.setAddNews(false);
        permission3.setDeleteNews(false);
        permission3.setUpdateNews(false);
        permission3.setReadNews(false);
    }

    @Test
    public void testGettersAndSetters() {
        Permissions permission = new Permissions();

        permission.setRoleId(10);
        assertEquals(10, permission.getRoleId());

        permission.setAddNews(true);
        assertTrue(permission.isAddNews());

        permission.setDeleteNews(true);
        assertTrue(permission.isDeleteNews());

        permission.setUpdateNews(true);
        assertTrue(permission.isUpdateNews());

        permission.setReadNews(true);
        assertTrue(permission.isReadNews());

        Roles role = new Roles();
        role.setRoleId(1);
        role.setRoleType("admin");
        permission.setRole(role);
        assertEquals(role, permission.getRole());
    }

    @Test
    public void testBooleanGettersAndSetters() {
        Permissions permission = new Permissions();

        permission.setAddNews(false);
        assertFalse(permission.isAddNews());

        permission.setDeleteNews(false);
        assertFalse(permission.isDeleteNews());

        permission.setUpdateNews(false);
        assertFalse(permission.isUpdateNews());

        permission.setReadNews(false);
        assertFalse(permission.isReadNews());
    }

    @Test
    public void testEquals_SameObject() {
        assertTrue(permission1.equals(permission1));
    }

    @Test
    public void testEquals_EqualObjects() {
        assertTrue(permission1.equals(permission2));
    }

    @Test
    public void testEquals_DifferentObjects() {
        assertFalse(permission1.equals(permission3));
    }

    @Test
    public void testEquals_Null() {
        assertFalse(permission1.equals(null));
    }

    @Test
    public void testEquals_DifferentClass() {
        assertFalse(permission1.equals("Not a Permissions object"));
    }

    @Test
    public void testEquals_DifferentRoleId() {
        Permissions permission4 = new Permissions();
        permission4.setRoleId(999);
        permission4.setAddNews(true);
        permission4.setDeleteNews(true);
        permission4.setUpdateNews(true);
        permission4.setReadNews(true);

        assertFalse(permission1.equals(permission4));
    }

    @Test
    public void testEquals_DifferentAddNews() {
        Permissions permission4 = new Permissions();
        permission4.setRoleId(1);
        permission4.setAddNews(false);
        permission4.setDeleteNews(true);
        permission4.setUpdateNews(true);
        permission4.setReadNews(true);

        assertFalse(permission1.equals(permission4));
    }

    @Test
    public void testHashCode() {
        assertEquals(permission1.hashCode(), permission2.hashCode());
        assertTrue(permission1.hashCode() != permission3.hashCode());
    }

    @Test
    public void testHashCode_Consistency() {
        int hashCode1 = permission1.hashCode();
        int hashCode2 = permission1.hashCode();
        assertEquals(hashCode1, hashCode2);
    }
}
