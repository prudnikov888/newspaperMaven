package services;

import db.PermissionsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pojos.Permissions;

@Transactional
@Service
public class PermissionsService extends BaseService<Permissions> {

    @Autowired
    private PermissionsDao permissionsDao;
}
