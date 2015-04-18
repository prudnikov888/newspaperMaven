import db.NewsDao;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import pojos.News;

import java.util.List;

import static junit.framework.Assert.*;

@ContextConfiguration("/entities-hib-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public class NewsDaoTest {

    private static Logger log = Logger.getLogger(NewsDaoTest.class);

    @Autowired
    private NewsDao newsDao;

    @Test
    public void saveOrUpdateAndGetNews() {

        News news = new News();
        news.setTitle4ann("Тестовая аннотация");
        news.setTitle("Тестовое название");
        news.setMainText("Тестовый текст новости");
        news.setAuthor("Тестовый автор");
        news.setPostDay("2015-04-03");
        try {
            newsDao.saveOrUpdate(news);
            assertNotNull(news.getNewsId());
        } catch (HibernateException e) {
            log.error("Error save or update News in NewsDaoTest " + e);
        }
        try {
            assertEquals("Проверка методов saveOrUpdate и get для сущности News", news, newsDao.get(news.getNewsId()));
        } catch (HibernateException e) {
            log.error("Error get News in NewsDaoTest " + e);
        }
    }

    @Test
    public void deleteNews() {
        List<News> list = newsDao.getAllNews();
        int size = list.size();
        News persistent = list.get(0);
        try {
            newsDao.delete(persistent);
        } catch (HibernateException e) {
            log.error("Error delete News in NewsDaoTest " + e);
        }
        assertNotSame("Проверяем количество новостей в выборке (ожидаем разное)", newsDao.getAllNews(), size);
    }

}
