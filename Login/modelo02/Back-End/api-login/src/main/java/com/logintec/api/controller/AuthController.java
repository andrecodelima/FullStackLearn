package com.logintec.api.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.logintec.api.domain.Usuario;
import com.logintec.api.dto.LoginRequestDTO;
import com.logintec.api.dto.RegisterRequestDTO;
import com.logintec.api.dto.ResponseDTO;
import com.logintec.api.repository.UsuarioRepository;
import com.logintec.api.security.TokenService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private TokenService tokenService;

	@PostMapping("/login")
	public ResponseEntity login(@RequestBody LoginRequestDTO body) {
		
		Usuario usuario = this.usuarioRepository.findByEmail(body.email()).orElseThrow(() -> new RuntimeException("User not Found"));
		if(passwordEncoder.matches(body.password(), usuario.getPassword())) {
			String token = this.tokenService.generateToken(usuario);
			return ResponseEntity.ok(new ResponseDTO(usuario.getName(), token));
		}
		
		return ResponseEntity.badRequest().build();
	}

	@PostMapping("/register")
	public ResponseEntity register(@RequestBody RegisterRequestDTO body) {
		
		Optional<Usuario>usuario = this.usuarioRepository.findByEmail(body.email());
		
		if(usuario.isEmpty()) {
			
			Usuario novoUsuario = new Usuario();
			novoUsuario.setPassword(passwordEncoder.encode(body.password()));
			novoUsuario.setEmail(body.email());
			novoUsuario.setName(body.nome());
			this.usuarioRepository.save(novoUsuario);
			
			String token = this.tokenService.generateToken(novoUsuario);
			return ResponseEntity.ok(new ResponseDTO(novoUsuario.getName(), token));
		}
		
		return ResponseEntity.badRequest().build();
		
	}

}
