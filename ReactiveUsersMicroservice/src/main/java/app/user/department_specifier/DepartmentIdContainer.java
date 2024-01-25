package app.user.department_specifier;

/**
 * The class {@code DepartmentIdContainer} is a sub-boundary class of {@code DepartmentSpecifier} which holds a depratment's id.
 * 
 * @author Rom Gat
 */
public class DepartmentIdContainer {
	private String deptId;

	
	public DepartmentIdContainer() {
		super();
	}

	
	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}


	@Override
	public String toString() {
		return "DepartmentIdContainer [deptId=" + deptId + "]";
	}
}
