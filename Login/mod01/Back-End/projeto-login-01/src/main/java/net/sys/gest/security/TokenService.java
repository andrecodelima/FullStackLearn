package net.sys.gest.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import net.sys.gest.domain.User;

@Service
public class TokenService {
	
	@Value("${api.security.token.secret}")// Recupera chave do properties
	private String secret;

	// Geração ou Criação de Token
	public String generateToken(User user) {
		
		try {
			
			Algorithm algorithm = Algorithm.HMAC256(secret);
			
			String token = JWT.create()
					.withIssuer("login-auth-api") // Quem esta emitindo o token
					.withSubject(user.getEmail()) // Quem esta recebendo o token
					.withExpiresAt(this.generateExpirationDate()) // Tempo de expiracao
					.sign(algorithm); // Assinatura do token
			return token;
					
			
		}catch (JWTCreationException e) {
			 throw new RuntimeException("Error while authenticating");
		}
		
	}
	
	//Validacao do token
	public String validateToken(String token) {
		try {
			
			Algorithm algorithm = Algorithm.HMAC256(secret);
			return JWT.require(algorithm)
					.withIssuer("login-auth-api")
					.build()
					.verify(token)
					.getSubject();
			
		}catch (JWTVerificationException e) {
			return null;
		}
	}
	
	// Expiracao do Token
	private Instant generateExpirationDate() {
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
	}
}
