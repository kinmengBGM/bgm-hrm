package com.beans.leaveapp.employeeprofile.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

import com.beans.common.security.users.model.Users;
import com.beans.leaveapp.address.model.Address;
import com.beans.leaveapp.address.service.AddressService;
import com.beans.leaveapp.employee.model.Employee;
import com.beans.leaveapp.employee.service.EmployeeNotFound;
import com.beans.leaveapp.employee.service.EmployeeService;
import com.beans.leaveapp.employeeprofile.model.AddressDataModel;
import com.beans.leaveapp.employeeprofile.model.EmployeeProfileDataModel;


public class EmployeeProfileManagementBean implements Serializable{
	private static final long serialVersionUID = 1L;
	private EmployeeService employeeService;
	private AddressService addressService;
	private List<Employee> employeeList;
	private EmployeeProfileDataModel employeeProfileDataModel;
	private Employee newEmployee = new Employee();
	private Employee selectedEmployee = new Employee();
	private boolean insertDelete = false;
	private boolean insertDeleteAddress = false;
	private List<Employee> searchEmployee;
	private AddressDataModel newAddressDataModel;
	private AddressDataModel addressDataModel;
	private Address selectedAddress = new Address();
	private String page = "details";
	private int selectedDepartment;
	private int selectedEmployeeType;
	private int selectedEmployeeGrade;
	private Users users = new Users();
	private boolean isRenderAddress = false;
	private String addressOperation = "Create";
	private String selectedAddressType = "Permanent";
	private boolean isEmployeeCreation = false;
	private List<Address> existingAddressList = new ArrayList<Address>();
	private String employeeName = "";
	
	private HashMap<Integer, Address> newAddressMap = new HashMap<Integer, Address>();
	
	
	public boolean isEmployeeCreation() {
		return isEmployeeCreation;
	}
	public void setEmployeeCreation(boolean isEmployeeCreation) {
		this.isEmployeeCreation = isEmployeeCreation;
	}
	
	public void initEmployeeCreation() {
		setEmployeeCreation(true);
		selectedEmployee = new Employee();
		setSelectedDepartment(-1);
		setSelectedEmployeeGrade(-1);
		setSelectedEmployeeType(-1);
		newAddressMap = new HashMap<Integer, Address>();
		setAddressOperation(false, "Create");
	}
	
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	
	public String getSelectedAddressType() {
		return selectedAddressType;
	}
	public void setSelectedAddressType(String selectedAddressType) {
		this.selectedAddressType = selectedAddressType;
	}
	
	public void setAddressOperation(boolean isRenderAddress, String addressOperation) {
		setRenderAddress(isRenderAddress);
		this.addressOperation = addressOperation;
		this.selectedAddress = new Address();
	}
	public String getAddressOperation() {
		return addressOperation;
	}
	
	public boolean isRenderAddress() {
		return isRenderAddress;
	}
	
	public void setRenderAddress(boolean isRenderAddress) {
		this.isRenderAddress = isRenderAddress;
	}
	
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	
	
	public List<Employee> getSearchEmployee() {
		
		return searchEmployee;
	}

	public void setSearchEmployee(List<Employee> searchEmployee) {
		this.searchEmployee = searchEmployee;
	}

	public EmployeeService getEmployeeService() {
		return employeeService;
	}
	
	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	public AddressService getAddressService() {
		return addressService;
	}
	public void setAddressService(AddressService addressService) {
		this.addressService = addressService;
	}
	
	public List<Employee> getEmployeeList() {
		if(employeeList == null || insertDelete == true) {
	
			employeeList = employeeService.findAll();			
		}		
		
		return employeeList;
	}
	
	public void setEmployeeList(List<Employee> employeeList) {
		this.employeeList = employeeList;
	}
	
	public EmployeeProfileDataModel getEmployeeDataModel() {
		if(employeeProfileDataModel == null || insertDelete == true) {
			employeeProfileDataModel = new EmployeeProfileDataModel(getEmployeeList());
		}
		return employeeProfileDataModel;
	}
	
	public void setEmployeeProfileDataModel(EmployeeProfileDataModel employeeProfileDataModel) {
		this.employeeProfileDataModel = employeeProfileDataModel;
	}
	
	public AddressDataModel getNewAddressDataModel() {
		if(newAddressDataModel == null || insertDeleteAddress == true) {
			newAddressDataModel = new AddressDataModel(new ArrayList<Address>(newAddressMap.values()));
			insertDeleteAddress = false;
		}
		
		return newAddressDataModel;
	}
	public void setAddressDataModel(AddressDataModel addressDataModel) {
		this.addressDataModel = addressDataModel;
	}
	
	public AddressDataModel getAddressDataModel() {
		if(addressDataModel == null || (insertDeleteAddress == true && isEmployeeCreation == false) ) {
			if(selectedEmployee != null && (existingAddressList == null || existingAddressList.size() == 0)) {
				existingAddressList = addressService.findByEmployeeId(this.selectedEmployee.getId());
			}
			if(existingAddressList == null || existingAddressList.size() == 0) {
				existingAddressList = new ArrayList<Address>();
			}
			List<Address> addressList = new ArrayList<Address>();
			Iterator<Address> existingAddressIterator = existingAddressList.iterator();
			
			while(existingAddressIterator.hasNext()) {
				
				Address currentAddress = existingAddressIterator.next();
				if(currentAddress.isDeleted() != true) {
					addressList.add(currentAddress);
				}
			}
			addressList.addAll(newAddressMap.values());
			addressDataModel = new AddressDataModel(addressList);
		}
		
		return addressDataModel;
	}
	public void setNewAddressDataModel(AddressDataModel newAddressDataModel) {
		this.newAddressDataModel = newAddressDataModel;
	}
	
	public Employee getNewEmployee() {
		return newEmployee;
	}
	
	public void setNewEmployee(Employee newEmployee) {
		this.newEmployee = newEmployee;
	}
	
	public void doCreateEmployee() {
		setInsertDelete(true);
		setPage("details");	
		newEmployee.setDeleted(false);
		newEmployee.setResigned(false);
		users.setEnabled(true);
		
		getEmployeeService().createEmployee(newEmployee, selectedEmployeeGrade, selectedEmployeeType, selectedDepartment, users, newAddressMap);
		
	}
	
	public Employee getSelectedEmployee() {
		return selectedEmployee;
	}
	public void setSelectedEmployee(Employee selectedEmployee) {
		this.selectedEmployee = selectedEmployee;
		this.selectedEmployeeType = this.selectedEmployee.getEmployeeType().getId();
		this.selectedEmployeeGrade = this.selectedEmployee.getEmployeeGrade().getId();
		this.selectedDepartment = this.selectedEmployee.getDepartment().getId();
		if(selectedEmployee.getUsers() == null) {
			users = new Users();
		} else {
			users = selectedEmployee.getUsers();
		}
		existingAddressList = null;
		setInsertDeleteAddress(true);
		setEmployeeName(this.selectedEmployee.getName());
		newAddressMap = new HashMap<Integer, Address>();
		
		
	}
	
	public Address getSelectedAddress() {

		return selectedAddress;
	}
	public void setSelectedAddress(Address selectedAddress) {
		this.selectedAddress = selectedAddress;
	}
	
	
	
	public void doUpdateEmployee() {
		getEmployeeService().updateEmployee(selectedEmployee, selectedEmployeeGrade, selectedEmployeeType, selectedDepartment, users, existingAddressList, newAddressMap);
		newAddressMap = new HashMap<Integer, Address>();
		existingAddressList = null;
	}
	
	public void onRowSelect(SelectEvent event) {  
		setSelectedEmployee((Employee) event.getObject());
		setEmployeeCreation(false);
        
    } 
	
	public void onAddressRowSelect(SelectEvent event) {  
		setAddressOperation(true, "Update");
		setSelectedAddress((Address) event.getObject());
		setSelectedAddressType(selectedAddress.getAddressType());
    } 
	
	public void doDeleteEmployee() {
		try {
			getEmployeeService().delete(selectedEmployee.getId());
		} catch(EmployeeNotFound e) {
			FacesMessage msg = new FacesMessage("Error", "Leave Type With id: " + selectedEmployee.getId() + " not found!");  
			  
	        FacesContext.getCurrentInstance().addMessage(null, msg);  
		}
		
		setInsertDelete(true);
	}
	
	public void setInsertDelete(boolean insertDelete) {
		this.insertDelete = insertDelete;
	}
	
	public boolean isInsertDelete() {
		return insertDelete;
	}
	public int getSelectedDepartment() {
		return selectedDepartment;
	}
	public void setSelectedDepartment(int selectedDepartment) {
		this.selectedDepartment = selectedDepartment;
	}
	public int getSelectedEmployeeType() {
		return selectedEmployeeType;
	}
	public void setSelectedEmployeeType(int selectedEmployeeType) {
		this.selectedEmployeeType = selectedEmployeeType;
	}
	public int getSelectedEmployeeGrade() {
		return selectedEmployeeGrade;
	}
	public void setSelectedEmployeeGrade(int selectedEmployeeGrade) {
		this.selectedEmployeeGrade = selectedEmployeeGrade;
	}
	
	public Users getUsers() {
		return users;
	}
	public void setUsers(Users users) {
		this.users = users;
	}
	
	public void addAddressToNewEmployee() {
		int index = newAddressMap.size();
		selectedAddress.setId(index);
		selectedAddress.setAddressType(selectedAddressType);
		selectedAddress.setDeleted(false);
		Address addressToBeAdded = selectedAddress;
		newAddressMap.put(index, addressToBeAdded);
		
		resetAddressOperation();
	}
	
	public void updateAddressToNewEmployee() {
		newAddressMap.put(selectedAddress.getId(), selectedAddress);
		
		resetAddressOperation();
	}
	
	public void deleteAddressToNewEmployee() {
		newAddressMap.remove(selectedAddress.getId());
		
		resetAddressOperation();
	}
	
	public void addAddressToEmployee() {
		int index = newAddressMap.size() + 9000;
		selectedAddress.setId(index);
		selectedAddress.setAddressType(selectedAddressType);
		selectedAddress.setDeleted(false);
		Address addressToBeAdded = selectedAddress;
		newAddressMap.put(index, addressToBeAdded);
		
		resetAddressOperation();
	}
	
	public void updateAddressToEmployee() {
		selectedAddress.setAddressType(selectedAddressType);
		
		resetAddressOperation();
	}
	
	public void deleteAddressToEmployee() {
		int addressId = this.selectedAddress.getId();
		if(newAddressMap.containsKey(addressId)) {
			newAddressMap.remove(addressId);
		} else {
			this.selectedAddress.setDeleted(true);
		}
		
		resetAddressOperation();
	}
	
	private void resetAddressOperation() {
		setAddressOperation(false, "Create");
		setInsertDeleteAddress(true);
		setSelectedAddressType("Permanent");
	}
	
	public void setInsertDeleteAddress(boolean insertDeleteAddress) {
		this.insertDeleteAddress = insertDeleteAddress;
	}
	
	
} 

