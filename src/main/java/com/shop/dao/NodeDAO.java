package com.shop.dao;

import java.util.List;

import com.shop.model.Node;

public interface NodeDAO {

	public void addNode(Node p);
	public void updateNode(Node p);
	public List<Node> listNodes();
	public Node getNodeById(int id);
	public void removeNode(int id);
}
