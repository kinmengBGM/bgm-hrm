package com.beans.leaveapp.common.audit.service;

import java.sql.Date;
import java.util.List;

import com.beans.leaveapp.common.audit.model.SystemAuditTrail;

public interface SystemAuditTrailRecordService {

	public SystemAuditTrail create(SystemAuditTrail systemAuditTrail);
	public SystemAuditTrail delete(int id) throws Exception;
	
	public List<SystemAuditTrail> findAll();
	public SystemAuditTrail update(SystemAuditTrail systemAuditTrail) throws Exception;
	public SystemAuditTrail findById(int id) throws Exception;
	public List<SystemAuditTrail> findSelectedDates(Date x,Date y);
	
	
	
	
}
