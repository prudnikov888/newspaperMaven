package services;

import db.NewsDao;
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
public class NewsServiceTest {

    @Mock
    private NewsDao newsDao;

    @InjectMocks
    private NewsService newsService;

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
    }

    @Test
    public void testGetNewsList() {
        // Arrange
        int selectedPage = 1;
        int newsOnPage = 5;
        String sortBy = "postDay";
        List<News> newsList = new ArrayList<>();
        newsList.add(testNews);

        when(newsDao.getNewsList(selectedPage, newsOnPage, sortBy)).thenReturn(newsList);

        // Act
        List<News> result = newsService.getNewsList(selectedPage, newsOnPage, sortBy);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testNews, result.get(0));
        verify(newsDao).getNewsList(selectedPage, newsOnPage, sortBy);
    }

    @Test
    public void testGetNewsList_SortByCategory() {
        // Arrange
        int selectedPage = 1;
        int newsOnPage = 5;
        String sortBy = "category";
        List<News> newsList = new ArrayList<>();
        newsList.add(testNews);

        when(newsDao.getNewsList(selectedPage, newsOnPage, sortBy)).thenReturn(newsList);

        // Act
        List<News> result = newsService.getNewsList(selectedPage, newsOnPage, sortBy);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(newsDao).getNewsList(selectedPage, newsOnPage, sortBy);
    }

    @Test
    public void testCountAllNews() {
        // Arrange
        int expectedCount = 10;
        when(newsDao.countAllNews()).thenReturn(expectedCount);

        // Act
        int result = newsService.countAllNews();

        // Assert
        assertEquals(expectedCount, result);
        verify(newsDao).countAllNews();
    }

    @Test
    public void testCountAllNews_Zero() {
        // Arrange
        when(newsDao.countAllNews()).thenReturn(0);

        // Act
        int result = newsService.countAllNews();

        // Assert
        assertEquals(0, result);
        verify(newsDao).countAllNews();
    }

    @Test
    public void testSaveOrUpdate() {
        // Act
        newsService.saveOrUpdate(testNews);

        // Assert
        verify(newsDao).saveOrUpdate(testNews);
    }

    @Test
    public void testGet() {
        // Arrange
        Integer newsId = 1;
        when(newsDao.get(newsId)).thenReturn(testNews);

        // Act
        News result = newsService.get(newsId);

        // Assert
        assertNotNull(result);
        assertEquals(testNews, result);
        verify(newsDao).get(newsId);
    }

    @Test
    public void testGet_NotFound_ReturnsNull() {
        // Arrange
        Integer newsId = 999;
        when(newsDao.get(newsId)).thenReturn(null);

        // Act
        News result = newsService.get(newsId);

        // Assert
        assertNull(result);
        verify(newsDao).get(newsId);
    }

    @Test
    public void testDelete() {
        // Act
        newsService.delete(testNews);

        // Assert
        verify(newsDao).delete(testNews);
    }
}
