package com.poincare.pyxl.cc.app.launcher;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.poincare.pyxl.cc.app.request.GenericUserChoiceRequest;
import com.poincare.pyxl.cc.app.response.GenericUserChoiceResponse;
import com.poincare.pyxl.cc.app.response.GetAllUserChoiceResponse;

@Controller
@RequestMapping(path = "/pyxl")
public class UserChoiceResource {
	Logger logger = LoggerFactory.getLogger(UserChoiceResource.class);

	@Autowired
	private UserChoiceDao userChoiceDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private PyxlTimeZoneDao pyxlTimeZoneDao;

	@GetMapping(path = "/userchoice/get")
	public @ResponseBody GenericUserChoiceResponse getUserChoice(@RequestParam Long id) {
		GenericUserChoiceResponse response = new GenericUserChoiceResponse();
		if (id == null) {
			response.setSuccess(false);
			return response;
		}

		Optional<UserChoice> userChoice = userChoiceDao.findById(id);
		if (userChoice.isPresent() && Status.ACTIVE.equals(userChoice.get().getStatus())) {
			response.setSuccess(true);
			response.setUserChoice(userChoice.get());
			return response;
		} else {
			response.setSuccess(false);
			return response;
		}
	}

	@GetMapping(path = "/userchoice/getbyuserid")
	public @ResponseBody GetAllUserChoiceResponse getUserChoiceByUserId(@RequestParam Long userId) {
		GetAllUserChoiceResponse response = new GetAllUserChoiceResponse();
		if (userId == null) {
			response.setSuccess(false);
			return response;
		}

		List<UserChoice> userChoices = userChoiceDao.findByUserId(userId);
		if (userChoices != null) {
			response.setSuccess(true);
			response.setUserChoices(userChoices);
			return response;
		} else {
			response.setSuccess(false);
			return response;
		}
	}

	/*
	 * failfast
	 */
	@PostMapping(path = "/userchoice/add", produces = "application/json")
	public @ResponseBody GenericUserChoiceResponse addUserChoice(@RequestBody GenericUserChoiceRequest request) {
		GenericUserChoiceResponse response = new GenericUserChoiceResponse();

		if (request.getUserId() == null || request.getTzId() == null) {
			response.setSuccess(false);
			return response;
		}

		Optional<User> user = userDao.findById(request.getUserId());
		Optional<PyxlTimeZone> pyxlTimeZone = pyxlTimeZoneDao.findById(request.getTzId());
		if (user.isPresent() && Status.ACTIVE.equals(user.get().getStatus())) {
			if (pyxlTimeZone.isPresent() && Status.ACTIVE.equals(pyxlTimeZone.get().getStatus())) {

			} else {
				response.setSuccess(false);
				return response;
			}
		} else {
			response.setSuccess(false);
			return response;
		}

		List<UserChoice> savedUserChoices = null;
		if (request.getUserId() != null)
			savedUserChoices = userChoiceDao.findByUserId(request.getUserId());

		if (savedUserChoices != null
				&& savedUserChoices.stream().filter(x -> request.getTzId().equals(x.getTzId())).count() > 0) {
			UserChoice userChoice = savedUserChoices.stream().filter(x -> request.getTzId().equals(x.getTzId()))
					.findAny().get();
			if (Status.INACTIVE.equals(userChoice.getStatus())) {
				userChoice.setStatus(Status.ACTIVE);
				userChoice = userChoiceDao.save(userChoice);

			}
			response.setSuccess(true);
			response.setUserChoice(userChoice);
			return response;
		} else {
			UserChoice userChoice = userChoiceDao.save(new UserChoice(request.getUserId(), request.getTzId()));
			response.setSuccess(true);
			response.setUserChoice(userChoice);
			return response;
		}
	}

	/**
	 * toggles all user's choices OR all that have the particular tz
	 */
	@PostMapping(path = "/userchoice/toggle", produces = "application/json")
	public @ResponseBody GenericUserChoiceResponse toggleUserChoices(@RequestBody GenericUserChoiceRequest request) {
		GenericUserChoiceResponse response = new GenericUserChoiceResponse();

		if (request.getStatus() == null) {
			response.setSuccess(false);
			return response;
		}
		if (request.getUserId() == null && request.getTzId() == null) {
			response.setSuccess(false);
			return response;
		}

		Optional<User> user = userDao.findById(request.getUserId());
		Optional<PyxlTimeZone> pyxlTimeZone = pyxlTimeZoneDao.findById(request.getTzId());
		if (user.isEmpty() || pyxlTimeZone.isPresent()) {
			if (Status.ACTIVE.equals(pyxlTimeZone.get().getStatus())) {
				List<UserChoice> userChoices = userChoiceDao.findByTzId(request.getTzId());
				userChoices.stream().forEach(x -> x.setStatus(request.getStatus()));
				userChoiceDao.saveAll(userChoices);
				response.setSuccess(true);
				return response;
			} else {
				response.setSuccess(false);
				return response;
			}
		} else if (user.isPresent() || pyxlTimeZone.isEmpty()) {
			List<UserChoice> userChoices = userChoiceDao.findByUserId(request.getUserId());
			userChoices.stream().forEach(x -> x.setStatus(request.getStatus()));
			userChoiceDao.saveAll(userChoices);
			response.setSuccess(true);
			return response;
		}
		response.setSuccess(false);
		return response;
	}

}
