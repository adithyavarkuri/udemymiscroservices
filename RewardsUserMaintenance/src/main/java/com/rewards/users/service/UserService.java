package com.rewards.users.service;


import org.springframework.security.core.userdetails.UserDetailsService;

import com.rewards.users.entities.User;

public interface UserService extends UserDetailsService {

	User findUserByEmail(String email);

}
