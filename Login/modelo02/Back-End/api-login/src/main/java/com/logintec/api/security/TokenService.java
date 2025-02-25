package com.logintec.api.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.logintec.api.domain.Usuario;

@Service
public class TokenService {
	
	/*		
	 *	Essa classe gerencia a geração e validação de tokens JWT
	 	Objetivo:
	*	Gerar um token JWT quando um usuário se autentica e validar esse token posteriormente.
	 */

	
	/*  O palavra secreta usada para assinar o token JWT, carregado a partir das configurações da aplicação. */
	@Value("${api.security.token.secret}")
		private String secret;
	
	
	/*  
	 *  Gera um token JWT contendo o e-mail do usuário no campo subject.
	 *  O token tem um tempo de expiração de 2 horas, conforme configurado no método generateExpirationDate().
	 *  Usa HMAC256 para assinar o token.
	 *  Se houver erro na criação do token, lança uma RuntimeException.
	 * 
	 */
	public String generateToken(Usuario usuario) {
		
		try {
			
			Algorithm algorithm = Algorithm.HMAC256(secret);
			
			String token = JWT.create()
					.withIssuer("login-auth-api")
					.withSubject(usuario.getEmail())
					.withExpiresAt(this.generateExpirationDate())
					.sign(algorithm);
					return token;
					
					
		}catch (JWTCreationException e) {
			 throw new RuntimeException("Error while authenticated");
		}
	}
	
	
	/*  
	 *  Verifica se o token recebido é válido.
	 *  Se válido, retorna o e-mail do usuário (presente no subject do token).
	 *  Se inválido, retorna null.
 	 * 
	 */
	public String validateToken(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			return JWT.require(algorithm)
					.withIssuer("login-auth-api")
					.build()
					.verify(token)
					.getSubject();
					
		}catch (Exception e) {
			return null;
		}
	}
	
	/*  
	 *  Gera um Instant com a data e hora de expiração do token.
	 * Adiciona 2 horas ao tempo atual.
	 * sa ZoneOffset.of("-03:00") para garantir que o fuso horário esteja correto (Brasil).
 	 * 
	 */
	public Instant generateExpirationDate() {
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
	}
}
