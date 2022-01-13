package com.consyn.app.service;

import java.util.List;
import java.util.Optional;

import com.consyn.app.dao.User;

public interface CustomerService {

	public List<User> getCustomers();

	public Optional<User> getCustomer(String email);
	
	public User register(User request);
}
