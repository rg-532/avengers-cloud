package app;

import jakarta.validation.constraints.NotBlank;

/**TODO - add this doc.
 * 
 */
public class NameBoundary {
	@NotBlank(message = "Empty first name")
	private String firstName;
	
	@NotBlank(message = "Empty last name")
	private String lastName;
	
	
	public NameBoundary() {/*do nothing*/}

	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	@Override
	public String toString() {
		return "NameBoundary [firstName=" + firstName + ", lastName=" + lastName + "]";
	}
}
