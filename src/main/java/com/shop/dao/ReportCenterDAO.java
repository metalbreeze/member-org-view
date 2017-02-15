package com.shop.dao;

import java.util.List;

import com.shop.model.ReportCenter;
import com.shop.model.User;

public interface ReportCenterDAO {

	public void addReportCenter(ReportCenter p);
	public void updateReportCenter(ReportCenter p);
	public List<ReportCenter> listReportCenters();
	public ReportCenter getReportCenterById(int id);
	public void removeReportCenter(int id);
	public List<ReportCenter> getReportCenterByOwnerId(int id);
}
