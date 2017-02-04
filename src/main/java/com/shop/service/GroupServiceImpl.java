package com.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shop.dao.*;
import com.shop.model.*;

@Service
public class GroupServiceImpl implements GroupService {
	
	private com.shop.dao.GroupDAO groupDAO;

	public void setGroupDAO(GroupDAO groupDAO) {
		this.groupDAO = groupDAO;
	}

	@Override
	@Transactional
	public void addGroup(Group p) {
		this.groupDAO.addGroup(p);
	}

	@Override
	@Transactional
	public void updateGroup(Group p) {
		this.groupDAO.updateGroup(p);
	}

	@Override
	@Transactional
	public List<Group> listGroups() {
		return this.groupDAO.listGroups();
	}

	@Override
	@Transactional
	public Group getGroupById(int id) {
		return this.groupDAO.getGroupById(id);
	}

	@Override
	@Transactional
	public void removeGroup(int id) {
		this.groupDAO.removeGroup(id);
	}

//	@Override
//	@Transactional
//	public Group listGroupWithUsers(int id) {
//		return groupDAO.listGroupWithUsers(id);
//	}
}
