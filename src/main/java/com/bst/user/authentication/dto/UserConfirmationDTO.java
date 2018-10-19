
package com.bst.user.authentication.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserConfirmationDTO {

	@Email
	@NotNull
	@NotEmpty
	private String email;

	@NotNull
	@NotEmpty
	private String password;

	public String getEmail() {
		return this.email;
	}

	public String getPassword() {
		return this.password;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

}
