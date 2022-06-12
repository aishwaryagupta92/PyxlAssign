package com.poincare.pyxl.cc.app.launcher;

import java.time.ZoneId;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.poincare.pyxl.cc.app.request.ToggleTZRequest;
import com.poincare.pyxl.cc.app.response.ToggleTZResponse;

@Controller
@RequestMapping(path = "/pyxl")
public class PyxlTimeZoneResource {
	Logger logger = LoggerFactory.getLogger(PyxlTimeZoneResource.class);

	@Autowired
	private PyxlTimeZoneDao pyxlTimeZoneDao;

	@Autowired
	private UserChoiceDao userChoiceDao;

	@GetMapping(path = "/admin/spawntzs", produces = "application/json")
	public @ResponseBody List<PyxlTimeZone> spawn() {
		for (String zone : ZoneId.SHORT_IDS.keySet().stream().collect(Collectors.toCollection(TreeSet::new))) {
			PyxlTimeZone pyxlTZ = new PyxlTimeZone();
			pyxlTZ.setStatus(Status.ACTIVE);
			pyxlTZ.setZoneId(zone);
			pyxlTZ.setCity(ZoneId.SHORT_IDS.get(zone));
			pyxlTimeZoneDao.save(pyxlTZ);
		}
		Iterable<PyxlTimeZone> iter = pyxlTimeZoneDao.findAll();
		return StreamSupport.stream(iter.spliterator(), false).collect(Collectors.toList());
	}

	@GetMapping(path = "/user/getalltzs", produces = "application/json")
	public @ResponseBody List<PyxlTimeZone> getAllTZs() {
		return StreamSupport.stream(pyxlTimeZoneDao.findAll().spliterator(), false).collect(Collectors.toList());
	}

	@PostMapping(path = "/admin/toggletz", produces = "application/json")
	@Transactional
	public @ResponseBody ToggleTZResponse toggle(@RequestBody ToggleTZRequest request) {
		ToggleTZResponse response = new ToggleTZResponse();
		PyxlTimeZone pyxlTimeZone = null;
		List<UserChoice> userChoices  = null;

		/*
		 * zoneId match > city match (priority)
		 */
		if (!Strings.isBlank(request.getZoneId()))
			pyxlTimeZone = pyxlTimeZoneDao.findByZoneId(request.getZoneId().trim());
		if (pyxlTimeZone == null && !Strings.isBlank(request.getCity()))
			pyxlTimeZone = pyxlTimeZoneDao.findByCity(request.getCity().trim());

		if (pyxlTimeZone != null) {
			if (Status.ACTIVE.equals(pyxlTimeZone.getStatus())) {
				pyxlTimeZone.setStatus(Status.INACTIVE);
				pyxlTimeZone = pyxlTimeZoneDao.save(pyxlTimeZone);

				userChoices = userChoiceDao.findByTzId(pyxlTimeZone.getId());
				if (userChoices != null) {
					userChoices.stream().forEach(x -> x.setStatus(Status.INACTIVE));
					userChoiceDao.saveAll(userChoices);
				}
			} else if (Status.INACTIVE.equals(pyxlTimeZone.getStatus())) {
				pyxlTimeZone.setStatus(Status.ACTIVE);
				pyxlTimeZone = pyxlTimeZoneDao.save(pyxlTimeZone);

				userChoices = userChoiceDao.findByTzId(pyxlTimeZone.getId());
				if (userChoices != null) {
					userChoices.stream().forEach(x -> x.setStatus(Status.ACTIVE));
					userChoiceDao.saveAll(userChoices);
				}
			}

			response.setSuccess(true);
			response.setPyxlTimeZone(pyxlTimeZone);
		} else
			response.setSuccess(false);

		return response;
	}
}
