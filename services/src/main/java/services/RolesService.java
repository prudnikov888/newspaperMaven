package services;

import db.RolesDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pojos.Roles;

@Transactional
@Service
public class RolesService extends BaseService<Roles> {

    @Autowired
    private RolesDao rolesDao;
}

