package com.shop.service;

import java.util.List;

import com.shop.model.Node;



public interface NodeService {

	public List<Node> listNodes();
	public Node getNodeById(int id);
	public void addNode(Node p);
	public void getTrees(String string);
}
