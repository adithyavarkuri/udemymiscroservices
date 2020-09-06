package com.rewards.users.serviceimpl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rewards.users.entities.User;
import com.rewards.users.repositories.UserRepository;
import com.rewards.users.service.UserService;

/*
– UserDetailsService interface has a method to load User by username and returns a UserDetails object that Spring Security can use for authentication and validation.

– UserDetails contains necessary information (such as: username, password, authorities) to build an Authentication object.*/
@Service("userService")
	public class UserServiceImpl implements UserService {
	 
	 @Autowired
	 private UserRepository userRepository;
	 
	 

	 public User findUserByEmail(String email) {
	  return userRepository.findByEmail(email);
	 }

	 @Override
		@Transactional
		public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
			User user = userRepository.findByUsername(username)
					.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

			return UserDetailsImpl.build(user);
		}



	 


	


}
