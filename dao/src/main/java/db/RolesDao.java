package db;

import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import pojos.Roles;

import java.util.List;

@Repository
public class RolesDao extends BaseDao<Roles> {

    /**
     * @param roleType - e.g. "admin" or "user"
     * @return the role, or null if no role of this type exists
     */
    public Roles findByType(String roleType) {
        TypedQuery<Roles> query = entityManager.createQuery(
                "FROM Roles r WHERE r.roleType = :roleType", Roles.class);
        query.setParameter("roleType", roleType);
        List<Roles> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }
}
