package net.sys.gest.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.sys.gest.domain.User;
import net.sys.gest.dto.LoginRequestDTO;
import net.sys.gest.dto.RegisterRequestDTO;
import net.sys.gest.dto.ResponseDTO;
import net.sys.gest.repository.UserRepository;
import net.sys.gest.security.TokenService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private  UserRepository userRepository;
	@Autowired
	private  PasswordEncoder passwordEncoder;
	@Autowired
	private  TokenService tokenService;
	
	@PostMapping("/login")
	public ResponseEntity login(@RequestBody LoginRequestDTO body) {
		
		User user = this.userRepository.findByEmail(body.email()).orElseThrow(() -> new RuntimeException("User not found"));
		if(passwordEncoder.matches(body.password(), user.getPassword())) {
			String token = this.tokenService.generateToken(user);
			return ResponseEntity.ok(new ResponseDTO(user.getName(), token));
		}
		return ResponseEntity.badRequest().build();
	}
	
	@PostMapping("/register")
	public ResponseEntity register(@RequestBody RegisterRequestDTO body) {
		
		Optional<User>user = this.userRepository.findByEmail(body.email());
		
		if(user.isEmpty()) {
			
			User newUser = new User();
			newUser.setPassword(passwordEncoder.encode(body.password()));
			newUser.setEmail(body.email());
			newUser.setName(body.name());
			this.userRepository.save(newUser);
			
 				String token = this.tokenService.generateToken(newUser);
				return ResponseEntity.ok(new ResponseDTO(newUser.getName(), token));
			
		}
		
		return ResponseEntity.badRequest().build();
	}
}
