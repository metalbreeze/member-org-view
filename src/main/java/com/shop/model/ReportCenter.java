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
import javax.persistence.Transient;

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
	@Transient
	private BigDecimal electricMoney2=new BigDecimal(0);
	private BigDecimal withdraw=new BigDecimal(0);
	private BigDecimal withdrawRequest=new BigDecimal(0);
	private Integer withdrawStatus;
	private Timestamp withdrawDate;
	
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

	public BigDecimal getWithdrawRequest() {
		return withdrawRequest;
	}

	public void setWithdrawRequest(BigDecimal withdrawRequest) {
		this.withdrawRequest = withdrawRequest;
	}

	public Integer getWithdrawStatus() {
		return withdrawStatus;
	}

	public void setWithdrawStatus(Integer withdrawStatus) {
		this.withdrawStatus = withdrawStatus;
	}

	public Timestamp getWithdrawDate() {
		return withdrawDate;
	}

	public void setWithdrawDate(Timestamp withdrawDate) {
		this.withdrawDate = withdrawDate;
	}
	public void addWithdraw(BigDecimal withdrawRequest2) {
		withdraw=withdraw.add(withdrawRequest2);
	}
	public BigDecimal getAccountRemain(){
		return money1.add(money2).add(withdraw.negate());
	}

	public BigDecimal getElectricMoney2() {
		return electricMoney2;
	}

	public void setElectricMoney2(BigDecimal electricMoney2) {
		this.electricMoney2 = electricMoney2;
	}
	public void addElectricMoney(BigDecimal b){
		electricMoney=electricMoney.add(b);
	}
	
}
