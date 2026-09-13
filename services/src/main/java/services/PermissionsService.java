package services;

import db.PermissionsDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pojos.Permissions;

@Transactional
@Service
public class PermissionsService extends BaseService<Permissions> {

    public PermissionsService(PermissionsDao permissionsDao) {
        super(permissionsDao);
    }
}
