package db;

import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import pojos.News;

import java.util.List;
@Repository
public class NewsDao extends BaseDao<News> {

    /**
     *
     * @param selectedPage - number of page that has been selected by the user
     * @param newsOnPage - number of news on the page
     * @return List of news that would be displayed for the user
     */
    public List<News> getNewsList(int selectedPage, int newsOnPage, String sortBy) {
        String hql;
        if (sortBy.equals("postDay")) {
            hql = "SELECT N FROM News N ORDER BY N.postDay DESC";
        }
        else
            hql = "SELECT N FROM News N ORDER BY N.category.categoryName ASC";
        TypedQuery<News> query = entityManager.createQuery(hql, News.class);
        query.setFirstResult((selectedPage - 1)*newsOnPage);
        query.setMaxResults(newsOnPage);
        return query.getResultList();
    }
    /**
     *
     * @return number of all news in the database
     */
    public int countAllNews() {
        TypedQuery<News> query = entityManager.createQuery("FROM News", News.class);
        return query.getResultList().size();
    }

    /**
     *
     * @return list of all news in the database
     */
    public List<News> getAllNews() {
        TypedQuery<News> query = entityManager.createQuery("FROM News", News.class);
        return query.getResultList();
    }
}
