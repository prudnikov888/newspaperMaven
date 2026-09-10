package db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pojos.Users;

import java.util.List;
@Repository
public class UsersDao extends BaseDao<Users> {
    private static final Logger log = LoggerFactory.getLogger(UsersDao.class);

    private static final String FIND_BY_EMAIL_AND_PASS_HQL =
            "FROM Users u WHERE u.email = :email AND u.pass = :pass";

    @Autowired
    public UsersDao(SessionFactory sessionFactory){
        super(sessionFactory);
    }

    /**
     * @param email - User's email, provided for logging in
     * @param pass  - User's pass, provided for logging in
     * @return true, if User's email and pass are valid for logging in;
     * otherwise returns false;
     */
    public boolean checkUser(String email, String pass) {
        Session session = currentSession();
        Query<Users> query = session.createQuery(FIND_BY_EMAIL_AND_PASS_HQL, Users.class);
        query.setParameter("email", email);
        query.setParameter("pass", pass);
        List<Users> results = query.list();
        return !results.isEmpty();
    }

    /**
     * @param email - User's email, provided for logging in
     * @param pass  - User's pass, provided for logging in
     * @return Users entity
     */
    public Users getUser(String email, String pass) {
        Session session = currentSession();
        Query<Users> query = session.createQuery(FIND_BY_EMAIL_AND_PASS_HQL, Users.class);
        query.setParameter("email", email);
        query.setParameter("pass", pass);
        return query.uniqueResult();
    }
}
