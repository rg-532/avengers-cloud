package app.user;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * The class {@code SecureUserBoundary} is another boundary class representing a user, <b>without the password</b>.
 * 
 * This boundary should only be returned on <b>HTTP GET methods</b>, since we do not return passwords on those methods.
 *
 * @author Rom Gat
 */
public class SecureUserBoundary {
	private String email;
	private NameBoundary name;
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date birthdate;

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
