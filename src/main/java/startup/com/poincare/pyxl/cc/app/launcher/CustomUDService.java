package com.poincare.pyxl.cc.app.launcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUDService implements UserDetailsService {

	@Autowired
	UserDao userDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.findByEmailId(username);

		if (user == null)
			throw new UsernameNotFoundException("email_id : '" + username + "' not found!");
		
		return new CustomUserDetails(user);
	}

}
