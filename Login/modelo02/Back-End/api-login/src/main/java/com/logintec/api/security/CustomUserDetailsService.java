package com.logintec.api.security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.logintec.api.domain.Usuario;
import com.logintec.api.repository.UsuarioRepository;

@Component
public class CustomUserDetailsService implements UserDetailsService {
	
	/* 	Essa classe implementa UserDetailsService, que é uma interface do Spring Security responsável por carregar os detalhes do usuário para autenticação.
	 *	 Objetivo:
	 *		Carregar os detalhes do usuário a partir do banco de dados com base no e-mail (que está sendo tratado como username).
	 */
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Usuario usuario = this.usuarioRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("Usuario não encontrado"));
		return  new org.springframework.security.core.userdetails.User(usuario.getEmail(), usuario.getPassword(), new ArrayList<>());
	}

}
