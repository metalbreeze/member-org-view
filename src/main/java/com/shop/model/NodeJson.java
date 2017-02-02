package com.shop.model;

import java.util.Iterator;

import com.fasterxml.jackson.annotation.JsonView;

public class NodeJson {
	@JsonView(View.Simple.class)
	private int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String userName) {
		this.name = userName;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int i) {
		this.uid = i;
	}
	public java.util.Set<NodeJson> getChildren() {
		return children;
	}
	public void setChildren(java.util.Set<NodeJson> children) {
		this.children = children;
	}
	@JsonView(View.Simple.class)
	private String name;
	@JsonView(View.Simple.class)
	private int uid;
	@JsonView(View.Simple.class)
    private java.util.Set<NodeJson> children = new java.util.HashSet<NodeJson>();
    public static NodeJson transform(Node n){
    	NodeJson nj = new NodeJson();
    	nj.setId(n.getId());
    	nj.setUid(n.getUser().getId());
    	nj.setName(n.getUser().getName());
    	if (! n.getChildren().isEmpty()){
    		Iterator<Node> iterator = n.getChildren().iterator();
    		if(iterator.hasNext()){
    			nj.getChildren().add(transform(iterator.next()));
    		}
    	}
    	return nj;   	
    }
}
