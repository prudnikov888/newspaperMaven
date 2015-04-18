package db;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pojos.Users;

import java.util.List;
@Repository
public class UsersDao extends BaseDao<Users> {
    private static Logger log = Logger.getLogger(UsersDao.class);

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
        Criteria criteria = session.createCriteria(Users.class);
        Criterion first = Restrictions.eq("email", email);
        Criterion second = Restrictions.eq("pass", pass);
        LogicalExpression andExp = Restrictions.and(first, second);
        criteria.add(andExp);
        List<Users> results = criteria.list();
        if (results.size() > 0)
            return true;
        else
            return false;
    }

    /**
     * @param email - User's email, provided for logging in
     * @param pass  - User's pass, provided for logging in
     * @return Users entity
     */
    public Users getUser(String email, String pass) {

        Session session = currentSession();
        Criteria criteria = session.createCriteria(Users.class);
        Criterion first = Restrictions.eq("email", email);
        Criterion second = Restrictions.eq("pass", pass);
        LogicalExpression andExp = Restrictions.and(first, second);
        criteria.add(andExp);
        Users result = (Users) criteria.uniqueResult();
        return result;
    }
}
