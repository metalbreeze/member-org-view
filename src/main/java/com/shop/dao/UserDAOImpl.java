package com.shop.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.ReplicationMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shop.model.Group;
import com.shop.model.User;
import com.shop.service.ProductService;

@Repository
public class UserDAOImpl implements UserDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}

	@Override
	public void addUser(User p) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(p);
		logger.info("User saved successfully, User Details="+p);
	}
	@Override
	public void saveWithId(User p,int i) {
		Session session = this.sessionFactory.getCurrentSession();
		session.save("user",p);
		logger.info("User saved successfully, User Details="+p);
	}

	@Override
	public void updateUser(User p) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(p);
		session.flush();
		logger.info("User updated successfully, User Details="+p);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> listUsers() {
		Session session = this.sessionFactory.getCurrentSession();
		List<User> UsersList = session.createQuery("from User").list();
		return UsersList;
	}

	@Override
	public User getUserById(int id) {
		Session session = this.sessionFactory.getCurrentSession();		
		User p = (User) session.load(User.class, new Integer(id));
		logger.info("User loaded successfully, User details="+p);
		return p;
	}

	@Override
	public void removeUser(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		User p = (User) session.load(User.class, new Integer(id));
		if(null != p){
			session.delete(p);
		}
		logger.info("User deleted successfully, User details="+p);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<User> listAvailableUsers() {
		Session session = this.sessionFactory.getCurrentSession();
		List<User> UsersList = session.createQuery("from User where group_id is null or group_id=0").list();
		return UsersList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<User> listUserOrderBySaleMoney(int count) {
		Session session = this.sessionFactory.getCurrentSession();
		List<User> list =session.createQuery("from User where id not in (1,2,3,18) order by saleMoney desc").setMaxResults(count).list();
		return list;
	}
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<User> listAvailableReporterCenterUsers() {
		Session session = this.sessionFactory.getCurrentSession();
		List<User> UsersList = session.createQuery("from User where reportCenter_id is null or reportCenter_id=0").list();
		return UsersList;
	}
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<User> listOldUsers() {
		Session session = this.sessionFactory.getCurrentSession();
		List<User> UsersList = session.createQuery("from User where status='old'").list();
		return UsersList;
	}
	@Override
	@Transactional
	public User getUserByName(String s) {
		Session session = this.sessionFactory.getCurrentSession();
		 User u = (User)session.createQuery("from User where name=:name").setString("name", s).uniqueResult();
		 logger.debug("getUserByName"+s);
		return u;
	}
	@Override
	@Transactional
	public User getUserByNameWithChildren(String s) {
		Session session = this.sessionFactory.getCurrentSession();
		 User u = (User)session.createQuery("from User where name=:name").setString("name", s).uniqueResult();
		 Hibernate.initialize(u.getChildren());
		 logger.debug("getUserByName"+s);
		return u;
	}
	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public List<User> getUserByReportCenter(int i) {
		Session session = this.sessionFactory.getCurrentSession();
		List<User> l = (List<User>)session.createQuery("from User where reportCenter_id=:id").setInteger("id", i).list();
		 logger.debug("getUserByReportCenter"+i);
		return l;
	}

	@Override
	public int getCurrentPosiztionByGroup(Group group, String Level) {
		Session session = this.sessionFactory.getCurrentSession();
		Long l = (Long) session.createQuery("select count(*) from User where group_id=:id and level=:level")
					.setInteger("id", group.getId())
					.setString("level", Level).uniqueResult();
		return l.intValue();
	}
	
	@Override
	public List<User> getNoParenetUsers() {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
//		List<User> l =  session.createQuery("SELECT t1 FROM User t1 LEFT JOIN t1.parent t2 WHERE t2.id is NULL")
//					       .list();
		List<User> l =  session.createQuery("SELECT t1 FROM User t1 where t1.id=20")
			       .list();
		//个人
		Hashtable<Integer, BigDecimal> person = new Hashtable<Integer, BigDecimal>();
		//团队
		Hashtable<Integer, BigDecimal> group = new Hashtable<Integer, BigDecimal>();
		for (Iterator iterator = l.iterator(); iterator.hasNext();) {
			User user = (User) iterator.next();
			if(user.getChildren().size()==0){
				
			}else{
				
			}
			User parent = user.getParent();
			BigDecimal personScore=person.get(parent.getId());
			if(personScore==null){
				personScore=parent.getBonusMoney();
				person.put(parent.getId(), personScore);
			}else{
				person.put(parent.getId(),user.getBonusMoney().add(personScore));
			}
		}
		return l;
	}
	@Override
	public BigDecimal getChildrenGroupScore(User u ){
		Session session = this.sessionFactory.getCurrentSession();
//		BigDecimal b = u.getPersonalScore()==null?new BigDecimal(0):u.getPersonalScore();
		BigDecimal b=new BigDecimal(999);
		BigDecimal c = b;
		List<User> l = u.getChildren();
		logger.debug("id"+u.getId()+"begin b"+b+"c"+c);
		for (Iterator iterator = l.iterator(); iterator.hasNext();) {
			User user = (User) iterator.next();
			BigDecimal ds = getChildrenGroupScore(user);
			logger.debug("id"+user.getId()+"b."+b+"c."+c+"ds."+ds);
			b=b.add(ds);
			c=c.add(user.getPersonalScore()==null?new BigDecimal(0):user.getPersonalScore());
		}
		u.setGroupScore(b);
		u.setPersonalScore(c);
		logger.debug("id"+u.getId()+"setGroup"+b+"Person"+c);
		session.update(u);
		return b;
	}
	@Override
	public int getOldUser(){
		Session session = this.sessionFactory.getCurrentSession();
		Long i = (Long) session.createQuery("SELECT count(*) FROM User t1 where t1.status='old'").uniqueResult();
		return i.intValue();
	}
	@Override
	public BigDecimal getWithdraw(){
		Session session = this.sessionFactory.getCurrentSession();
		BigDecimal i = (BigDecimal) session.createQuery("SELECT sum(withdraw) FROM User t1 where t1.status='old'").uniqueResult();
		return i;
	}	
	//	Hashtable<User, BigDecimal> handleUp(Hashtable<User, BigDecimal> ht){
//		Hashtable<User, BigDecimal> person = new Hashtable<User, BigDecimal> ();
//		for (Iterator iterator = ht.keySet().iterator(); iterator.hasNext();) {
//			User u = (User) iterator.next();
//			if(u.getParent()==null){
//				u.
//			}else{
//				person.put(u.getParent(), value)
//			}
//			
//		}
//		
//		return person;
//	}
//	
	@Override
	public void refresh(User parent) {
		Session session = this.sessionFactory.getCurrentSession();	
		session.refresh(parent);
	}

	@Override
	public List<User> getOrderList(int i) {
		Session session = this.sessionFactory.getCurrentSession();
		List<User> l = session.createQuery("FROM User  where product_id=:id and orderStatus=:send order by id ")
				.setInteger("id", i).setInteger("send", ProductService.order_init).list();
		return l;
	}

	@Override
	public List<User> getOldOrderList(int i) {
		Session session = this.sessionFactory.getCurrentSession();
		List<User> l = session.createQuery("FROM User  where product_id=:id and orderStatus=:send order by id ")
				.setInteger("id", i).setInteger("send", ProductService.order_send).list();
		return l;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAvailableUserList() {
		Session session = this.sessionFactory.getCurrentSession();
		List<User> UsersList = session.createQuery("from User where status='old' ").list();
		return UsersList;
	}
}
