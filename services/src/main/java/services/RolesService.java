package services;

import db.RolesDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pojos.Roles;

@Transactional
@Service
public class RolesService extends BaseService<Roles> {

    public RolesService(RolesDao rolesDao) {
        super(rolesDao);
    }
}

