package com.shop.dao;

import java.math.BigDecimal;
import java.util.List;

import com.shop.model.ReportCenter;
import com.shop.model.User;

public interface ReportCenterDAO {

	public void addReportCenter(ReportCenter p);
	public void updateReportCenter(ReportCenter p);
	public List<ReportCenter> listReportCenters();
	public ReportCenter getReportCenterById(int id);
	public void removeReportCenter(int id);
	public ReportCenter getReportCenterByOwnerId(int id);
	public BigDecimal getReportCenterCost();
	public List<ReportCenter>  listWithdrawStatus(int withdraw_status,String id,String order);
}
