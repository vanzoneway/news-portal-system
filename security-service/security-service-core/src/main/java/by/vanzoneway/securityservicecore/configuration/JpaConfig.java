package by.vanzoneway.securityservicecore.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = {"by.vanzoneway.securityservicecore"})
@EnableJpaRepositories(basePackages = {"by.vanzoneway.securityservicecore.repository"})
@EnableJpaAuditing
@EntityScan(basePackages = {"by.vanzoneway.securityserviceapi.model"})
public class JpaConfig {
}
