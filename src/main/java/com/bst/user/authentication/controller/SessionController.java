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
import com.bst.user.authentication.entities.User;

@RestController
@RequestMapping("${bst.uri.user.authentication.session:/auth/session}")
public class SessionController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	@PostMapping
	public User login(@RequestBody @Validated UserConfirmationDTO credentials, HttpSession httpSession) {

		Authentication authentication = new UsernamePasswordAuthenticationToken(credentials.getEmail(),
				credentials.getPassword());
		SecurityContextHolder.getContext().setAuthentication(authenticationManager.authenticate(authentication));

		User user = userService.loadUser(credentials.getEmail());
		user.setPassword(null);
		httpSession.setAttribute("user", user);
		return user;
	}

	@GetMapping
	public User session(HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			throw new ResourceNotFoundException();
		}
		return user;
	}

	@DeleteMapping
	public void logout(HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			throw new ResourceNotFoundException();
		}
		session.invalidate();
	}
}
