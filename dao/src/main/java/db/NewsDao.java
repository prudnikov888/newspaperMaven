package db;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pojos.News;

import java.util.List;
@Repository
public class NewsDao extends BaseDao<News> {
    private static Logger log = Logger.getLogger(NewsDao.class);

    @Autowired
    public NewsDao(SessionFactory sessionFactory){
        super(sessionFactory);
    }
    /**
     *
     * @param selectedPage - number of page that has been selected by the user
     * @param newsOnPage - number of news on the page
     * @return List of news that would be displayed for the user
     */
    public List<News> getNewsList(int selectedPage, int newsOnPage, String sortBy) {
        Session session = currentSession();
        String hql;
        if (sortBy.equals("postDay")) {
            hql = "SELECT N FROM News N ORDER BY N.postDay DESC";
        }
        else
            hql = "SELECT N FROM News N ORDER BY N.category.categoryName ASC";
        Query query = session.createQuery(hql);
        query.setFirstResult((selectedPage - 1)*newsOnPage);
        query.setMaxResults(newsOnPage);
        List <News> results = query.list();
        return results;
    }
    /**
     *
     * @return number of all news in the database
     */
    public int countAllNews() {
        Session session = currentSession();
        String hql = "FROM News";
        Query query = session.createQuery(hql);
        List<News> results = query.list();
        return results.size();
    }

    /**
     *
     * @return list of all news in the database
     */
    public List<News> getAllNews() {
        Session session = currentSession();
        String hql = "FROM News";
        Query query = session.createQuery(hql);
        List <News> results = (List<News>) query.list();
        return results;
    }
}
