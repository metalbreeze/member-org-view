package com.shop.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shop.base.BaseObject;
import com.shop.model.ReportCenter;

@Repository
public class ReportCenterDAOImpl extends BaseObject implements ReportCenterDAO {
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}

	@Override
	public void addReportCenter(ReportCenter p) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(p);
		logger.info("ReportCenter saved successfully, ReportCenter Details="+p);
	}

	@Override
	public void updateReportCenter(ReportCenter p) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(p);
		logger.info("ReportCenter updated successfully, ReportCenter Details="+p);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ReportCenter> listReportCenters() {
		Session session = this.sessionFactory.getCurrentSession();
		List<ReportCenter> ReportCentersList = session.createQuery("from ReportCenter").list();
		return ReportCentersList;
	}

	@Override
	public ReportCenter getReportCenterById(int id) {
		Session session = this.sessionFactory.getCurrentSession();		
		ReportCenter p = (ReportCenter) session.load(ReportCenter.class, new Integer(id));
		logger.info("ReportCenter loaded successfully, ReportCenter details="+p);
		return p;
	}

	@Override
	public void removeReportCenter(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		ReportCenter p = (ReportCenter) session.load(ReportCenter.class, new Integer(id));
		if(null != p){
			session.delete(p);
		}
		logger.info("ReportCenter deleted successfully, ReportCenter details="+p);
	}

	@Override
	public List<ReportCenter> getReportCenterByOwnerId(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
