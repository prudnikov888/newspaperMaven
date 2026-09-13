package services;

import db.NewsDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pojos.News;

import java.util.List;

@Transactional
@Service ("newsService")
public class NewsService extends BaseService<News> implements INewsService<News> {

    private final NewsDao newsDao;

    public NewsService(NewsDao newsDao) {
        super(newsDao);
        this.newsDao = newsDao;
    }

    @Override
    public List<News> getNewsList(int selectedPage, int newsOnPage, String sortBy) {
        return newsDao.getNewsList(selectedPage, newsOnPage, sortBy);
    }

    @Override
    public int countAllNews() {return newsDao.countAllNews();
    }
}
