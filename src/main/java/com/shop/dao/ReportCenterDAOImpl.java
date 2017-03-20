package com.shop.dao;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shop.base.BaseObject;
import com.shop.model.ReportCenter;
import com.shop.model.User;

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
		session.flush();
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
	public ReportCenter getReportCenterByOwnerId(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		ReportCenter p = (ReportCenter) session.createQuery("from ReportCenter where user_id = :id").setInteger("id", id).uniqueResult();
		return p;
	}
	@Override
	public BigDecimal getReportCenterCost() {
		Session session = this.sessionFactory.getCurrentSession();
		BigDecimal p = (BigDecimal) session.createQuery("select sum(money1+money2) from ReportCenter").uniqueResult();
		return p;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<ReportCenter> listWithdrawStatus(int withdraw_status) {
		Session session = this.sessionFactory.getCurrentSession();
		List<ReportCenter> ReportCentersList = session.createQuery("from ReportCenter where withdrawStatus=:withdraw_status")
				.setInteger("withdraw_status", withdraw_status).list();
		return ReportCentersList;
	}
}
