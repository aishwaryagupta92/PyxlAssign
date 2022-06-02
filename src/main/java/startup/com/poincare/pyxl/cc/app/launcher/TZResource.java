package com.poincare.pyxl.cc.app.launcher;

import java.time.ZoneId;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/pyxl")
public class TZResource {
	Logger logger = LoggerFactory.getLogger(TZResource.class);

	@Autowired
	private TZDao tZDao;

	@GetMapping(path = "/tz/spawn", produces = "application/json")
	public @ResponseBody List<PyxlTimeZone> spawn() {
		for (String zone : ZoneId.SHORT_IDS.keySet().stream().collect(Collectors.toCollection(TreeSet::new))) {
			PyxlTimeZone pyxlTZ = new PyxlTimeZone();
			pyxlTZ.setStatus(Status.ACTIVE);
			pyxlTZ.setZoneId(zone);
			pyxlTZ.setCity(ZoneId.SHORT_IDS.get(zone));
			tZDao.save(pyxlTZ);
		}
		Iterable<PyxlTimeZone> iter = tZDao.findAll();
		return StreamSupport.stream(iter.spliterator(), false).collect(Collectors.toList());
	}

	@GetMapping(path = "/tz/getAll", produces = "application/json")
	public @ResponseBody List<PyxlTimeZone> getAllTZs() {
		return StreamSupport.stream(tZDao.findAll().spliterator(), false).collect(Collectors.toList());
	}
}
