package db;

import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import pojos.Users;

import java.util.List;
@Repository
public class UsersDao extends BaseDao<Users> {

    private static final String FIND_BY_EMAIL_AND_PASS_HQL =
            "FROM Users u WHERE u.email = :email AND u.pass = :pass";

    /**
     * @param email - User's email, provided for logging in
     * @param pass  - User's pass, provided for logging in
     * @return true, if User's email and pass are valid for logging in;
     * otherwise returns false;
     */
    public boolean checkUser(String email, String pass) {
        TypedQuery<Users> query = entityManager.createQuery(FIND_BY_EMAIL_AND_PASS_HQL, Users.class);
        query.setParameter("email", email);
        query.setParameter("pass", pass);
        List<Users> results = query.getResultList();
        return !results.isEmpty();
    }

    /**
     * @param email - User's email, provided for logging in
     * @param pass  - User's pass, provided for logging in
     * @return Users entity
     */
    public Users getUser(String email, String pass) {
        TypedQuery<Users> query = entityManager.createQuery(FIND_BY_EMAIL_AND_PASS_HQL, Users.class);
        query.setParameter("email", email);
        query.setParameter("pass", pass);
        List<Users> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }
}
