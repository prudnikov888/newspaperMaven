package db;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pojos.Permissions;
@Repository
public class PermissionsDao extends BaseDao<Permissions> {

    @Autowired
    public PermissionsDao(SessionFactory sessionFactory){
        super(sessionFactory);
    }
}
