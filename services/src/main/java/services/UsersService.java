package services;

import db.UsersDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pojos.Users;

@Transactional
@Service
public class UsersService extends BaseService<Users> {

    public UsersService(UsersDao usersDao) {
        super(usersDao);
    }
    /*
    public boolean checkUser(String email, String pass) {
        return usersDao.checkUser(email, pass);
    }
    public Users getUser(String email, String pass) {
        return usersDao.getUser(email, pass);
    }
    */
}
