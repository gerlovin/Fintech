package telran.java47.accounting.service;

import java.util.ArrayList;
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
enum Roles {
    ADMIN, MODERATOR, USER
}

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
		userAccount.addRole(Roles.USER.toString());
		userAccountRepository.save(userAccount);
		userAccountRepository.findAll().forEach(e -> System.out.println(e));
		return modelMapper.map(userAccount, UserDto.class);
	}

	@Override
	public UserDto getUser(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDto removeUser(String email) {
		UserAccount userAccount = userAccountRepository.findById(email).orElseThrow(UserNotFoundException::new);
		userAccountRepository.delete(userAccount);
		return modelMapper.map(userAccount, UserDto.class);
	}

	@Override
	public void changePassword(String email, String newPassword) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<UserInfoDto> getAllUsers() {
		List<UserInfoDto> result = new ArrayList<>();
		userAccountRepository.findAll().stream()
				.map(u -> modelMapper.map(u, UserInfoDto.class))
				.forEach(u -> result.add(u));
				return result;
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

	@Override
	public UserDto changeRolesList(String email, String role, boolean isAddRole) {
		UserAccount userAccount = userAccountRepository.findById(email.trim()).orElse(null);
		if (userAccount == null) {
			throw new UserNotFoundException();
		}
		if (isAddRole) {
			userAccount.addRole(role);
		}else {
			userAccount.removeRole(role);
		}
		userAccountRepository.save(userAccount);
		return modelMapper.map(userAccount, UserDto.class);
	}

}
