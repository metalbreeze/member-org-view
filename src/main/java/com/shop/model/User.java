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
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonView;

/**
 * Entity bean with JPA annotations
 * Hibernate provides JPA implementation
 * @author pankaj
 *
 */
@Entity
@Table(name="user")
public class User implements Comparable<User> {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)

	private int id;
	
	private String name;
	
	private String mobile;
	
	private String address;

	private String password;
	@Transient
	private String password_2;
	
	private String level;
	private Integer position;
	@Column(name="register_date")
	private Timestamp registerDate = new Timestamp(System.currentTimeMillis());
	private Timestamp activeDate;
	
	private String wechat;
	@Column(name="account_number")
	private String accountNumber;
	
	private String alipay;

	private String status;
	
	private Integer product_id;
	
	private BigDecimal bonusMoney=new BigDecimal(0);
	private BigDecimal feedbackMoney=new BigDecimal(0);
	private BigDecimal withdraw=new BigDecimal(0);
	private BigDecimal withdrawRequest=new BigDecimal(0);
	private Integer withdrawStatus;
	private Timestamp withdrawDate;
	private BigDecimal saleMoney=new BigDecimal(0);
	private BigDecimal reGroupMoney=new BigDecimal(0);
	private BigDecimal groupScore=new BigDecimal(0);
	private BigDecimal personalScore=new BigDecimal(0);
	private BigDecimal directScore=new BigDecimal(0);
	private String withdrawReason;
	private int orderStatus = 0 ;
	private Timestamp orderSendDate;
	private Integer available;
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

	public BigDecimal getBonusMoney() {
		return bonusMoney;
	}

	public void setBonusMoney(BigDecimal money) {
		this.bonusMoney = money;
	}

	public void addBonusMoney(int i) {
		if(bonusMoney==null){
			bonusMoney=new BigDecimal(i);
		}else{
			bonusMoney=bonusMoney.add(new BigDecimal(i));
		}
	}
	public void addBonusBeforeMoneyByLevel(String s) {
		if(bonusMoney==null){
			bonusMoney=new BigDecimal(0);
		}
		for (int j = 0; j < Group.labels.length; j++) {
			if(Group.labels[j].equalsIgnoreCase(s)){
				bonusMoney=bonusMoney.add(new BigDecimal(Group.levelAllBeforeMoney[j]));
			}
		}
	}
	public void addFeedbackMoney(int i) {
		if(feedbackMoney==null){
			feedbackMoney=new BigDecimal(i);
		}else{
			feedbackMoney=feedbackMoney.add(new BigDecimal(i));
		}
	}
	public void addSaleMoney(int i) {
		if(saleMoney==null){
			saleMoney=new BigDecimal(i);
		}else{
			saleMoney=saleMoney.add(new BigDecimal(i));
		}
	}
	public BigDecimal getWithdraw() {
		return withdraw;
	}

	public void setWithdraw(BigDecimal withdraw) {
		this.withdraw = withdraw;
	}

	public BigDecimal getGroupScore() {
		return groupScore;
	}

	public void setGroupScore(BigDecimal groupScore) {
		this.groupScore = groupScore;
	}

	public BigDecimal getPersonalScore() {
		return personalScore;
	}

	public void setPersonalScore(BigDecimal personalScore) {
		this.personalScore = personalScore;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public BigDecimal getDirectScore() {
		return directScore;
	}

	public void setDirectScore(BigDecimal directScore) {
		this.directScore = directScore;
	}

	public BigDecimal getFeedbackMoney() {
		return feedbackMoney;
	}

	public void setFeedbackMoney(BigDecimal feedbackMoney) {
		this.feedbackMoney = feedbackMoney;
	}

	public BigDecimal getSaleMoney() {
		return saleMoney;
	}

	public void setSaleMoney(BigDecimal saleMoney) {
		this.saleMoney = saleMoney;
	}

	public String getPassword_2() {
		return password_2;
	}

	public void setPassword_2(String password_2) {
		this.password_2 = password_2;
	}

	public Integer getProduct_id() {
		return product_id;
	}

	public void setProduct_id(Integer product_id) {
		this.product_id = product_id;
	}

	@Override
	public int compareTo(User o) {
		return position-o.position; 
	}
	public BigDecimal getAccountRemain(){
		return getAccountTotal().add(this.withdraw.negate());
	}
	public BigDecimal getAccountTotal(){
		return this.saleMoney .add ( this.bonusMoney .add(this.feedbackMoney ) .multiply(new BigDecimal( 0.8)));
	}
	public int getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}

	public BigDecimal getWithdrawRequest() {
		return withdrawRequest;
	}

	public void setWithdrawRequest(BigDecimal withdrawRequest) {
		this.withdrawRequest = withdrawRequest;
	}


	public Timestamp getWithdrawDate() {
		return withdrawDate;
	}

	public void setWithdrawDate(Timestamp withdrawDate) {
		this.withdrawDate = withdrawDate;
	}

	public Integer getWithdrawStatus() {
		return withdrawStatus;
	}

	public void setWithdrawStatus(Integer withdrawStatus) {
		this.withdrawStatus = withdrawStatus;
	}

	public void addWithdraw(BigDecimal withdrawRequest2) {
		withdraw=withdraw.add(withdrawRequest2);
	}

	public Timestamp getActiveDate() {
		return activeDate;
	}

	public void setActiveDate(Timestamp activeDate) {
		this.activeDate = activeDate;
	}

	public Integer getAvailable() {
		return available;
	}

	public void setAvailable(Integer available) {
		this.available = available;
	}

	public String getWithdrawReason() {
		return withdrawReason;
	}

	public void setWithdrawReason(String withdrawReason) {
		this.withdrawReason = withdrawReason;
	}

	public BigDecimal getReGroupMoney() {
		return reGroupMoney;
	}

	public void setReGroupMoney(BigDecimal reGroupMoney) {
		this.reGroupMoney = reGroupMoney;
	}

	public void addReGroupMoney(Product productById) {
		if(reGroupMoney==null){
			reGroupMoney=new BigDecimal(0);
		}
		reGroupMoney=reGroupMoney.add(productById.getCost());
	}

	public Timestamp getOrderSendDate() {
		return orderSendDate;
	}

	public void setOrderSendDate(Timestamp orderSendDate) {
		this.orderSendDate = orderSendDate;
	}

}
