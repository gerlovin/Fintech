package telran.java47.accounting.service;

import java.util.List;

import org.modelmapper.ModelMapper;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.java47.accounting.dao.UserAccountRepository;
import telran.java47.accounting.dto.UserDto;
import telran.java47.accounting.dto.UserInfoDto;
import telran.java47.accounting.dto.UserRegisterDto;
import telran.java47.accounting.dto.exceptions.UserExistsException;
import telran.java47.accounting.dto.exceptions.UserNotFoundException;
import telran.java47.accounting.model.UserAccount;


@Service
@RequiredArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {
	final UserAccountRepository userAccountRepository;
	final ModelMapper modelMapper;
	final EmailService emailService;
	//final PasswordEncoder passwordEncoder;
	
	@Override
	public UserDto register(UserRegisterDto userRegisterDto) {

		if (userAccountRepository.existsById(userRegisterDto.getEmail().trim())) {
			throw new UserExistsException();
		}
		
		UserAccount userAccount = modelMapper.map(userRegisterDto, UserAccount.class);
		//String password = passwordEncoder.encode(userRegisterDto.getPassword());
		//userAccount.setPassword(password);
		userAccount.addRole("USER");
		userAccountRepository.save(userAccount);
		userAccountRepository.findAll().forEach(e -> System.out.println(e));
		return modelMapper.map(userAccount, UserDto.class);
	}

	@Override
	public UserDto getUser(String login) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDto removeUser(String login) {
		UserAccount userAccount = userAccountRepository.findById(login).orElseThrow(UserNotFoundException::new);
		userAccountRepository.delete(userAccount);
		return modelMapper.map(userAccount, UserDto.class);
	}

	@Override
	public void changePassword(String login, String newPassword) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<UserInfoDto> getAllUsers() {
		
		return userAccountRepository.findAll().stream()
				.map(u -> modelMapper.map(u, UserInfoDto.class))
				.toList();
	}

	@Override
	public void recoveryPassword(String email) {	
		// TODO generating random password with SecureRandom from Spring Security
		String passwordNew = "777";
		UserAccount userAccount = userAccountRepository.findById(email.trim()).orElse(null);
		
		if (userAccount == null) {
			throw new UserNotFoundException();
		}
		userAccount.setPassword(passwordNew);
		userAccountRepository.save(userAccount);
		System.out.println("User " + email + " exists") ;
		emailService.sendEmail(email, "New password for Fintech app", "Your new password is " + passwordNew);
		
	}

}
