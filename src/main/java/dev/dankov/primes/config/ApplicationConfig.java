package dev.dankov.primes.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.util.ResourceUtils;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "dev.dankov.primes.*")
@EntityScan(basePackages = "dev.dankov.primes.*")
@EnableJpaRepositories(basePackages = "dev.dankov.primes.*")
@PropertySource(ResourceUtils.CLASSPATH_URL_PREFIX + "application.properties")
public class ApplicationConfig
{
}
