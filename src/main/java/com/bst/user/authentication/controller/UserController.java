package com.bst.user.authentication.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {
	@RequestMapping(value = "/auth/signin", method = RequestMethod.GET)
	public String loginPage() {
		return "auth-signin";
	}
	
	@RequestMapping(value = "/user/password", method = RequestMethod.GET)
	public String setPassword() {
		return "auth-signin";
	}
}
