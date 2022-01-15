package com.consyn.app.controller;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.consyn.app.constants.Constants;
import com.consyn.app.model.JwtResponse;
import com.consyn.app.model.LoginRequest;
import com.consyn.app.security.service.UserDetailsImpl;
import com.consyn.app.security.util.JwtUtil;
import com.consyn.app.service.CustomerService;
import org.slf4j.Logger;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
@Api(value = "AuthContoller")
public class AuthController {

	private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	CustomerService customerService;

	@Autowired
	JwtUtil jwtUtils;

	@PostMapping(path = "/login")
	@ApiOperation(value = "Login User and generate JWT token", response = JwtResponse.class)
	public ResponseEntity<JwtResponse> authenticateUser(@RequestBody(required = true) LoginRequest loginRequest)
			throws Exception {

		LOG.info("Inside AuthConntroller:authenticateUser {} at {}", loginRequest.getEmail(),
				System.currentTimeMillis());
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwt = jwtUtils.generateToken(authentication);

			UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
			return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUser(), Constants.USER_AUTHENTICATED));

		} catch (DisabledException disabledException) {
			LOG.error("User account is  inactive", loginRequest.getEmail(), disabledException);
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new JwtResponse(null, null, Constants.USER_INACTIVE));

		} catch (BadCredentialsException badCreds) {
			LOG.error("Invalid Credentials", loginRequest.getEmail(), badCreds);
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new JwtResponse(null, null, Constants.USER_INVALID_CREDENTIALS));

		}

	}

}
