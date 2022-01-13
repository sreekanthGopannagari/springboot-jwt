package com.consyn.app.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.consyn.app.dao.User;
import com.consyn.app.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author sreekanthG
 *
 */

@RestController
@RequestMapping()
@Api(value = "CustomerController")
public class CustomerController {

	private static final Logger LOG = LoggerFactory.getLogger(CustomerController.class);

	@Autowired
	CustomerService customerService;

	@GetMapping(path = "/test", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Test API")
	public ResponseEntity<String> test() {
		LOG.info("testing");
		return new ResponseEntity<>("hello", HttpStatus.OK);

	}

	@GetMapping(path = "/customer/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Get customer")
	public ResponseEntity<Optional<User>> getCustomer(@ApiParam(value = "username") @PathVariable String email) {
		LOG.info("Inside CusomerController: getCustomer at {}", System.currentTimeMillis());
		Optional<User> user = customerService.getCustomer(email);
		return new ResponseEntity<>(user, HttpStatus.OK);

	}

	@GetMapping(path = "/customers", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Get all customers")
	public ResponseEntity<List<User>> getCustomers() {
		LOG.info("Inside CusomerController:getCustomers at {}", System.currentTimeMillis());
		try {
			List<User> users = customerService.getCustomers();
			return new ResponseEntity<>(users, HttpStatus.OK);
		} finally {
			LOG.info("Exiting  CusomerController:getCustomers at {}", System.currentTimeMillis());
		}
	}

	@PostMapping(path = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "User signup")
	public ResponseEntity<User> saveUser(@RequestBody User user) throws Exception {
		LOG.info("Inside CusomerController:signup at {}", System.currentTimeMillis());
		try {
			User user1 = customerService.register(user);
			return new ResponseEntity<>(user1, HttpStatus.OK);
		} finally {
			LOG.info("Exiting  CusomerController:signup at {}", System.currentTimeMillis());

		}
	}
}