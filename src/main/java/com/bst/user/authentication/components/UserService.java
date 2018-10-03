package com.bst.user.authentication.components;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.bst.user.authentication.entities.LocalUserDetails;
import com.bst.user.authentication.entities.Person;
import com.bst.user.authentication.repositories.PersonRepository;

@Component
public class UserService implements UserDetailsService {
	
	@Autowired
	private PersonRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Person user = userRepository.findByEmail(username);
		if (user != null) {
			return new LocalUserDetails(user);
		}
		throw new UsernameNotFoundException("Bad credentials");
	}
	
	@Transactional
	public Person loadUser(String username) throws UsernameNotFoundException {
		
		Person user = userRepository.findByEmail(username);
		if (user != null) {
			return user;
		}
		throw new UsernameNotFoundException("Bad credentials");
	}
	
	@Transactional
	public Person createUser(String email, String name, String password) {
		Person user = new Person(email);
		user.setName(name);
		user.setPassword(passwordEncoder.encode(password));
		
		userRepository.save(user);
		return user;
	}
}
