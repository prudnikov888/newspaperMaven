package db;

import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import pojos.Users;

import java.util.List;
@Repository
public class UsersDao extends BaseDao<Users> {

    private static final String FIND_BY_EMAIL_HQL =
            "SELECT DISTINCT u FROM Users u LEFT JOIN FETCH u.roles WHERE u.email = :email";

    /**
     * @param email - user's email, used as the login identifier
     * @return the user (with roles eagerly fetched, needed for authentication/authorization),
     * or null if no user with this email exists
     */
    public Users findByEmail(String email) {
        TypedQuery<Users> query = entityManager.createQuery(FIND_BY_EMAIL_HQL, Users.class);
        query.setParameter("email", email);
        List<Users> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

    /**
     * Persists a brand-new user and populates its generated id, unlike
     * {@link #saveOrUpdate(Users)} (merge) whose result isn't captured.
     */
    public void create(Users user) {
        entityManager.persist(user);
    }
}
