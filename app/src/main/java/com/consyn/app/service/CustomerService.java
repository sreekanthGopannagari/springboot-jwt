package com.consyn.app.service;

import java.util.List;
import java.util.Optional;

import com.consyn.app.dao.User;

public interface CustomerService {

	public List<User> getUsers();

	public Optional<User> getUser(String userId);
	
	public User register(User request);
	
	boolean existsByUserId(String userId);

}
