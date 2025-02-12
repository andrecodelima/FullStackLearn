package net.sys.gest.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // Informa que é uma classe que cuida da configuracao da Web
public class SecurityConfig {

	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@Autowired
	SecurityFilter securityFilter;
	
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		
		http
						.csrf(csrf -> csrf.disable())
						
						// Todas as Apis RestFull preicisam ser Stateless, ou seja, elas nao guardam o estado de login dentro delas.
						.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
						
						.authorizeHttpRequests(authorize -> authorize
								
								// Esses endpoints a baixo nao precisam de autenticacao.
								.requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
								.requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
								
								// Outras requisicoes precisam estar autenticadas.
								.anyRequest().authenticated()
								
								)
						
							// Antes de fazer as autorizacoes rode o filtro que ira fazer as validacoes solicitadas pela classe SecurityFilter
							.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
		return authenticationConfiguration.getAuthenticationManager();
	}
	
}
