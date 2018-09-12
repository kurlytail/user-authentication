package com.bst.user.authentication.components;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.bst.user.authentication.entities.LocalUserDetails;
import com.bst.user.authentication.entities.User;
import com.bst.user.authentication.repositories.UserRepository;

@Component
public class UserService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userRepository.findByEmail(username);
		if (user != null) {
			return new LocalUserDetails(user);
		}
		throw new UsernameNotFoundException("Bad credentials");
	}
	
	@Transactional
	public User loadUser(String username) throws UsernameNotFoundException {
		
		User user = userRepository.findByEmail(username);
		if (user != null) {
			return user;
		}
		throw new UsernameNotFoundException("Bad credentials");
	}
	
	@Transactional
	public User createUser(String email, String name, String password) {
		User user = new User(email);
		user.setName(name);
		user.setPassword(passwordEncoder.encode(password));
		
		userRepository.save(user);
		return user;
	}
}
