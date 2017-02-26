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
@Table(name="user")
public class User {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)

	private int id;
	
	private String name;
	
	private String mobile;
	
	private String address;

	private String password;
//	private int available=1;
	
	private String level;
	private Integer position;
	@Column(name="register_date")
	private Timestamp registerDate = new Timestamp(System.currentTimeMillis());
	
	private String wechat;
	@Column(name="account_number")
	private String accountNumber;
	
	private String alipay;

	private String status;
	
	private BigDecimal money=new BigDecimal(0);
	private BigDecimal withdraw=new BigDecimal(0);
	
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	@ManyToOne
	private Group group;

	@OneToOne(mappedBy = "owner")
	private ReportCenter owner;
	
	@ManyToOne
	private ReportCenter reportCenter;
	
	@OneToMany(mappedBy = "user")
	private List<Profile> profiles;

	@OneToMany(mappedBy = "user")
	private List<Operation> operations;

	
	public List<Profile> getProfiles() {
		return profiles;
	}

	public void setProfiles(List<Profile> profiles) {
		this.profiles = profiles;
	}

	@ManyToOne
	private User parent ;

	public User getParent() {
		return parent;
	}

	public void setParent(User parent) {
		this.parent = parent;
	}

	public List<User> getChildren() {
		return children;
	}

	public void setChildren(List<User> children) {
		this.children = children;
	}

	@JsonView(View.Collection.class)
	@OneToMany(mappedBy = "parent")
	private List<User> children = new ArrayList<User>();
	
	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString(){
		return "id="+id+", name="+name;
	}

	public String getWechat() {
		return wechat;
	}

	public void setWechat(String wechat) {
		this.wechat = wechat;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAlipay() {
		return alipay;
	}

	public void setAlipay(String alipay) {
		this.alipay = alipay;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Timestamp registerDate) {
		this.registerDate = registerDate;
	}
	public static Map<String,String> statusMap=new HashMap<String,String>();
	static {
		statusMap.put("new", "新会员");
		statusMap.put("old", "会员");
	}
	public ReportCenter getReportCenter() {
		return reportCenter;
	}

	public void setReportCenter(ReportCenter reportCenter) {
		this.reportCenter = reportCenter;
	}

	public static Map<String, String> getStatusMap() {
		return statusMap;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int postion) {
		this.position = postion;
	}

	public ReportCenter getOwner() {
		return owner;
	}

	public void setOwner(ReportCenter owner) {
		this.owner = owner;
	}

	public List<Operation> getOperations() {
		return operations;
	}

	public void setOperations(List<Operation> operations) {
		this.operations = operations;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public void addMoney(int i) {
		if(money==null){
			money=new BigDecimal(i);
		}else{
			this.money.add(new BigDecimal(i));
		}
	}

	public BigDecimal getWithdraw() {
		return withdraw;
	}

	public void setWithdraw(BigDecimal withdraw) {
		this.withdraw = withdraw;
	}
}
