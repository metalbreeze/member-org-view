package com.shop.model;

import java.sql.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;

/**
 * Entity bean with JPA annotations Hibernate provides JPA implementation
 * 
 * @author pankaj
 *
 */
@Entity
@Table(name = "GROUP1")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Group {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView(View.Simple.class)
	private int id;
	@JsonView(View.Simple.class)
	private String name;

	private Date createDate = new Date(System.currentTimeMillis());



	@JsonView(View.Collection.class)
	@Transient
	private HashMap<Integer, List<User>> levelUsers=new HashMap<Integer, List<User>>();
	
	public HashMap<Integer,List<User>> getLevelUsers() {
		return levelUsers;
	}


	public void setLevelUsers(HashMap<Integer, List<User>> levelUsers) {
		this.levelUsers = levelUsers;
	}
	
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date date) {
		this.createDate = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "id=" + id + ", name=" + name;
	}
}
