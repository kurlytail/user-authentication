package com.bst.user.authentication.entities;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class LocalUserDetails implements UserDetails {

	private Person user;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 20020030203L;
	
	public LocalUserDetails(Person user) {
		this.user = user;
	}
	
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	@Override
	public String getUsername() {
		return user.getEmail();
	}
	
	@Override
	public String getPassword() {
		return user.getPassword();
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

}
