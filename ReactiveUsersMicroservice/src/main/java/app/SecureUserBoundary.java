package app;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * The class {@code SecureUserBoundary} is another boundary class representing a user, <b>without the password</b>.
 * 
 * This boundary should only be <b>sent away</b> to the outside as it does not contain the user's password.
 *
 * @author Rom Gat
 */
public class SecureUserBoundary {
	@Email(message = "Invalid email format")
	private String email;
	
	@Valid
	private NameBoundary name;
	
	@NotBlank(message = "Empty birthdate")
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date birthdate;

	@NotBlank(message = "Empty recruitdate")
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date recruitdate;
	
	private List<String> roles;

	
	public SecureUserBoundary() {/*do nothing*/}
	
	public SecureUserBoundary(UserEntity entity) {
		/* Converter method: entity -> boundary.
		 */
		this.setEmail(entity.getEmail());
		
		NameBoundary name = new NameBoundary();
		name.setFirstName(entity.getFirstName());
		name.setLastName(entity.getLastName());
		this.setName(name);

		this.setBirthdate(entity.getBirthdate());
		this.setRecruitdate(entity.getRecruitdate());
		this.setRoles(entity.getRoles());
	}
	
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public NameBoundary getName() {
		return name;
	}

	public void setName(NameBoundary name) {
		this.name = name;
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
		return "SecureUserBoundary [email=" + email + ", name=" + name + ", birthdate=" + birthdate + ", recruitdate="
				+ recruitdate + ", roles=" + roles + "]";
	}
}
