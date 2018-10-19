package com.bst.user.authentication.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bst.user.authentication.components.UserService;
import com.bst.user.authentication.dto.UserConfirmationDTO;
import com.bst.user.authentication.entities.Person;

@RestController
@RequestMapping("${bst.uri.user.authentication.session:/auth/session}")
public class SessionController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	@PostMapping
	public Person login(@RequestBody @Validated final UserConfirmationDTO credentials, final HttpSession httpSession) {

		final Authentication authentication = new UsernamePasswordAuthenticationToken(credentials.getEmail(),
				credentials.getPassword());
		SecurityContextHolder.getContext().setAuthentication(this.authenticationManager.authenticate(authentication));

		final Person user = this.userService.loadUser(credentials.getEmail());
		user.setPassword(null);
		httpSession.setAttribute("user", user);
		return user;
	}

	@DeleteMapping
	public void logout(final HttpSession session) {
		final Person user = (Person) session.getAttribute("user");
		if (user == null) {
			throw new ResourceNotFoundException();
		}
		session.invalidate();
	}

	@GetMapping
	public Person session(final HttpSession session) {
		final Person user = (Person) session.getAttribute("user");
		if (user == null) {
			throw new ResourceNotFoundException();
		}
		return user;
	}
}
