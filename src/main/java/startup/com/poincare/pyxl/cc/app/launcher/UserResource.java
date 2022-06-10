package com.poincare.pyxl.cc.app.launcher;

import java.time.LocalDateTime;

import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.poincare.pyxl.cc.app.request.AddUserRequest;
import com.poincare.pyxl.cc.app.request.GetUserRequest;

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

	@PostMapping(path = "/user/get", produces = "application/json")
	public @ResponseBody User getUser(@RequestBody GetUserRequest request) {
		User user = userDao.findByEmailId(request.getEmailId());
		return user;
	}

	@PostMapping(path = "/user/add", produces = "application/json")
	public @ResponseBody User addUser(@RequestBody AddUserRequest request) {
		if (Strings.isBlank(request.getEmailId())) {
			return null;
		}
		return userDao.save(new User(request.getEmailId()));
	}
}
