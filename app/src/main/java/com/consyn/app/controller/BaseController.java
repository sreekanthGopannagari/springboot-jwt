package com.consyn.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ResponseStatusException;

import com.consyn.app.constants.Constants;

/**
 * @author SreekanthG
 *
 */

public class BaseController {

	/**
	 * Method to validate if user in request is same as the logged-in user
	 *
	 */

	protected boolean validateUser(String userId) {
		if (!isLoggedInUser(userId)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		}
		return true;

	}

	/**
	 * Method to check if logged-in user is admin
	 *
	 */

	protected boolean isLoggedInUserAdmin() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return (userDetails != null && !CollectionUtils.isEmpty(userDetails.getAuthorities())
				&& userDetails.getAuthorities().contains(new SimpleGrantedAuthority(Constants.USER_TYPE_ADMIN)));

	}

	/**
	 * Method to check if user is logged-in user
	 *
	 */
	protected boolean isLoggedInUser(String userid) {

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userDetails != null && userDetails.getUsername().equalsIgnoreCase(userid);
	}

	/**
	 * @param userId
	 * @param allowAdminFlag   action allowed if user is admin
	 * @param allowLoggedInUserOrAdminFlag action allowed if userId is the logged-in user or if logged-in user is admin
	 * @return
	 */
	protected boolean validateRequest(String userId, boolean allowAdminFlag, boolean allowLoggedInUserOrAdminFlag) {
		if (userId == null || (!allowAdminFlag && !isLoggedInUser(userId) && allowLoggedInUserOrAdminFlag)
				|| (allowAdminFlag && !isLoggedInUser(userId)))

		{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		if ((allowLoggedInUserOrAdminFlag && !(!isLoggedInUser(userId) || isLoggedInUserAdmin()))
				|| (!allowLoggedInUserOrAdminFlag && !isLoggedInUser(userId))) {
			
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		}
		if (allowAdminFlag && !isLoggedInUserAdmin()) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		}
		return true;

	}
}
