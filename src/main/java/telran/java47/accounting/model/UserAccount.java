package telran.java47.accounting.model;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Document(collection = "usersFintech")
@ToString
public class UserAccount {
	@Id
	String login;
	@Setter
	String email;
	@Setter
	String password;
	Set<String> roles;
	
	public UserAccount() {
		roles = new HashSet<>();
	}

	public UserAccount(String login, String password, String email, String firstName, String lastName) {
		this();
		this.login = login;
		this.password = password;
		this.email = email;
	}

	public boolean addRole(String role) {
		return roles.add(role);
	}

	public boolean removeRole(String role) {
		return roles.remove(role);
	}

}
