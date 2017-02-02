package com.shop.dao;

import java.util.List;

import com.shop.model.Group;

public interface GroupDAO {

	public void addGroup(Group p);
	public void updateGroup(Group p);
	public List<Group> listGroups();
	public List<Group> listAvaiableGroups();
	public Group getGroupById(int id);
	public void removeGroup(int id);
	public Group listGroupWithUsers(int id);
}
