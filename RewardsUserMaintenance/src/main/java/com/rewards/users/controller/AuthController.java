package com.rewards.users.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rewards.users.entities.User;
import com.rewards.users.repositories.UserRepository;
import com.rewards.users.request.LoginRequest;
import com.rewards.users.request.SignupRequest;
import com.rewards.users.response.MessageResponse;
import com.rewards.users.utils.ConfigProperties;

//use model mapper

/*Controller receives and handles request after it was filtered by OncePerRequestFilter.*/

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	UserRepository userRepository;


	@Autowired
	PasswordEncoder encoder;

	@Autowired
	ConfigProperties configProp;

	@PostMapping(path = "/login" ,consumes  = {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
	//@Valid is used to validate the values inthe body
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		/*
		 *  UsernamePasswordAuthenticationToken gets {username, password} from login Request, AuthenticationManager will use
		 * it to authenticate a login account.
		 */
		/*
		 * AuthenticationManager has a DaoAuthenticationProvider (with help of
		 * UserDetailsService & PasswordEncoder) to validate
		 * UsernamePasswordAuthenticationToken object. If successful,
		 * AuthenticationManager returns a fully populated Authentication object
		 * (including granted authorities).
		 */
		/*
		 * If the authentication process is successful, we can get User’s information
		 * such as username, password, authorities from an Authentication object.
		 */
		/*
		 * If we want to get more data (id, email…), we can create an implementation of
		 * this UserDetails interface. like UserDetailsImpl
		 */
		

		return  ResponseEntity.ok("loged");
	}

	@PostMapping(path = "/signup" ,consumes  = {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
	
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUserName())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse( configProp.getConfigValue("auth.user.name.taken") ));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse( configProp.getConfigValue("auth.user.email.taken") ));
		}

		// Create new user's account
		User user = new User(signUpRequest.getUserName(), 
							 signUpRequest.getEmail(),
							 encoder.encode(signUpRequest.getPassword()));

		
		userRepository.save(user);

		return ResponseEntity.status(HttpStatus.OK).body(user);
	}

}


/* https://bezkoder.com/spring-boot-jwt-authentication/ */