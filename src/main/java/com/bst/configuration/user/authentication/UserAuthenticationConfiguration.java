package com.bst.configuration.user.authentication;

import java.util.Arrays;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.acls.AclPermissionEvaluator;
import org.springframework.security.acls.domain.AclAuthorizationStrategy;
import org.springframework.security.acls.domain.AclAuthorizationStrategyImpl;
import org.springframework.security.acls.domain.AuditLogger;
import org.springframework.security.acls.domain.ConsoleAuditLogger;
import org.springframework.security.acls.domain.DefaultPermissionGrantingStrategy;
import org.springframework.security.acls.domain.SpringCacheBasedAclCache;
import org.springframework.security.acls.jdbc.BasicLookupStrategy;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.model.AclCache;
import org.springframework.security.acls.model.PermissionGrantingStrategy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@ComponentScan("com.bst.user")
@EnableCaching
@EnableAutoConfiguration
public class UserAuthenticationConfiguration {
	@Autowired
	private DataSource dataSource;

	@Bean
	public AclAuthorizationStrategy aclAuthorizationStrategy() {
		return new AclAuthorizationStrategyImpl(new SimpleGrantedAuthority("ROLE_ADMINISTRATOR"));
	}

	@Bean
	public JdbcMutableAclService aclService() {
		return new JdbcMutableAclService(this.dataSource, this.lookupStrategy(), this.getAclCache());
	}

	@Bean
	public AuditLogger auditLogger() {
		return new ConsoleAuditLogger();
	}

	@Bean
	public CacheManager cacheManager() {
		final SimpleCacheManager cacheManager = new SimpleCacheManager();
		cacheManager.setCaches(Arrays.asList(new ConcurrentMapCache("acl")));
		return cacheManager;
	}

	@Bean
	public PasswordEncoder createEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public MethodSecurityExpressionHandler defaultMethodSecurityExpressionHandler() {
		final DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
		final AclPermissionEvaluator permissionEvaluator = new AclPermissionEvaluator(this.aclService());
		expressionHandler.setPermissionEvaluator(permissionEvaluator);
		return expressionHandler;
	}

	@Bean
	public AclCache getAclCache() {
		return new SpringCacheBasedAclCache(this.cacheManager().getCache("acl"), this.permissionGrantingStrategy(),
				this.aclAuthorizationStrategy());
	}

	@Bean
	public LookupStrategy lookupStrategy() {
		return new BasicLookupStrategy(this.dataSource, this.getAclCache(), this.aclAuthorizationStrategy(),
				this.auditLogger());
	}

	@Bean
	public PermissionGrantingStrategy permissionGrantingStrategy() {
		return new DefaultPermissionGrantingStrategy(new ConsoleAuditLogger());
	}
}
