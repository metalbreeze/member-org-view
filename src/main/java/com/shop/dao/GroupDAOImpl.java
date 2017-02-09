package com.shop.dao;

import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shop.model.Group;

@Repository
public class GroupDAOImpl implements GroupDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(GroupDAOImpl.class);

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}

	@Override
	public void addGroup(Group p) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(p);
		logger.info("Group saved successfully, Group Details="+p);
	}

	@Override
	public void updateGroup(Group p) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(p);
		logger.info("Group updated successfully, Group Details="+p);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	
	
	
	
	public List<Group> listGroups() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Group> GroupsList = session.createQuery("from Group").list();
		return GroupsList;
	}

	@Override
	public Group getGroupById(int id) {
		Session session = this.sessionFactory.getCurrentSession();		
		Group p = (Group) session.load(Group.class, new Integer(id));
		logger.info("Group loaded successfully, Group details="+p);
		return p;
	}

	@Override
	public void removeGroup(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Group p = (Group) session.load(Group.class, new Integer(id));
		if(null != p){
			session.delete(p);
		}
		logger.info("Group deleted successfully, Group details="+p);
	}

	@Override
	public List<Group> listAvaiableGroups() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Group> GroupsList = session.createQuery("from Group where node_id is null").list();
		return GroupsList;
	}

//	@SuppressWarnings("unchecked")
//	@Override
//	public Group listGroupWithUsers(int id ) {
//		Session session = this.sessionFactory.getCurrentSession();
//		Group g = (Group) session.load(Group.class, new Integer(id));
//		for (int i = 1 ; i<=6; i++){
//			logger.info("Group Listxxxx:");	
//			List l = session.createSQLQuery("SELECT user_id FROM group1_user where group_id="+id+" and level="+i).list();
//			logger.info(l.toString());
//			if(l.size()!=0){
//				List lu= session.createQuery("from User where id in (:ids)").setParameterList("ids", l).list();
//				g.getLevelUsers().put(i, lu);
//			}
//		}
//		logger.info("Group List::xxxxxxxxxxxxxxx"+g);
//		return g;
//	}
	
	
}
