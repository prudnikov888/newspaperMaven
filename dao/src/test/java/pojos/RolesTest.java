package pojos;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class RolesTest {

    private Roles role1;
    private Roles role2;
    private Roles role3;

    @Before
    public void setUp() {
        role1 = new Roles();
        role1.setRoleId(1);
        role1.setRoleType("admin");

        role2 = new Roles();
        role2.setRoleId(1);
        role2.setRoleType("admin");

        role3 = new Roles();
        role3.setRoleId(2);
        role3.setRoleType("user");
    }

    @Test
    public void testGettersAndSetters() {
        Roles role = new Roles();

        role.setRoleId(10);
        assertEquals(10, role.getRoleId());

        role.setRoleType("testRole");
        assertEquals("testRole", role.getRoleType());

        Set<Users> usersSet = new HashSet<>();
        role.setUsers(usersSet);
        assertEquals(usersSet, role.getUsers());

        Permissions permission = new Permissions();
        role.setPermission(permission);
        assertEquals(permission, role.getPermission());
    }

    @Test
    public void testEquals_SameObject() {
        assertTrue(role1.equals(role1));
    }

    @Test
    public void testEquals_EqualObjects() {
        assertTrue(role1.equals(role2));
    }

    @Test
    public void testEquals_DifferentObjects() {
        assertFalse(role1.equals(role3));
    }

    @Test
    public void testEquals_Null() {
        assertFalse(role1.equals(null));
    }

    @Test
    public void testEquals_DifferentClass() {
        assertFalse(role1.equals("Not a Roles object"));
    }

    @Test
    public void testEquals_DifferentRoleId() {
        Roles role4 = new Roles();
        role4.setRoleId(999);
        role4.setRoleType("admin");

        assertFalse(role1.equals(role4));
    }

    @Test
    public void testEquals_DifferentRoleType() {
        Roles role4 = new Roles();
        role4.setRoleId(1);
        role4.setRoleType("different");

        assertFalse(role1.equals(role4));
    }

    @Test
    public void testHashCode() {
        assertEquals(role1.hashCode(), role2.hashCode());
        assertTrue(role1.hashCode() != role3.hashCode());
    }

    @Test
    public void testHashCode_Consistency() {
        int hashCode1 = role1.hashCode();
        int hashCode2 = role1.hashCode();
        assertEquals(hashCode1, hashCode2);
    }
}
