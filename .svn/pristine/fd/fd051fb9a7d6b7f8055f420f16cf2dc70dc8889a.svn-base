package services;

import db.NewsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pojos.News;

import java.util.List;

@Transactional
@Service ("newsService")
public class NewsService extends BaseService<News> implements INewsService<News> {

    @Autowired
    private NewsDao newsDao;

    @Override
    public List<News> getNewsList(int selectedPage, int newsOnPage, String sortBy) {
        return newsDao.getNewsList(selectedPage, newsOnPage, sortBy);
    }

    @Override
    public int countAllNews() {return newsDao.countAllNews();
    }
}
