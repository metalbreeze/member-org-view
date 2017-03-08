package com.shop.dao;

import java.math.BigDecimal;
import java.util.List;

import com.shop.model.Group;
import com.shop.model.User;

public interface UserDAO {

	public void addUser(User p);
	public void updateUser(User p);
	public List<User> listUsers();
	public User getUserById(int id);
	public void removeUser(int id);
	public List<User> listAvailableUsers();
	public User getUserByName(String s);
	public List<User> listAvailableReporterCenterUsers();
	public List<User> getUserByReportCenter(int i);
	public int getCurrentPosiztionByGroup(Group group,String Level);
	public List<User> getNoParenetUsers();
	public BigDecimal getChildrenGroupScore(User u);
	public int getOldUser();
	public BigDecimal getWithdraw();
	List<User> listOldUsers();
	List<User> listUserOrderBySaleMoney(int count);
	void saveWithId(User p, int i);
}
