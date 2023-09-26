package telran.java47.accounting.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import telran.java47.accounting.dto.UserDto;
import telran.java47.accounting.dto.UserInfoDto;
import telran.java47.accounting.dto.UserRegisterDto;
import telran.java47.accounting.service.UserAccountService;


@RestController
@RequiredArgsConstructor
public class AccountingController {
	final UserAccountService userAccountService;
	
	@PostMapping("/account/register")
	@CrossOrigin(origins = "*") 
	public UserDto register(@RequestBody UserRegisterDto userRegisterDto) {
		return userAccountService.register(userRegisterDto);
		
	}
	
	@DeleteMapping("/account/user/{email}")
	@CrossOrigin(origins = "*") 
	public UserDto removeUser(@PathVariable String email) {
		return userAccountService.removeUser(email);
	}
	
	
	@GetMapping("/account/users")
	@CrossOrigin(origins = "*") 
	public List<UserInfoDto> getAllUsers() {
		return userAccountService.getAllUsers();
	}
	
	@GetMapping("/account/recovery/{email}")
	@CrossOrigin(origins = "*") 
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void getRecovery(@PathVariable String email) {
		System.out.println("we are here");
		userAccountService.recoveryPassword(email);
	}


}
