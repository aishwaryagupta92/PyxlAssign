package com.poincare.pyxl.cc.app.launcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {

	private User user;

	public CustomUserDetails() {
	}

	public CustomUserDetails(User user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return new ArrayList<>(Arrays.asList(user.getRole()).stream().map(x -> new SimpleGrantedAuthority(x.toString()))
				.collect(Collectors.toList()));
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getEmailId();
	}

	@Override
	public boolean isAccountNonExpired() {
		return !Status.DELETED.equals(user.getStatus());
	}

	@Override
	public boolean isAccountNonLocked() {
		return !Status.ACTIVE.equals(user.getStatus());
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return Status.ACTIVE.equals(user.getStatus());
	}

}
