package db;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pojos.News;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NewsDaoTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<News> query;

    @InjectMocks
    private NewsDao newsDao;

    private News testNews;

    @BeforeEach
    public void setUp() {
        testNews = new News();
        testNews.setNewsId(1);
        testNews.setTitle("Test Title");
        testNews.setTitle4ann("Test Annotation");
        testNews.setAuthor("Test Author");
        testNews.setPostDay("2025-01-01");
        testNews.setMainText("Test Main Text");
    }

    @Test
    public void testGetNewsList_SortByPostDay() {
        // Arrange
        int selectedPage = 1;
        int newsOnPage = 5;
        String sortBy = "postDay";
        List<News> newsList = new ArrayList<>();
        newsList.add(testNews);

        when(entityManager.createQuery("SELECT N FROM News N ORDER BY N.postDay DESC", News.class)).thenReturn(query);
        when(query.setFirstResult(anyInt())).thenReturn(query);
        when(query.setMaxResults(anyInt())).thenReturn(query);
        when(query.getResultList()).thenReturn(newsList);

        // Act
        List<News> result = newsDao.getNewsList(selectedPage, newsOnPage, sortBy);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testNews, result.get(0));
        verify(entityManager).createQuery("SELECT N FROM News N ORDER BY N.postDay DESC", News.class);
        verify(query).setFirstResult(0);
        verify(query).setMaxResults(5);
        verify(query).getResultList();
    }

    @Test
    public void testGetNewsList_SortByCategory() {
        // Arrange
        int selectedPage = 1;
        int newsOnPage = 5;
        String sortBy = "category";
        List<News> newsList = new ArrayList<>();
        newsList.add(testNews);

        when(entityManager.createQuery("SELECT N FROM News N ORDER BY N.category.categoryName ASC", News.class)).thenReturn(query);
        when(query.setFirstResult(anyInt())).thenReturn(query);
        when(query.setMaxResults(anyInt())).thenReturn(query);
        when(query.getResultList()).thenReturn(newsList);

        // Act
        List<News> result = newsDao.getNewsList(selectedPage, newsOnPage, sortBy);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(entityManager).createQuery("SELECT N FROM News N ORDER BY N.category.categoryName ASC", News.class);
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

        when(entityManager.createQuery("SELECT N FROM News N ORDER BY N.postDay DESC", News.class)).thenReturn(query);
        when(query.setFirstResult(anyInt())).thenReturn(query);
        when(query.setMaxResults(anyInt())).thenReturn(query);
        when(query.getResultList()).thenReturn(newsList);

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

        when(entityManager.createQuery("FROM News", News.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(newsList);

        // Act
        int result = newsDao.countAllNews();

        // Assert
        assertEquals(2, result);
        verify(entityManager).createQuery("FROM News", News.class);
        verify(query).getResultList();
    }

    @Test
    public void testCountAllNews_EmptyList() {
        // Arrange
        List<News> emptyList = new ArrayList<>();

        when(entityManager.createQuery("FROM News", News.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(emptyList);

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

        when(entityManager.createQuery("FROM News", News.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(newsList);

        // Act
        List<News> result = newsDao.getAllNews();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testNews, result.get(0));
        verify(entityManager).createQuery("FROM News", News.class);
        verify(query).getResultList();
    }

    @Test
    public void testGetAllNews_EmptyList() {
        // Arrange
        List<News> emptyList = new ArrayList<>();

        when(entityManager.createQuery("FROM News", News.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(emptyList);

        // Act
        List<News> result = newsDao.getAllNews();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
