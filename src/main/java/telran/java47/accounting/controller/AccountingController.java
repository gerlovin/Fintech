package telran.java47.accounting.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import telran.java47.accounting.dto.UserDto;
import telran.java47.accounting.dto.UserRegisterDto;
import telran.java47.accounting.service.UserAccountService;


@RestController
@RequiredArgsConstructor
public class AccountingController {
	final UserAccountService userAccountService;
	
	@PostMapping("/account/register")
	public UserDto register(@RequestBody UserRegisterDto userRegisterDto) {
		return userAccountService.register(userRegisterDto);
		
	}

}
