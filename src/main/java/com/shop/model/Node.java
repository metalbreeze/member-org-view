package com.shop.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;


/**
 * Entity bean with JPA annotations
 * Hibernate provides JPA implementation
 * @author pankaj
 *
 */
@Entity
@Table(name="node")
public class Node {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
    @ManyToOne(fetch=FetchType.EAGER)
    private Node parent;

    @OneToMany(fetch=FetchType.EAGER,mappedBy = "parent")
    private java.util.Set<Node> children = new java.util.HashSet<Node>();

    @OneToOne(fetch=FetchType.EAGER)
	private User user;
	
	
	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public java.util.Set<Node> getChildren() {
		return children;
	}

	public void setChildren(java.util.Set<Node> children) {
		this.children = children;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString(){
		return "id="+id+", user="+user;
	}
}
