package com.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "query")
@Data
public class Queries {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String email;
	private String subject;
	private String message;

}
