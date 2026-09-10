package db.config;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Exposes the Hibernate-native {@link SessionFactory} unwrapped from Boot's
 * autoconfigured {@link EntityManagerFactory}, so the existing db.BaseDao-based
 * DAO layer (which talks to Hibernate's Session API directly) keeps working
 * without being rewritten onto Spring Data JPA repositories.
 */
@Configuration
public class HibernateSessionFactoryConfig {

    @Bean
    public SessionFactory sessionFactory(EntityManagerFactory entityManagerFactory) {
        return entityManagerFactory.unwrap(SessionFactory.class);
    }
}
