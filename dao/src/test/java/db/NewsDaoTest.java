package db;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pojos.News;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class NewsDaoTest {

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @Mock
    private Query query;

    @InjectMocks
    private NewsDao newsDao;

    private News testNews;

    @Before
    public void setUp() {
        testNews = new News();
        testNews.setNewsId(1);
        testNews.setTitle("Test Title");
        testNews.setTitle4ann("Test Annotation");
        testNews.setAuthor("Test Author");
        testNews.setPostDay("2025-01-01");
        testNews.setMainText("Test Main Text");

        when(sessionFactory.getCurrentSession()).thenReturn(session);
    }

    @Test
    public void testGetNewsList_SortByPostDay() {
        // Arrange
        int selectedPage = 1;
        int newsOnPage = 5;
        String sortBy = "postDay";
        List<News> newsList = new ArrayList<>();
        newsList.add(testNews);

        when(session.createQuery("SELECT N FROM News N ORDER BY N.postDay DESC")).thenReturn(query);
        when(query.setFirstResult(anyInt())).thenReturn(query);
        when(query.setMaxResults(anyInt())).thenReturn(query);
        when(query.list()).thenReturn(newsList);

        // Act
        List<News> result = newsDao.getNewsList(selectedPage, newsOnPage, sortBy);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testNews, result.get(0));
        verify(session).createQuery("SELECT N FROM News N ORDER BY N.postDay DESC");
        verify(query).setFirstResult(0);
        verify(query).setMaxResults(5);
        verify(query).list();
    }

    @Test
    public void testGetNewsList_SortByCategory() {
        // Arrange
        int selectedPage = 1;
        int newsOnPage = 5;
        String sortBy = "category";
        List<News> newsList = new ArrayList<>();
        newsList.add(testNews);

        when(session.createQuery("SELECT N FROM News N ORDER BY N.category.categoryName ASC")).thenReturn(query);
        when(query.setFirstResult(anyInt())).thenReturn(query);
        when(query.setMaxResults(anyInt())).thenReturn(query);
        when(query.list()).thenReturn(newsList);

        // Act
        List<News> result = newsDao.getNewsList(selectedPage, newsOnPage, sortBy);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(session).createQuery("SELECT N FROM News N ORDER BY N.category.categoryName ASC");
        verify(query).setFirstResult(0);
        verify(query).setMaxResults(5);
    }

    @Test
    public void testGetNewsList_Pagination() {
        // Arrange
        int selectedPage = 2;
        int newsOnPage = 5;
        String sortBy = "postDay";
        List<News> newsList = new ArrayList<>();

        when(session.createQuery("SELECT N FROM News N ORDER BY N.postDay DESC")).thenReturn(query);
        when(query.setFirstResult(anyInt())).thenReturn(query);
        when(query.setMaxResults(anyInt())).thenReturn(query);
        when(query.list()).thenReturn(newsList);

        // Act
        newsDao.getNewsList(selectedPage, newsOnPage, sortBy);

        // Assert
        verify(query).setFirstResult(5); // (2-1)*5 = 5
        verify(query).setMaxResults(5);
    }

    @Test
    public void testCountAllNews() {
        // Arrange
        List<News> newsList = new ArrayList<>();
        newsList.add(testNews);
        newsList.add(new News());

        when(session.createQuery("FROM News")).thenReturn(query);
        when(query.list()).thenReturn(newsList);

        // Act
        int result = newsDao.countAllNews();

        // Assert
        assertEquals(2, result);
        verify(session).createQuery("FROM News");
        verify(query).list();
    }

    @Test
    public void testCountAllNews_EmptyList() {
        // Arrange
        List<News> emptyList = new ArrayList<>();

        when(session.createQuery("FROM News")).thenReturn(query);
        when(query.list()).thenReturn(emptyList);

        // Act
        int result = newsDao.countAllNews();

        // Assert
        assertEquals(0, result);
    }

    @Test
    public void testGetAllNews() {
        // Arrange
        List<News> newsList = new ArrayList<>();
        newsList.add(testNews);

        when(session.createQuery("FROM News")).thenReturn(query);
        when(query.list()).thenReturn(newsList);

        // Act
        List<News> result = newsDao.getAllNews();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testNews, result.get(0));
        verify(session).createQuery("FROM News");
        verify(query).list();
    }

    @Test
    public void testGetAllNews_EmptyList() {
        // Arrange
        List<News> emptyList = new ArrayList<>();

        when(session.createQuery("FROM News")).thenReturn(query);
        when(query.list()).thenReturn(emptyList);

        // Act
        List<News> result = newsDao.getAllNews();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
