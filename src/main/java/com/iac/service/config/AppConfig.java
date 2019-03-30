package com.iac.service.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(value = "com.iac.service")
//@EnableJpaRepositories(basePackages = "com.iac.service.repository")
public class AppConfig {
}
