package com.shop.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.shop.model.Node;

@Repository
public class NodeDAOImpl implements NodeDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(NodeDAOImpl.class);

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}

	@Override
	public void addNode(Node p) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(p);
		logger.info("Node saved successfully, Node Details="+p);
	}

	@Override
	public void updateNode(Node p) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(p);
		logger.info("Node updated successfully, Node Details="+p);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Node> listNodes() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Node> NodesList = session.createQuery("from Node").list();
		for(Node p : NodesList){
			logger.info("Node List::"+p);
		}
		return NodesList;
	}

	@Override
	public Node getNodeById(int id) {
		Session session = this.sessionFactory.getCurrentSession();		
		Node p = (Node) session.load(Node.class, new Integer(id));
		logger.info("Node loaded successfully, Node details="+p);
		return p;
	}

	@Override
	public void removeNode(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Node p = (Node) session.load(Node.class, new Integer(id));
		if(null != p){
			session.delete(p);
		}
		logger.info("Node deleted successfully, Node details="+p);
	}

}
