package app.user;

/**
 * The class {@code NameBoundary} is a sub-boundary class of {@code UserBoundary} and {@code SecureUserBoundary} representing
 * a user's full name.
 * 
 * @author Rom Gat
 */
public class NameBoundary {
	private String firstName;
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
