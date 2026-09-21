package web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import pojos.Roles;
import pojos.Users;
import services.UsersService;

/**
 * Replaces the legacy WEB-INF/spring-security.xml.
 *
 * <p>The old XML config had no <code>&lt;intercept-url&gt;</code> rules at all, so every
 * URL was open at the HTTP filter-chain level; the only real access control came from
 * {@code @RolesAllowed("admin")} on individual controller methods, enforced by
 * {@code <global-method-security jsr250-annotations="enabled"/>}. This config faithfully
 * reproduces that shape: HTTP-level access is wide open, and {@link EnableMethodSecurity}
 * with {@code jsr250Enabled = true} is what actually protects the admin-only endpoints.
 *
 * <p>Authentication is backed by the {@code users} table (via {@link UsersService}), not
 * an in-memory user: {@code loadUserByUsername} looks a user up by email, and its granted
 * roles come from {@code Users.roles} (the {@code roles}/{@code t_roles_users} tables).
 * The very first admin account is bootstrapped into that same table on startup by
 * {@link AdminUserInitializer}, from the {@code ADMIN_USERNAME}/{@code ADMIN_PASSWORD_HASH}
 * env vars — see its javadoc.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/news", true)
                        .failureUrl("/login?error")
                        .permitAll())
                .logout(logout -> logout
                        .logoutSuccessUrl("/news")
                        .permitAll());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UsersService usersService) {
        return email -> {
            Users user = usersService.findByEmail(email);
            if (user == null) {
                throw new UsernameNotFoundException("No user with email " + email);
            }
            String[] roleNames = (user.getRoles() == null || user.getRoles().isEmpty())
                    ? new String[]{"user"}
                    : user.getRoles().stream().map(Roles::getRoleType).toArray(String[]::new);
            return User.withUsername(user.getEmail())
                    .password(user.getPass())
                    .roles(roleNames)
                    .build();
        };
    }
}
