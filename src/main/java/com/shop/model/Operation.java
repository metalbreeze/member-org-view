package com.shop.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

/**
 * Entity bean with JPA annotations
 * Hibernate provides JPA implementation
 * @author pankaj
 *
 */
@Entity
@Table(name="operations")
public class Operation {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	private User user;
	@ManyToOne
	private ReportCenter reportCenter;
	private String operation;
	private BigDecimal money;
	private BigDecimal moneyBefore;
	private BigDecimal moneyAfter;
	private Timestamp operationDate = new Timestamp(System.currentTimeMillis());
	private String remark;
	
	public Operation(){
		
	}
	public Operation(User u , ReportCenter r , String o, BigDecimal m){
		user=u;
		reportCenter=r;
		operation=o;
		money=m;
	}
	public Operation(User u , ReportCenter r , String o, int i){
		user=u;
		reportCenter=r;
		operation=o;
		money=new BigDecimal(i);
	}
	public Operation(User u , ReportCenter r , String o, BigDecimal m,String remark){
		user=u;
		reportCenter=r;
		operation=o;
		money=m;
		this.remark=remark;
	}
	public Operation(User u , ReportCenter r , String o, int i,String remark){
		user=u;
		reportCenter=r;
		operation=o;
		money=new BigDecimal(i);
		this.remark=remark;
	}
	public Operation(User u , ReportCenter r , String o, BigDecimal i,BigDecimal before, BigDecimal after,String remark){
		user=u;
		reportCenter=r;
		operation=o;
		money=i;
		this.remark=remark;
		moneyBefore=before;
		moneyAfter=after;
	}
	public Operation(User u , ReportCenter r , String o, int i,BigDecimal before, BigDecimal after,String remark){
		user=u;
		reportCenter=r;
		operation=o;
		money=new BigDecimal(i);
		this.remark=remark;
		moneyBefore=before;
		moneyAfter=after;
	}	
	@Override
	public String toString(){
		return "id="+id+", operation="+operation;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public Timestamp getOperationDate() {
		return operationDate;
	}

	public void setOperationDate(Timestamp operationDate) {
		this.operationDate = operationDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ReportCenter getReportCenter() {
		return reportCenter;
	}

	public void setReportCenter(ReportCenter reportCenter) {
		this.reportCenter = reportCenter;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	public void setMoney(int i) {
		this.money = new BigDecimal(i);
	}
	public BigDecimal getMoneyBefore() {
		return moneyBefore;
	}
	public void setMoneyBefore(BigDecimal moneyBefore) {
		this.moneyBefore = moneyBefore;
	}
	public BigDecimal getMoneyAfter() {
		return moneyAfter;
	}
	public void setMoneyAfter(BigDecimal moneyAfter) {
		this.moneyAfter = moneyAfter;
	}

}
