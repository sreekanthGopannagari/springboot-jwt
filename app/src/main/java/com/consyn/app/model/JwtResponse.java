package com.consyn.app.model;

import java.util.Optional;

import com.consyn.app.dao.User;

public class JwtResponse {

	private String token;
	private Optional<User> user;
	private String statusCode;

	public JwtResponse(String token, Optional<User> optional, String statusCode) {
		super();
		this.token = token;
		this.user = optional;
		this.statusCode = statusCode;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Optional<User> getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = Optional.of(user);
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

}
