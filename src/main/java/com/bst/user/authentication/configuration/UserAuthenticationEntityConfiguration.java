package com.bst.user.authentication.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan("com.bst.user.authentication.entities")
@EnableJpaRepositories("com.bst.user.authentication.repositories")
public class UserAuthenticationEntityConfiguration {

}
