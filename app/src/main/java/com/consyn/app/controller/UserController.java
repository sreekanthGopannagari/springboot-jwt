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
import org.springframework.web.bind.annotation.RequestParam;
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
public class UserController extends BaseController {

	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

	@Autowired
	CustomerService customerService;

	@GetMapping(path = "/test", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Test API")
	public ResponseEntity<String> test() {
		LOG.info("testing");
		return new ResponseEntity<>("hello", HttpStatus.OK);

	}

	@GetMapping(path = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Get user details")
	public ResponseEntity<Optional<User>> getCustomer(@ApiParam(value = "id") @PathVariable String id) {

		LOG.info("Inside CusomerController: getCustomer at {}", System.currentTimeMillis());
		try {
			validateRequest(id, true, false);
			Optional<User> user = customerService.getUser(id);
			return new ResponseEntity<>(user, HttpStatus.OK);
		} finally {
			LOG.info("Exiting  CusomerController:getCustomers at {}", System.currentTimeMillis());
		}

	}

	@GetMapping(path = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Get all users list")
	public ResponseEntity<List<User>> getCustomers(@ApiParam(value = "userId") @RequestParam String userId) {

		LOG.info("Inside CusomerController:getCustomers at {}", System.currentTimeMillis());
		validateRequest(userId, true, false);
		try {
			List<User> users = customerService.getUsers();
			return new ResponseEntity<>(users, HttpStatus.OK);
		} finally {
			LOG.info("Exiting  CusomerController:getCustomers at {}", System.currentTimeMillis());
		}
	}

	@PostMapping(path = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "User signup")
	public ResponseEntity<String> saveUser(@ApiParam(value = "user") @RequestBody User user) throws Exception {
		LOG.info("Inside CusomerController:signup at {}", System.currentTimeMillis());
		try {
			if (customerService.existsByUserId(user.getEmail())) {
			
				 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User with that username already exists.");
			} else {

				User user1 = customerService.register(user);
		        return ResponseEntity.status(HttpStatus.OK).body("User added Successfully");

			}
		} finally {
			LOG.info("Exiting  CusomerController:signup at {}", System.currentTimeMillis());

		}

	}
}