package services;

import db.CategoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pojos.Category;

@Transactional
@Service
public class CategoryService extends BaseService<Category> {

    @Autowired
    CategoryDao categoryDao;
}
