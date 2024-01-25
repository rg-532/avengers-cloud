package app;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * The class {@code UserEntity} is an entity class representing a user in the database.
 *
 * @author Rom Gat
 */
@Document(collection = "USERS")
public class UserEntity {
	@Id private String email;
	private String firstName;
	private String lastName;
	private String password;
	private Date birthdate;
	private Date recruitdate;
	private List<String> roles;
	
	
	public UserEntity() {
	}

	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public Date getRecruitdate() {
		return recruitdate;
	}

	public void setRecruitdate(Date recruitdate) {
		this.recruitdate = recruitdate;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}


	@Override
	public String toString() {
		return "UserEntity [email=" + email + ", firstName=" + firstName + ", lastName=" + lastName + ", password="
				+ password + ", birthdate=" + birthdate + ", recruitdate=" + recruitdate + ", roles=" + roles + "]";
	}
}
