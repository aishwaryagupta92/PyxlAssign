package com.poincare.pyxl.cc.app.launcher;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UserChoice {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

}
