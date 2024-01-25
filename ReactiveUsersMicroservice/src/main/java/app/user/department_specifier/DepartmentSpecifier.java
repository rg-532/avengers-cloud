package app.user.department_specifier;

/**
 * The class {@code DepartmentSpecifier} is a boundary which simply serves to fit the structure of the JSON which needs to
 * be received for department assignment for a user.
 * 
 * @author Rom Gat
 */
public class DepartmentSpecifier {
	private DepartmentIdContainer container;

	
	public DepartmentSpecifier() {
		super();
	}

	
	public DepartmentIdContainer getContainer() {
		return container;
	}

	public void setContainer(DepartmentIdContainer container) {
		this.container = container;
	}


	@Override
	public String toString() {
		return "DepartmentSpecifier []";
	}
}
