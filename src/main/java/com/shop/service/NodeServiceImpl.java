package com.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shop.dao.*;
import com.shop.model.*;

@Service
public class NodeServiceImpl implements NodeService {
	
	private NodeDAO nodeDAO;

	public void setNodeDAO(NodeDAO NodeDAO) {
		this.nodeDAO = NodeDAO;
	}
	@Override
	@Transactional
	public void addNode(Node p) {
		this.nodeDAO.addNode(p);
	}

	@Transactional
	public void updateNode(Node p) {
		this.nodeDAO.updateNode(p);
	}

	@Override
	@Transactional
	public List<Node> listNodes() {
		return this.nodeDAO.listNodes();
	}

	@Override
	@Transactional
	public Node getNodeById(int id) {
		return this.nodeDAO.getNodeById(id);
	}

	@Transactional
	public void removeNode(int id) {
		this.nodeDAO.removeNode(id);
	}
	@Override
	public void getTrees(String string) {
		// TODO Auto-generated method stub
	}

}
