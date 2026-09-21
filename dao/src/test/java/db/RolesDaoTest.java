package db;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pojos.Roles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RolesDaoTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Roles> query;

    @InjectMocks
    private RolesDao rolesDao;

    private Roles testRole;

    @BeforeEach
    public void setUp() {
        testRole = new Roles();
        testRole.setRoleId(1);
        testRole.setRoleType("admin");
    }

    @Test
    public void testSaveOrUpdate() {
        // Act
        rolesDao.saveOrUpdate(testRole);

        // Assert
        verify(entityManager).merge(testRole);
    }

    @Test
    public void testGet() {
        // Arrange
        Integer roleId = 1;
        when(entityManager.find(Roles.class, roleId)).thenReturn(testRole);

        // Act
        Roles result = rolesDao.get(roleId);

        // Assert
        assertNotNull(result);
        assertEquals(testRole, result);
        verify(entityManager).find(Roles.class, roleId);
    }

    @Test
    public void testGet_NotFound_ReturnsNull() {
        // Arrange
        Integer roleId = 999;
        when(entityManager.find(Roles.class, roleId)).thenReturn(null);

        // Act
        Roles result = rolesDao.get(roleId);

        // Assert
        assertNull(result);
    }

    @Test
    public void testLoad() {
        // Arrange
        Integer roleId = 1;
        when(entityManager.getReference(Roles.class, roleId)).thenReturn(testRole);

        // Act
        Roles result = rolesDao.load(roleId);

        // Assert
        assertNotNull(result);
        assertEquals(testRole, result);
        verify(entityManager).getReference(Roles.class, roleId);
    }

    @Test
    public void testDelete() {
        // Arrange
        when(entityManager.contains(testRole)).thenReturn(true);

        // Act
        rolesDao.delete(testRole);

        // Assert
        verify(entityManager).remove(testRole);
    }

    @Test
    public void testFindByType_Found() {
        // Arrange
        when(entityManager.createQuery("FROM Roles r WHERE r.roleType = :roleType", Roles.class)).thenReturn(query);
        when(query.setParameter("roleType", "admin")).thenReturn(query);
        List<Roles> results = new ArrayList<>();
        results.add(testRole);
        when(query.getResultList()).thenReturn(results);

        // Act
        Roles result = rolesDao.findByType("admin");

        // Assert
        assertNotNull(result);
        assertEquals(testRole, result);
    }

    @Test
    public void testFindByType_NotFound_ReturnsNull() {
        // Arrange
        when(entityManager.createQuery("FROM Roles r WHERE r.roleType = :roleType", Roles.class)).thenReturn(query);
        when(query.setParameter("roleType", "missing")).thenReturn(query);
        when(query.getResultList()).thenReturn(new ArrayList<>());

        // Act
        Roles result = rolesDao.findByType("missing");

        // Assert
        assertNull(result);
    }
}
