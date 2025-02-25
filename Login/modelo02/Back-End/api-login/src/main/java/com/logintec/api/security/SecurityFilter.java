package com.logintec.api.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.logintec.api.domain.Usuario;
import com.logintec.api.repository.UsuarioRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
 

@Component
public class SecurityFilter extends OncePerRequestFilter {

	/*
	 * Objetivo: 
	 * 	Interceptar todas as requisições para verificar se há um token JWT válido.
	 * 
	 * Como funciona:
	 * 	Valida o token utilizando TokenService
	 * 	Autentica o usuário caso o token seja válido.
	 */
	
	@Autowired
	 TokenService tokenService;
 
	@Autowired
	UsuarioRepository usuarioRepository;

	
	/*
	 *	   Como funciona:
		 *		Esse método é chamado a cada requisição que passa pelo filtro.
				Recupera o token do cabeçalho HTTP com recoverToken(request).
				Valida o token chamando tokenService.validateToken(token).
				
				Se o token for válido:
				Busca o usuário pelo e-mail (UserRepository.findByEmail(login)).
				Cria uma autenticação para o usuário (UsernamePasswordAuthenticationToken).
				Define a autenticação no Spring Security Context (SecurityContextHolder.getContext().setAuthentication(authentication)).
				Passa a requisição adiante com filterChain.doFilter(request, response).
	 
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
	 
		var token = this.recoverToken(request);
        var login = tokenService.validateToken(token);
        
        if(login != null) {
        	
        	Usuario usuario = usuarioRepository.findByEmail(login).orElseThrow(()-> new RuntimeException("User not Found"));
        	var authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        	var authentication = new UsernamePasswordAuthenticationToken(usuario, null, authorities);
        	SecurityContextHolder.getContext().setAuthentication(authentication);
        	
        }
        
        filterChain.doFilter(request, response);
	}
 

	/*
	 * Como funciona:
		 * recoverToken(HttpServletRequest request)
			Extrai o token do cabeçalho Authorization.
			Se o cabeçalho for null, retorna null.
			Remove a palavra "Bearer " do token para obter apenas o JWT 
	 * */
	private String recoverToken(HttpServletRequest request) {
		var autHeader = request.getHeader("Authorization");
		if(autHeader == null) {
			return null;
		}
		return autHeader.replace("Bearer ", "");
		
	}
	
	
	
	
	
	
}
