package app.department;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * The class {@code DepartmentEntity} is an entity class representing a department in the database.
 *
 * @author Rom Gat
 */
@Document(collection = "DEPARTMENTS")
public class DepartmentEntity {
	@Id private String deptId;
	private String departmentName;
	private Date creationDate;
	
	public DepartmentEntity() {/*do nothing*/}

	
	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}


	@Override
	public String toString() {
		return "DepartmentEntity [deptId=" + deptId + ", departmentName=" + departmentName + ", creationDate="
				+ creationDate + "]";
	}
}
