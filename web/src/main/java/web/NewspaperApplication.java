package web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"controllers", "services", "db", "pojos", "web"})
@EntityScan("pojos")
public class NewspaperApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewspaperApplication.class, args);
    }
}
