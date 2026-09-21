package web.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import pojos.Users;
import services.UsersService;

/**
 * Bootstraps the first admin account into the {@code users} table on startup, from the
 * {@code ADMIN_USERNAME} (used as the login email) / {@code ADMIN_PASSWORD_HASH} (a
 * pre-computed BCrypt hash, generate with {@code new BCryptPasswordEncoder().encode(...)})
 * env vars. Both are optional now (unlike the old in-memory-only setup): if either is blank,
 * bootstrapping is skipped and the app still starts — useful once at least one real admin
 * already exists in the database. Idempotent: does nothing if a user with that email is
 * already present, so it's safe to leave the env vars set across restarts.
 */
@Component
public class AdminUserInitializer implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(AdminUserInitializer.class);

    private final UsersService usersService;

    @Value("${app.security.admin.username:}")
    private String adminEmail;

    @Value("${app.security.admin.password-hash:}")
    private String adminPasswordHash;

    public AdminUserInitializer(UsersService usersService) {
        this.usersService = usersService;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (adminEmail.isBlank() || adminPasswordHash.isBlank()) {
            log.warn("ADMIN_USERNAME/ADMIN_PASSWORD_HASH not set - skipping admin user bootstrap");
            return;
        }
        Users admin = new Users();
        admin.setEmail(adminEmail);
        admin.setPass(adminPasswordHash);
        admin.setName("Admin");
        admin.setSurname("Admin");
        Users created = usersService.registerUser(admin, "admin");
        if (created != null) {
            log.info("Bootstrapped admin user {}", adminEmail);
        }
    }
}
