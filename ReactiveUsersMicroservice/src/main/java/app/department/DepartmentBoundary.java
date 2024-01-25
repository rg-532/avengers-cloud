package app.department;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * The class {@code DepartmentBoundary} is a boundary class representing a department.
 *
 * @author Rom Gat
 */
public class DepartmentBoundary {
	private String departmentName;
	private String deptId;

	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date creationDate;
	
	
	public DepartmentBoundary() {/*do nothing*/}
	
	public DepartmentBoundary(DepartmentEntity entity) {
		/* Converter method: entity -> boundary.
		 */
		this.setDeptId(entity.getDeptId());
		this.setDepartmentName(entity.getDepartmentName());
		this.setCreationDate(entity.getCreationDate());
	}
	
	public DepartmentEntity toEntity() {
		/* Converter method: boundary -> entity.
		 */
		DepartmentEntity entity = new DepartmentEntity();
		
		entity.setDeptId(this.getDeptId());
		entity.setDepartmentName(this.getDepartmentName());
		entity.setCreationDate(this.getCreationDate());
		
		return entity;
	}
	

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	
	@Override
	public String toString() {
		return "DepartmentBoundary [departmentName=" + departmentName + ", deptId=" + deptId + ", creationDate="
				+ creationDate + "]";
	}
}
