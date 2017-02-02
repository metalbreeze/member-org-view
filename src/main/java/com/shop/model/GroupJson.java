package com.shop.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.shop.model.View;

public class GroupJson {
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public NodeJson getTree() {
		return tree;
	}
	public void setTree(NodeJson tree) {
		this.tree = tree;
	}
	@JsonView(View.Simple.class)
	public String group;
	@JsonView(View.Simple.class)
	public NodeJson tree;
}
