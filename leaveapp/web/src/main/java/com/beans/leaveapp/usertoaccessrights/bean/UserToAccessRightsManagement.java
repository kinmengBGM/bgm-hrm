package com.beans.leaveapp.usertoaccessrights.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.drools.core.spi.Enabled;

import org.primefaces.event.SelectEvent;

import com.beans.common.security.accessrights.model.AccessRights;
import com.beans.common.security.accessrights.service.AccessRightsService;
import com.beans.common.security.users.model.Users;
import com.beans.common.security.users.service.UsersNotFound;
import com.beans.common.security.users.service.UsersService;
import com.beans.common.security.usertoaccessrights.model.UserToAccessRights;
import com.beans.common.security.usertoaccessrights.service.UserToAccessRightsNotFound;
import com.beans.common.security.usertoaccessrights.service.UserToAccessRightsService;
import com.beans.leaveapp.accessrights.model.AccessRightsDataModel;
import com.beans.leaveapp.usertoaccessrights.model.UserToAccessRightsDataModel;
import com.beans.leaveapp.usertoaccessrights.model.UserToAssignedAccessRightsDataModel;
import com.beans.leaveapp.usertoaccessrights.model.UserToUnAssignedAccessRightsDataModel;


public class UserToAccessRightsManagement implements Serializable{

	private static final long serialVersionUID = 1L;
	private UsersService usersService;
	private AccessRightsService accessRightsService;
	private UserToAccessRightsService userToAccessRightsService;
	private List<Users> usersList;
	private UserToAccessRightsDataModel userToAccessRightsDataModel;
	private Users selectedUsers = new Users();
	private boolean insertDelete = false;
	private List<Users> searchUsers;
	private List<AccessRights> accessRightsList = new LinkedList<AccessRights>();	
	private UserToAssignedAccessRightsDataModel userToAssignedAccessRightsDataModel;
	private UserToAccessRights selectedUserToAccessRights = new UserToAccessRights();	
	private int userId;
	private List<UserToAccessRights> userToAccessRightsList = new LinkedList<UserToAccessRights>();
	private AccessRightsDataModel accessRightsDataModel;
    private boolean renderAccessRights = false;
    private AccessRights selectedAccessRights = new AccessRights();
    private boolean enabled;
    
	private int id;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	private String searchUsername = "";
	
	public UsersService getUsersService() {
		return usersService;
	}
	
	public void setUsersService(UsersService usersService) {
		this.usersService = usersService;
	}
	
	
	public AccessRightsService getAccessRightsService() {
		return accessRightsService;
	}
	
	public void setAccessRightsService(AccessRightsService accessRightsService) {
		this.accessRightsService = accessRightsService;
	}
	
	
	public List<Users> getUsersList() {
			if(usersList == null || insertDelete == true){
					usersList = usersService.findAll();
				}		
		return usersList;
	}
	
	public void setUsersList(List<Users> usersList) {
		this.usersList = usersList;
	}
	
	
	public UserToAccessRightsDataModel getUserToAccessRightsDataModel() {
		if(userToAccessRightsDataModel == null || insertDelete == true)
			userToAccessRightsDataModel = new UserToAccessRightsDataModel(getUsersList());		
		return userToAccessRightsDataModel;
	}
	
	public void setUserToAccessRightsDataModel(
			UserToAccessRightsDataModel userToAccessRightsDataModel) {
		this.userToAccessRightsDataModel = userToAccessRightsDataModel;
	}
	
	
	public Users getSelectedUsers() {
		return selectedUsers;
	}
	
	public void setSelectedUsers(Users selectedUsers) {				
		this.selectedUsers = selectedUsers;
	}
	
	
	public void assignedAccessRights(){
		userId = getSelectedUsers().getId();		
	}	

	public void assignAccessRightsToUser(){
		this.setRenderAccessRights(true);
	}
	
	public void onRowSelect(SelectEvent event){
		this.selectedUsers = ((Users) event.getObject());
		if(selectedUsers != null){
			List<UserToAccessRights> userToAccessRightsList1 = new ArrayList<UserToAccessRights>();
			userToAccessRightsList1 = this.getUserToAccessRightsService().findByUserId(selectedUsers.getId());
			this.setUserToAccessRightsList(userToAccessRightsList1);
			this.userToAssignedAccessRightsDataModel = null;
			this.setId(selectedUsers.getId());
			System.out.println(id);
		}		

	}	
	
	public boolean isInsertDelete() {
		return insertDelete;
	}
	
	public void setInsertDelete(boolean insertDelete) {
		this.insertDelete = insertDelete;
	}
	
	
	public List<Users> getSearchUsers() {
		return searchUsers;
	}
	
	public void setSearchUsers(List<Users> searchUsers) {
		this.searchUsers = searchUsers;
	}
	
	public String getSearchUsername() {
		return searchUsername;
	}
	public void setSearchUsername(String searchUsername) {
		this.searchUsername = searchUsername;
	}
	
	public void searchUser(){		
		if(getSearchUsername() == null || getSearchUsername().trim().equals("")){
			this.usersList = null;
			this.userToAccessRightsDataModel = null;			
		}else {
			this.usersList = usersService.findUsersByUsername(getSearchUsername());
			this.userToAccessRightsDataModel = null;
		}		
	}

	public UserToAssignedAccessRightsDataModel getUserToAssignedAccessRightsDataModel() throws Exception {	
		if(userToAssignedAccessRightsDataModel == null || insertDelete == true){
					
			userToAssignedAccessRightsDataModel = new UserToAssignedAccessRightsDataModel(this.userToAccessRightsList);
		}		
		return userToAssignedAccessRightsDataModel;
	}

	public void setUserToAssignedAccessRightsDataModel(
			UserToAssignedAccessRightsDataModel userToAssignedAccessRightsDataModel) {
		this.userToAssignedAccessRightsDataModel = userToAssignedAccessRightsDataModel;
	}

	public UserToAccessRightsService getUserToAccessRightsService() {
		return userToAccessRightsService;
	}

	public void setUserToAccessRightsService(
			UserToAccessRightsService userToAccessRightsService) {
		this.userToAccessRightsService = userToAccessRightsService;
	}
	
	@SuppressWarnings("unchecked")
	public List<AccessRights> getAccessRightsList() {
		List<AccessRights> accessRightsListArray = null;
	try {				
		
			List<AccessRights> list;
			list = userToAccessRightsService.findAllAccessRights();
			List<String> assigned = new LinkedList<String>();
			List<String> unAssigned = new LinkedList<String>();
			
        if(userToAccessRightsList.size() >0){
			for(UserToAccessRights u :userToAccessRightsList){
				String s = u.getAccessRights().getAccessRights();
				assigned.add(s);
			}
}
			if(list.size() >0 ){				
			
			for(AccessRights a : list){
				String s1 = a.getAccessRights();
				unAssigned.add(s1);
			}
		
			List<String> finalList = new LinkedList<String>();
			 finalList = (List<String>) CollectionUtils.disjunction(assigned, unAssigned);
			
			for(String s2 : finalList){
				for(AccessRights a:list){
					if(s2.trim().equalsIgnoreCase(a.getAccessRights().trim())){
						int i = 0;
						System.out.println(++i + "times");
						accessRightsList.add(new AccessRights(a.getId(), a.getAccessRights(), a.getDescription(), a.getCreatedBy(), a.getCreationTime(), a.getLastModifiedBy(), a.getLastModifiedTime(), a.isDeleted()));
						Set<AccessRights> a1 = new HashSet<AccessRights>(accessRightsList);
							accessRightsListArray = new ArrayList<AccessRights>(a1);						
						
						System.out.println(accessRightsListArray.size());
					}
				}				 
			 }
		}
		
		
	} catch (Exception e) {
		e.printStackTrace();
	}	
	this.setRenderAccessRights(true);	
	return accessRightsListArray;
}

	public void setAccessRightsList(List<AccessRights> accessRightsList) {
		this.accessRightsList = accessRightsList;
	}
	
	public AccessRightsDataModel getAccessRightsDataModel() {		
		if(accessRightsDataModel == null || insertDelete == true){
			accessRightsDataModel = new AccessRightsDataModel(getAccessRightsList());
		}		
		return accessRightsDataModel;
	}

	public void setAccessRightsDataModel(AccessRightsDataModel accessRightsDataModel) {
		this.accessRightsDataModel = accessRightsDataModel;
	}

	public void addAccessRights(){
		UserToAccessRights u = new UserToAccessRights();
		u.setAccessRights(selectedAccessRights);
		u.setUsers(selectedUsers);
		u.setDeleted(false);		
		userToAccessRightsList.add(u);
		userToAssignedAccessRightsDataModel = null;
		accessRightsList.remove(selectedAccessRights);
		accessRightsDataModel = null;
		
		System.out.println(accessRightsList.size());
		this.setRenderAccessRights(false);
		this.getAccessRightsDataModel();           
		System.out.println(userToAccessRightsList.size());
           } 
	
	
	public void onUnAssignedAccessRightsSelect(SelectEvent event){
		
		selectedAccessRights = (AccessRights)event.getObject();
		
	}
	
	public void saveUserToAccessRights(){
		try {			
			for(UserToAccessRights userToAccessRights1: userToAccessRightsList){			
				getUserToAccessRightsService().update(userToAccessRights1);
			}
		} catch (UserToAccessRightsNotFound e) {
			e.printStackTrace();
		}
	}
	
	public UserToAccessRights getSelectedUserToAccessRights() {
		return selectedUserToAccessRights;
	}

	public void setSelectedUserToAccessRights(UserToAccessRights selectedUserToAccessRights) {
		this.selectedUserToAccessRights = selectedUserToAccessRights;
	}

	public List<UserToAccessRights> getUserToAccessRightsList() {
		
		
		return userToAccessRightsList;
	}

	public void setUserToAccessRightsList(List<UserToAccessRights> userToAccessRightsList) {
		this.userToAccessRightsList = userToAccessRightsList;
	}

	public boolean isRenderAccessRights() {
		return renderAccessRights;
	}

	public void setRenderAccessRights(boolean renderAccessRights) {
		this.renderAccessRights = renderAccessRights;
	}

	public AccessRights getSelectedAccessRights() {
		return selectedAccessRights;
	}

	public void setSelectedAccessRights(AccessRights selectedAccessRights) {
		this.selectedAccessRights = selectedAccessRights;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}	

	
}
