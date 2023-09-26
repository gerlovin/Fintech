package telran.java47.accounting.service;


import java.util.List;

import telran.java47.accounting.dto.UserDto;
import telran.java47.accounting.dto.UserRegisterDto;
import telran.java47.accounting.dto.UserInfoDto;


public interface UserAccountService {

	UserDto register(UserRegisterDto userRegisterDto);

	UserDto getUser(String login);

	UserDto removeUser(String email);

	UserDto changeRolesList(String email, String role, boolean isAddRole);

	void changePassword(String email, String newPassword);
	
	List<UserInfoDto> getAllUsers();
	
	void recoveryPassword(String email);

}
