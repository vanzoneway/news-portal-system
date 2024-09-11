package by.vanzoneway.newsservicecore.configuration;


import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


//TODO если не работает, то перенести обратно в репозитори и проверить все эти аннотации
@Configuration
@ComponentScan(basePackages = {"by.vanzoneway.newsservicecore"})
@EnableJpaRepositories(basePackages = {"by.vanzoneway.newsservicecore.repository"})
@EnableJpaAuditing
@EntityScan(basePackages = {"by.vanzoneway.newsserviceapi.model"})
public class JpaConfig {

}
