package app;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * The class {@code UserBoundary} is a boundary class representing a user.
 * 
 * This boundary should only be used in <b>HTTP POST methods</b> as these are the only ones which receive and return the
 * user's password in the request body. For all other purposes, see {@code SecureUserBoundary}.
 *
 * @author Rom Gat
 */
public class UserBoundary {
	@Email(message = "Invalid email format")
	private String email;
	
	@Valid
	private NameBoundary name;
	
	@NotBlank(message = "Empty password")
	@Size(min = 3, message = "Password must be at least 3 characters")
	private String password;
	
	@NotBlank(message = "Empty birthdate")
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date birthdate;

	@NotBlank(message = "Empty recruitdate")
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date recruitdate;
	
	private List<String> roles;

	
	public UserBoundary() {/*do nothing*/}
	
	public UserEntity toEntity() {
		/* Converter method: boundary -> entity.
		 */
		UserEntity entity = new UserEntity();
		
		entity.setEmail(this.getEmail());
		entity.setFirstName(this.getName().getFirstName());
		entity.setLastName(this.getName().getLastName());
		entity.setPassword(this.getPassword());
		entity.setBirthdate(this.getBirthdate());
		entity.setRecruitdate(this.getRecruitdate());
		entity.setRoles(this.getRoles());
		
		return entity;
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
		return "UserBoundary [email=" + email + ", name=" + name + ", password=" + password + ", birthdate=" + birthdate
				+ ", recruitdate=" + recruitdate + ", roles=" + roles + "]";
	}
}
