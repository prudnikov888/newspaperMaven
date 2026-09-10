package web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Replaces the legacy WEB-INF/spring-security.xml.
 *
 * <p>The old XML config had no <code>&lt;intercept-url&gt;</code> rules at all, so every
 * URL was open at the HTTP filter-chain level; the only real access control came from
 * {@code @RolesAllowed("admin")} on individual controller methods, enforced by
 * {@code <global-method-security jsr250-annotations="enabled"/>}. This config faithfully
 * reproduces that shape: HTTP-level access is wide open, and {@link EnableMethodSecurity}
 * with {@code jsr250Enabled = true} is what actually protects the admin-only endpoints.
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

    /**
     * Faithful replacement for the single hardcoded in-memory admin user that used to live
     * in spring-security.xml. Unlike the old XML, the credentials are NOT baked into source:
     * both the username and the BCrypt password hash must be supplied via environment
     * variables. Generate a hash locally with
     * {@code new BCryptPasswordEncoder().encode("your-password")} and set it out of band
     * (local env var / secrets manager) - never commit it.
     */
    @Bean
    public UserDetailsService userDetailsService(
            @Value("${app.security.admin.username}") String adminUsername,
            @Value("${app.security.admin.password-hash}") String adminPasswordHash) {
        return new InMemoryUserDetailsManager(
                User.withUsername(adminUsername)
                        .password(adminPasswordHash)
                        .roles("admin")
                        .build());
    }
}
