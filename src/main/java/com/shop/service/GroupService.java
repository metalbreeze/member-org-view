package com.shop.service;

import java.util.List;

import com.shop.model.Group;



public interface GroupService {

	public void addGroup(Group p);
	public void updateGroup(Group p);
	public List<Group> listGroups();
	public Group getGroupById(int id);
	public void removeGroup(int id);
	public Group listGroupWithUsers(int id);
}
