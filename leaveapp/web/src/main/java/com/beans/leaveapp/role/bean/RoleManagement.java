package com.beans.leaveapp.role.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

import com.beans.common.audit.service.AuditTrail;
import com.beans.common.audit.service.SystemAuditTrailActivity;
import com.beans.common.audit.service.SystemAuditTrailLevel;
import com.beans.common.security.role.model.Role;
import com.beans.common.security.role.service.RoleNotFound;
import com.beans.common.security.role.service.RoleService;
import com.beans.common.security.users.model.Users;
import com.beans.leaveapp.role.model.RoleDataModel;

public class RoleManagement implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	RoleService roleService;
	private List<Role> roleList;
	private RoleDataModel roleDataModel;
	private Role newRole = new Role();
	private Role selectedRole = new Role();
	private boolean insertDelete = false;
	private List<Role> searchRole;
	
	private String searchRoleName = "";
	
	private Users actorUsers;
	private AuditTrail auditTrail;
	
	
	
	public RoleService getRoleService() {
		return roleService;
	}
	
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	
	public List<Role> getRoleList() {
		
		if(roleList == null || insertDelete == true) {			
			roleList = roleService.findAll();
		}
		return roleList;
	}
	
	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}
	
	public RoleDataModel getRoleDataModel() {
		
		if(roleDataModel == null || insertDelete == true) {
			
			roleDataModel = new RoleDataModel(getRoleList());
		}
		
		return roleDataModel;
	}
	
	public void setRoleDataModel(RoleDataModel roleDataModel) {
		this.roleDataModel = roleDataModel;
	}
	
	public Role getNewRole() {
		return newRole;
	}
	
	public void setNewRole(Role newRole) {
		this.newRole = newRole;
	}
	
	
	public void doCreateRole() {
		newRole.setDeleted(false);
		getRoleService().create(newRole);
		setInsertDelete(true);	
		
		auditTrail.log(SystemAuditTrailActivity.CREATED, SystemAuditTrailLevel.INFO, getActorUsers().getId(), getActorUsers().getUsername(), getActorUsers().getUsername() + " has Created Role " + newRole.getRole());
		
		System.out.println("Actor Id: " +getActorUsers().getId());
	}
	
	public Role getSelectedRole() {
		return selectedRole;
	}
	
	public void setSelectedRole(Role selectedRole) {
		this.selectedRole = selectedRole;
	}
	
	
	public void doUpdateRole() {
		try {
			System.out.println("New Role:" + selectedRole.getRole());
			System.out.println("Id:" + selectedRole.getId());
			System.out.println("Description:" + selectedRole.getDescription());
			getRoleService().update(selectedRole);
			
			auditTrail.log(SystemAuditTrailActivity.UPDATED, SystemAuditTrailLevel.INFO, getActorUsers().getId(), getActorUsers().getUsername(), getActorUsers().getUsername() + " has updated Role " + selectedRole.getRole() + " with id " + selectedRole.getId());
			
		} catch (RoleNotFound e) {
			FacesMessage msg = new FacesMessage("Error", "Role With id: " + selectedRole.getId() + " not found!");  
			FacesContext.getCurrentInstance().addMessage(null, msg);  
		}		
	}
	
	public void onRowSelect(SelectEvent event) { 
		setSelectedRole((Role)event.getObject());
		 FacesMessage msg = new FacesMessage("Role Selected", selectedRole.getRole());  
	        
	        FacesContext.getCurrentInstance().addMessage(null, msg); 	
	}
	
	public void doDeleteRole(){
		try {
			getRoleService().delete(selectedRole.getId());
			
			auditTrail.log(SystemAuditTrailActivity.DELETED, SystemAuditTrailLevel.INFO, getActorUsers().getId(), getActorUsers().getUsername(), getActorUsers().getUsername() + " has Deleted Role " + selectedRole.getRole() + " with id " + selectedRole.getId());	
		} catch (RoleNotFound e) {
			FacesMessage msg = new FacesMessage("Error", "Role With id: " + selectedRole.getId() + " not found!");  
			FacesContext.getCurrentInstance().addMessage(null, msg);  
		}	
		setInsertDelete(true);
	}
	
	public List<Role> getSearchRole() {
		return searchRole;
	}
	
	public void setSearchRole(List<Role> searchRole) {
		this.searchRole = searchRole;
	}

	public boolean isInsertDelete() {
		return insertDelete;
	}

	public void setInsertDelete(boolean insertDelete) {
		this.insertDelete = insertDelete;
	}

	public String getSearchRoleName() {
		return searchRoleName;
	}

	public void setSearchRoleName(String searchRoleName) {
		this.searchRoleName = searchRoleName;
	}	
	
	public void searchRole(){
		
		if(getSearchRoleName() == null || getSearchRoleName().trim().equals("")){
			this.roleList = null;
			this.roleDataModel = null;			
		}else{
			this.roleList = roleService.findRoleByRoleName(getSearchRoleName());
			this.roleDataModel = null;		
		}
		
	}

	public Users getActorUsers() {
		return actorUsers;
	}

	public void setActorUsers(Users actorUsers) {
		this.actorUsers = actorUsers;
	}

	public AuditTrail getAuditTrail() {
		return auditTrail;
	}

	public void setAuditTrail(AuditTrail auditTrail) {
		this.auditTrail = auditTrail;
	}
	
	

}

