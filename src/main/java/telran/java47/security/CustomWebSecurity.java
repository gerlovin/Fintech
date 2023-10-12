package telran.java47.security;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.java47.accounting.dao.UserAccountRepository;
import telran.java47.accounting.model.UserAccount;


@Service("customSecurity")
@RequiredArgsConstructor
public class CustomWebSecurity {
	final UserAccountRepository userAccountRepository;

	public boolean checkPostAuthor(String postId, String userName) {
//		UserAccount post;
//		try {
//			post = UserAccountRepository.findById(postId).orElse(null);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return post != null && userName.equalsIgnoreCase(post.getAuthor());
		return false;
	}
}
