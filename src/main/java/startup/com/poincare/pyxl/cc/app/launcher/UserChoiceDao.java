package com.poincare.pyxl.cc.app.launcher;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface UserChoiceDao extends CrudRepository<UserChoice, Long> {

	List<UserChoice> findByUserId(Long userId);

	List<UserChoice> findByTzId(Long tzId);

}
