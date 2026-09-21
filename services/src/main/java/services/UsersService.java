package services;

import db.UsersDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pojos.Roles;
import pojos.Users;

@Transactional
@Service
public class UsersService extends BaseService<Users> implements IUsersService {

    private final UsersDao usersDao;
    private final RolesService rolesService;

    public UsersService(UsersDao usersDao, RolesService rolesService) {
        super(usersDao);
        this.usersDao = usersDao;
        this.rolesService = rolesService;
    }

    @Override
    public Users findByEmail(String email) {
        return usersDao.findByEmail(email);
    }

    @Override
    public Users registerUser(Users user, String roleType) {
        if (usersDao.findByEmail(user.getEmail()) != null) {
            return null;
        }
        usersDao.create(user);
        Roles role = rolesService.findByType(roleType);
        if (role != null) {
            role.getUsers().add(user);
        }
        return user;
    }
}
