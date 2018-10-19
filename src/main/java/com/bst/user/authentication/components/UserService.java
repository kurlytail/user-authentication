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
	private PasswordEncoder passwordEncoder;

	@Autowired
	private PersonRepository userRepository;

	@Transactional
	public Person createUser(final String email, final String name, final String password) {
		final Person user = new Person(email);
		user.setName(name);
		user.setPassword(this.passwordEncoder.encode(password));

		this.userRepository.save(user);
		return user;
	}

	@Transactional
	public Person loadUser(final String username) throws UsernameNotFoundException {

		final Person user = this.userRepository.findByEmail(username);
		if (user != null) {
			return user;
		}
		throw new UsernameNotFoundException("Bad credentials");
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

		final Person user = this.userRepository.findByEmail(username);
		if (user != null) {
			return new LocalUserDetails(user);
		}
		throw new UsernameNotFoundException("Bad credentials");
	}
}
