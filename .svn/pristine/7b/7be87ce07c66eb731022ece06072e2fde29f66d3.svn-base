package db;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pojos.Category;

@Repository
public class CategoryDao extends BaseDao<Category> {

    @Autowired
    public CategoryDao(SessionFactory sessionFactory){
        super(sessionFactory);
    }
}
