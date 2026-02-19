package controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.ModelMap;
import pojos.Category;
import pojos.News;
import services.NewsService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MainControllerTest {

    @Mock
    private NewsService newsService;

    @InjectMocks
    private MainController mainController;

    private News testNews;
    private ModelMap modelMap;

    @Before
    public void setUp() {
        modelMap = new ModelMap();
        testNews = new News();
        testNews.setNewsId(1);
        testNews.setTitle("Test Title");
        testNews.setTitle4ann("Test Annotation");
        testNews.setAuthor("Test Author");
        testNews.setPostDay("2025-01-01");
        testNews.setMainText("Test Main Text");
    }

    @Test
    public void testShowNews_DefaultParameters() {
        // Arrange
        List<News> newsList = new ArrayList<>();
        newsList.add(testNews);
        when(newsService.countAllNews()).thenReturn(10);
        when(newsService.getNewsList(1, 5, "postDay")).thenReturn(newsList);

        // Act
        String viewName = mainController.showNews(modelMap, 5, 1, "postDay");

        // Assert
        assertEquals("showNews", viewName);
        assertNotNull(modelMap.get("newsList"));
        assertEquals(2, (int) modelMap.get("numberOfPages"));
        assertEquals(1, (int) modelMap.get("selectedPage"));
        assertEquals(5, (int) modelMap.get("newsOnPage"));
        assertEquals("postDay", modelMap.get("sortBy"));
        assertEquals("По дате", modelMap.get("sortByFull"));
        verify(newsService).countAllNews();
        verify(newsService).getNewsList(1, 5, "postDay");
    }

    @Test
    public void testShowNews_SortByCategory() {
        // Arrange
        List<News> newsList = new ArrayList<>();
        newsList.add(testNews);
        when(newsService.countAllNews()).thenReturn(10);
        when(newsService.getNewsList(1, 5, "category")).thenReturn(newsList);

        // Act
        String viewName = mainController.showNews(modelMap, 5, 1, "category");

        // Assert
        assertEquals("showNews", viewName);
        assertEquals("По категории", modelMap.get("sortByFull"));
        verify(newsService).getNewsList(1, 5, "category");
    }

    @Test
    public void testShowNews_PageBoundary_LessThanOne() {
        // Arrange
        List<News> newsList = new ArrayList<>();
        when(newsService.countAllNews()).thenReturn(10);
        when(newsService.getNewsList(1, 5, "postDay")).thenReturn(newsList);

        // Act
        mainController.showNews(modelMap, 5, 0, "postDay");

        // Assert
        assertEquals(1, (int) modelMap.get("selectedPage"));
        verify(newsService).getNewsList(1, 5, "postDay");
    }

    @Test
    public void testShowNews_PageBoundary_GreaterThanMax() {
        // Arrange
        List<News> newsList = new ArrayList<>();
        when(newsService.countAllNews()).thenReturn(10);
        when(newsService.getNewsList(2, 5, "postDay")).thenReturn(newsList);

        // Act
        mainController.showNews(modelMap, 5, 10, "postDay");

        // Assert
        assertEquals(2, (int) modelMap.get("numberOfPages"));
        assertEquals(2, (int) modelMap.get("selectedPage"));
        verify(newsService).getNewsList(2, 5, "postDay");
    }

    @Test
    public void testAddNews() {
        // Act
        String viewName = mainController.addNews();

        // Assert
        assertEquals("addNews", viewName);
    }

    @Test
    public void testEditNews() {
        // Arrange
        Integer newsId = 1;
        when(newsService.get(newsId)).thenReturn(testNews);

        // Act
        String viewName = mainController.editNews(modelMap, newsId);

        // Assert
        assertEquals("editNews", viewName);
        assertEquals(testNews, modelMap.get("newsToEdit"));
        verify(newsService).get(newsId);
    }

    @Test
    public void testAddWriteNews() {
        // Arrange
        News news = new News();
        news.setTitle("New Title");
        String categoryName = "Technology";

        // Act
        String viewName = mainController.addWriteNews(news, categoryName);

        // Assert
        assertEquals("redirect:/showNews.form", viewName);
        assertNotNull(news.getCategory());
        assertEquals(categoryName, news.getCategory().getCategoryName());
        verify(newsService).saveOrUpdate(news);
    }

    @Test
    public void testEditWriteNews() {
        // Arrange
        News news = new News();
        news.setNewsId(1);
        news.setTitle("Updated Title");
        String categoryName = "Science";

        // Act
        String viewName = mainController.editWriteNews(news, categoryName);

        // Assert
        assertEquals("redirect:/showNews.form", viewName);
        assertNotNull(news.getCategory());
        assertEquals(categoryName, news.getCategory().getCategoryName());
        verify(newsService).saveOrUpdate(news);
    }

    @Test
    public void testDelNews() {
        // Arrange
        Integer newsId = 1;
        when(newsService.get(newsId)).thenReturn(testNews);

        // Act
        String viewName = mainController.writeNews(newsId);

        // Assert
        assertEquals("redirect:/showNews.form", viewName);
        verify(newsService).get(newsId);
        verify(newsService).delete(testNews);
    }

    @Test
    public void testShowSingleNews() {
        // Arrange
        Integer newsId = 1;
        when(newsService.get(newsId)).thenReturn(testNews);

        // Act
        String viewName = mainController.showSingleNews(modelMap, newsId);

        // Assert
        assertEquals("showSingleNews", viewName);
        assertEquals(testNews, modelMap.get("singleNews"));
        verify(newsService).get(newsId);
    }

    @Test
    public void testErrorPage() {
        // Act
        String viewName = mainController.errorPage();

        // Assert
        assertEquals("errorPage", viewName);
    }

    @Test
    public void testLogInPage() {
        // Act
        String viewName = mainController.logInPage();

        // Assert
        assertEquals("logInPage", viewName);
    }
}
