package services;

import db.RolesDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pojos.Roles;

@Transactional
@Service
public class RolesService extends BaseService<Roles> implements IRolesService {

    private final RolesDao rolesDao;

    public RolesService(RolesDao rolesDao) {
        super(rolesDao);
        this.rolesDao = rolesDao;
    }

    @Override
    public Roles findByType(String roleType) {
        return rolesDao.findByType(roleType);
    }
}
