package com.shop.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

/**
 * Entity bean with JPA annotations
 * Hibernate provides JPA implementation
 * @author pankaj
 *
 */
@Entity
@Table(name="report_center")
public class ReportCenter {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)

	private int id;
	
	private String name;
	
	private BigDecimal money1=new BigDecimal(0);
	private BigDecimal money2=new BigDecimal(0);	
	
	private BigDecimal electricMoney=new BigDecimal(0);
	private BigDecimal withdraw=new BigDecimal(0);
	@OneToOne
	@JoinColumn(name="user_id")
	private User owner;
	
	@OneToMany(mappedBy = "reportCenter" )
	private List<User> members;

	@OneToMany(mappedBy = "reportCenter" )
	private List<Operation> operations;

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

	public BigDecimal getMoney1() {
		return money1;
	}

	public void setMoney1(BigDecimal money) {
		this.money1 = money;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public List<User> getMembers() {
		return members;
	}

	public void setMembers(List<User> members) {
		this.members = members;
	}

	public List<Operation> getOperations() {
		return operations;
	}

	public void setOperations(List<Operation> operations) {
		this.operations = operations;
	}

	public BigDecimal getElectricMoney() {
		return electricMoney;
	}

	public void setElectricMoney(BigDecimal electricMoney) {
		this.electricMoney = electricMoney;
	}

	public BigDecimal getMoney2() {
		return money2;
	}

	public void setMoney2(BigDecimal money2) {
		this.money2 = money2;
	}
	public void addMoney1(int i){
		if(money1==null){
			money1=new BigDecimal(i);
		}else{
			money1=money1.add(new BigDecimal(i));
		}
	}
	public void addMoney2(int i){
		if(money2==null){
			money2=new BigDecimal(i);
		}else{
			money2=money2.add(new BigDecimal(i));
		}
	}

	public BigDecimal getWithdraw() {
		return withdraw;
	}

	public void setWithdraw(BigDecimal withdraw) {
		this.withdraw = withdraw;
	}
}
