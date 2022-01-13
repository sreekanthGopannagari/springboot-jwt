package com.consyn.app.security.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.consyn.app.dao.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserDetailsImpl implements UserDetails {

	private static final long serialVersionUID = 1L;

	private String name;
	private String email;
	@JsonIgnore
	private String password;
	private Optional<User> user;

	private Collection<? extends GrantedAuthority> authorities;

	public UserDetailsImpl(Optional<User> user, Collection<? extends GrantedAuthority> authorities) {
		super();
		this.name = user.get().getName();
		this.email = user.get().getEmail();
		this.password  =user.get().getPassword();
		this.user = user;
		this.authorities = authorities;
	}

	public static UserDetailsImpl build(Optional<User> user) {
		List<GrantedAuthority> authorities = Arrays
				.asList(new SimpleGrantedAuthority(user.get().getUserRole().toString()));
		return new UserDetailsImpl(user, authorities);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public boolean isAccountNonExpired() {
		
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
	
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
	
		return true;
	}

	@Override
	public boolean isEnabled() {
		if (this.user != null && this.user.get().getEnabled()) {
			return true;
		}
		return false;
	}

	public String getEmail() {
		return email;
	}

	public Optional<User> getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = Optional.ofNullable(user);
	}

	@Override
	public String getUsername() {

		return name;
	}

	public String getName() {
		return email;
	}

}
