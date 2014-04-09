package com.beans.leaveapp.employeegrade.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;


@Entity
@Table(name="EmployeeGrade")
public class EmployeeGrade {
	 private int id;
	 private String name;
	 private String description;
	 private boolean isDeleted = false;
	 private java.util.Date creationTime;
	 private String createdBy;
	 private java.util.Date lastModifiedTime;
	 private String lastModifiedBy;
	
	 
	   @Id
       @GeneratedValue
       @Column(name="id",nullable=false,unique=true)
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		
		@Column(name="name",nullable=false)
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		@Column(name="description",nullable=false)
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		
		@Column(name="isDeleted",columnDefinition="TINYINT(1)")
		@Type(type="org.hibernate.type.NumericBooleanType")
		public boolean isDeleted() {
				return isDeleted;
			}
		public void setDeleted(boolean isDeleted) {
				this.isDeleted = isDeleted;
			}
		
		

		public void setCreationTime(java.util.Date creationTime) {
			this.creationTime = creationTime;
		}
		public void setCreatedBy(String createdBy) {
			this.createdBy = createdBy;
		}
		public void setLastModifiedTime(java.util.Date lastModifiedTime) {
			this.lastModifiedTime = lastModifiedTime;
		}
		public void setLastModifiedBy(String lastModifiedBy) {
			this.lastModifiedBy = lastModifiedBy;
		}
				
		@Column(name="creationTime",nullable=true)
		@Temporal(TemporalType.TIMESTAMP)
		public java.util.Date getCreationTime() {
			return creationTime;
		}
		@Column(name="createdBy",nullable=true)
		public String getCreatedBy() {
			return createdBy;
		}
		@Column(name="lastModifiedTime",nullable=true)
		@Temporal(TemporalType.TIMESTAMP)
		public java.util.Date getLastModifiedTime() {
			return lastModifiedTime;
		}
		@Column(name="lastModifiedBy",nullable=true)
		public String getLastModifiedBy() {
			return lastModifiedBy;
		}
		public boolean equals(Object other)
		{
		    return other instanceof EmployeeGrade && id == ((EmployeeGrade) other).getId();
		}

		public int hashCode()
		{
		    return this.getClass().hashCode();
		}

		public String toString()
		{
		    return "EmployeeGrade[" + getId() + "," + getName() + "]";
		}
}
