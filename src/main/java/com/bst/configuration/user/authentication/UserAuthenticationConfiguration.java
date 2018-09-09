package com.bst.configuration.user.authentication;

import java.util.Arrays;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
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
public class UserAuthenticationConfiguration {
	@Bean
	public PasswordEncoder createEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public MethodSecurityExpressionHandler defaultMethodSecurityExpressionHandler() {
		DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
		AclPermissionEvaluator permissionEvaluator = new AclPermissionEvaluator(aclService());
		expressionHandler.setPermissionEvaluator(permissionEvaluator);
		return expressionHandler;
	}
	
	@Bean
	public AuditLogger auditLogger() {
		return new ConsoleAuditLogger();
	}
	
	@Bean
	public LookupStrategy lookupStrategy() {
		return new BasicLookupStrategy(dataSource, getAclCache(), aclAuthorizationStrategy(), auditLogger());
	}

	@Bean
	public JdbcMutableAclService aclService() {
		return new JdbcMutableAclService(dataSource, lookupStrategy(), getAclCache());
	}

	@Autowired
	private DataSource dataSource;

	@Bean
	public AclAuthorizationStrategy aclAuthorizationStrategy() {
		return new AclAuthorizationStrategyImpl(new SimpleGrantedAuthority("ROLE_ADMINISTRATOR"));
	}

	@Bean
	public PermissionGrantingStrategy permissionGrantingStrategy() {
		return new DefaultPermissionGrantingStrategy(new ConsoleAuditLogger());
	}

	@Bean
	public AclCache getAclCache() {
		return new SpringCacheBasedAclCache(cacheManager().getCache("acl"), permissionGrantingStrategy(),
				aclAuthorizationStrategy());
	}

	@Bean
	public CacheManager cacheManager() {
		SimpleCacheManager cacheManager = new SimpleCacheManager();
		cacheManager.setCaches(Arrays.asList(new ConcurrentMapCache("acl")));
		return cacheManager;
	}
}
