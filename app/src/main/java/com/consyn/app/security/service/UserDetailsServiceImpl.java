package com.consyn.app.security.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.consyn.app.dao.User;
import com.consyn.app.service.CustomerService;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	CustomerService customerService;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
				
		 Optional<User> user = customerService.getCustomer(email);

		if (user.isPresent()) {
			return UserDetailsImpl.build(user);
		}
		return null;
	}	
	

}
