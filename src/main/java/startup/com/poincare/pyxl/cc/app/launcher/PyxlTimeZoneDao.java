package com.poincare.pyxl.cc.app.launcher;

import org.springframework.data.repository.CrudRepository;

public interface PyxlTimeZoneDao extends CrudRepository<PyxlTimeZone, Long> {

	PyxlTimeZone findByZoneId(String zoneId);

	PyxlTimeZone findByCity(String city);

}
