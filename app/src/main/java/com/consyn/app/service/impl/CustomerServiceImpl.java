package com.consyn.app.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.consyn.app.dao.User;
import com.consyn.app.repository.UserRepository;
import com.consyn.app.security.util.MLMD5PasswordEncoder;
import com.consyn.app.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	private static final Logger LOG = LoggerFactory.getLogger(CustomerServiceImpl.class);

	@Autowired
	UserRepository userRepository;
    
	@Autowired(required=true)
	MLMD5PasswordEncoder passwordEncoder;

	@Override
	public List<User> getCustomers() {
		LOG.info("Inside CustomerServiceImpl:getCustomers at {}", System.currentTimeMillis());
		List<User> users = userRepository.findAll();
		return users;
	}

	@Override
	public Optional<User> getCustomer(String email) {
		LOG.info("Inside CustomerServiceImpl:getCustomer at {}", System.currentTimeMillis());
		return userRepository.findByEmail(email);
	}

	@Override
	public User register(User user) {
		LOG.info("Inside CustomerServiceImpl:signup at {}", System.currentTimeMillis());

		final String encryptedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encryptedPassword);
		final User createdUser = userRepository.save(user);
		return createdUser;

	}

	
}
