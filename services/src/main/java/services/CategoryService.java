package services;

import db.CategoryDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pojos.Category;

@Transactional
@Service
public class CategoryService extends BaseService<Category> {

    public CategoryService(CategoryDao categoryDao) {
        super(categoryDao);
    }
}
