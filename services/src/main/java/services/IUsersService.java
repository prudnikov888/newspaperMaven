package services;

import pojos.Users;

public interface IUsersService {
    Users findByEmail(String email);

    /**
     * Creates a new user and assigns it the given role (by type, e.g. "admin"/"user").
     * @return the created user, or null if the email is already taken
     */
    Users registerUser(Users user, String roleType);
}
