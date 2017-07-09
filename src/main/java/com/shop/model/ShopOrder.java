package com.shop.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entity bean with JPA annotations
 * Hibernate provides JPA implementation
 * @author pankaj
 *
 */
@Entity
@Table(name="shop_order")
public class ShopOrder {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private BigDecimal totalPrice;
	
	private BigDecimal cost;
	
	@ManyToOne
	private User user;
	
	private Timestamp packageSendDate;
	private Timestamp paymentDate;
	
	@OneToMany
	private List<ShopOrderItem> shopOrderItems;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Timestamp getPackageSendDate() {
		return packageSendDate;
	}

	public void setPackageSendDate(Timestamp packageSendDate) {
		this.packageSendDate = packageSendDate;
	}

	public Timestamp getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Timestamp paymentDate) {
		this.paymentDate = paymentDate;
	}

	public List<ShopOrderItem> getShopOrderItems() {
		return shopOrderItems;
	}

	public void setShopOrderItems(List<ShopOrderItem> shopOrderItems) {
		this.shopOrderItems = shopOrderItems;
	}
}
