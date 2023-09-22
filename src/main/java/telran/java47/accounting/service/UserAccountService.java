package telran.java47.accounting.service;


import java.util.List;

import telran.java47.accounting.dto.UserDto;
import telran.java47.accounting.dto.UserRegisterDto;
import telran.java47.accounting.dto.UserInfoDto;


public interface UserAccountService {

	UserDto register(UserRegisterDto userRegisterDto);

	UserDto getUser(String login);

	UserDto removeUser(String login);

	//UserDto updateUser(String login, UserEditDto userEditDto);

	//RolesDto changeRolesList(String login, String role, boolean isAddRole);

	void changePassword(String login, String newPassword);
	
	List<UserInfoDto> getAllUsers();
	
	void recoveryPassword(String email);

}
