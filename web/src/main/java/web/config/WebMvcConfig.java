package web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Locale;

/**
 * Replaces the localeResolver/LocaleChangeInterceptor beans that used to live in
 * WEB-INF/dispatcher-servlet.xml. Boot's default LocaleResolver is header-based
 * (AcceptHeaderLocaleResolver), so the old cookie-based behaviour (default locale ru,
 * cookie name LocaleCookie, 1 hour max age, ?locale= query param switch) has to be
 * re-declared explicitly.
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public LocaleResolver localeResolver() {
        // VERIFY: exact CookieLocaleResolver API shape (constructor/setter signatures
        // shifted across Spring Framework 6/7) against the resolved Spring Framework version.
        CookieLocaleResolver resolver = new CookieLocaleResolver("LocaleCookie");
        resolver.setDefaultLocale(new Locale("ru"));
        resolver.setCookieMaxAge(java.time.Duration.ofSeconds(3600));
        return resolver;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("locale");
        return interceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}
