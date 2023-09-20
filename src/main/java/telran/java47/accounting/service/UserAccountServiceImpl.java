package telran.java47.accounting.service;

import org.modelmapper.ModelMapper;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.java47.accounting.dao.UserAccountRepository;
import telran.java47.accounting.dto.UserDto;
import telran.java47.accounting.dto.UserRegisterDto;
import telran.java47.accounting.dto.exceptions.UserExistsException;
import telran.java47.accounting.model.UserAccount;


@Service
@RequiredArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {
	final UserAccountRepository userAccountRepository;
	final ModelMapper modelMapper;
	//final PasswordEncoder passwordEncoder;
	
	@Override
	public UserDto register(UserRegisterDto userRegisterDto) {
		if (userAccountRepository.existsById(userRegisterDto.getLogin().trim())) {
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void changePassword(String login, String newPassword) {
		// TODO Auto-generated method stub

	}

}
