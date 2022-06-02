package com.poincare.pyxl.cc.app.launcher;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/pyxl")
public class UserResource {
	Logger logger = LoggerFactory.getLogger(UserResource.class);

	@Autowired
	private UserDao userDao;

	@GetMapping(path = "/serverMonitor")
	public @ResponseBody String serverMonitor() {
		return LocalDateTime.now().toString();
	}

	@PostMapping(path = "/user/add")
	public @ResponseBody String addUser(@RequestParam String emailId) {
		User user = new User();
		user.setEmailId(emailId);
		userDao.save(user);
		return user.toString();
	}
}
