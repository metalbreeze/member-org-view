package com.shop.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	private static final Logger logger = LoggerFactory.getLogger(Group.class);
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView(View.Simple.class)
	private int id;

	@JsonView(View.Simple.class)
	private String name;

	private Date createDate = new Date(System.currentTimeMillis());

	@JsonView(View.Collection.class)
	@OneToMany(mappedBy = "group" ,cascade = CascadeType.ALL )
	private List<User> users = new ArrayList<User>();

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	@JsonView(View.Collection.class)
	@Transient
	private HashMap<String, List<User>> levelUsers;
	public HashMap<String, List<User>> getLevelUsers() {
		return levelUsers;
	}

	@JsonView(View.Collection.class)
	@Transient
	private ArrayList<String> availbleLabes;

	public ArrayList<String> getAvailbleLabes() {
		return availbleLabes;
	}

	public void setAvailbleLabes(ArrayList<String> availbleLabes) {
		this.availbleLabes = availbleLabes;
	}

	public void setLevelUsers(HashMap<String, List<User>> levelUsers) {
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

	public static Group transform(Group g) {
		HashMap<String, List<User>> hm= new HashMap<String, List<User>>();
		g.setLevelUsers(hm);
		for (int i = 0; i < labels.length; i++) {
			hm.put(labels[i], (List<User>)new ArrayList<User>());
		}
		for (Iterator iterator = g.getUsers().iterator(); iterator.hasNext();) {
			User user = (User) iterator.next();
			List<User> list = hm.get(user.getLevel());
			list.add(user);
		}
		logger.debug("transform: g.getUsers().size "+g.getUsers().size());
		ArrayList<String> al = new ArrayList<String>();
		g.setAvailbleLabes(al);
		for (int i = 0; i < labels.length; i++) {
			if(maxLabels[i]>hm.get(labels[i]).size()){
				al.add(labels[i]);
			}
		}
		logger.debug("transform: av lable "+al);
		return g;
	}
	public static String upgradeLevel (String level){
		for (int i =0; i<labels.length;i++){
			if(level.equals(labels[i])){
				return labels[i-1];
			}
		}
		return null;
	}
	public static String[] labels = new String[]{"A","B","C","D","E","F"};
	public static int[] maxLabels =  new int[]  {  1,  2,  4,  8, 16, 32};
}
