package com.beans.leaveapp.yearlyentitlement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.beans.leaveapp.yearlyentitlement.model.YearlyEntitlement;

public interface YearlyEntitlementRepository extends
		CrudRepository<YearlyEntitlement, Integer> {

	@Query("select y from YearlyEntitlement y where isDeleted =?")
	public List<YearlyEntitlement> findByIsDeleted(int isDeleted);

	@Query("select y from YearlyEntitlement y where  employeeId =? and isDeleted = 0")
	public List<YearlyEntitlement> findByEmployeeId(int employeeId);

	@Query("select y from  YearlyEntitlement y where leaveTypeId =? and isDeleted = 0")
	public List<YearlyEntitlement> findByLeaveTypeIdLike(int leaveTypeId);

	
	@Query("select y from YearlyEntitlement y join y.employee e join y.leaveType l where e.id =? and y.id =?")
	public List<YearlyEntitlement> findByEmployeeIdAndLeaveTypeId( int employeeId,int leaveTypeId);
	  
	
	@Query("select y from YearlyEntitlement y join y.employee e where e.name like :name")
	public List<YearlyEntitlement> findByEmployeeLike(@Param("name") String name);

	@Query("select y from YearlyEntitlement y join y.leaveType l where l.name like :name")
	public List<YearlyEntitlement> findByLeaveTypeLike(
			@Param("name") String name);

	@Query("select ye from YearlyEntitlement ye join ye.employee ee join ye.leaveType lt where ee.name like :employeeName and lt.name like :leaveTypeName")
	public List<YearlyEntitlement> findByEmployeeAndLeaveTypeLike(
			@Param("employeeName") String employeeName,
			@Param("leaveTypeName") String leaveTypeName);

}
