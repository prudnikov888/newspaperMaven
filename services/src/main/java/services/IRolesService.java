package services;

import pojos.Roles;

public interface IRolesService {
    Roles findByType(String roleType);
}
