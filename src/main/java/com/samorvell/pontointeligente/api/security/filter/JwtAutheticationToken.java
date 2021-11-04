package com.samorvell.pontointeligente.api.security.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.samorvell.pontointeligente.api.model.Funcionario;
import com.samorvell.pontointeligente.api.security.dto.JwtAuthenticationDto;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtAutheticationToken {

	private static final String TOKEN_HEADER = "Authorization";
	private static final String BEARER_PREFIX = "Bearer ";

	@Value("${jwt.secret}")
	static String secret;

	@Value("${jwt.expiration}")
	private static Long expiration;

	private final AuthenticationManager authenticationManager;

	public JwtAutheticationToken(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	// @Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			Funcionario funcionario = new ObjectMapper().readValue(request.getInputStream(), Funcionario.class);

			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(funcionario.getEmail(),
					funcionario.getSenha(), new ArrayList<>()));

		} catch (IOException e) {
			throw new RuntimeException("Falha ao autenticar usuario", e);
		}

	}

	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		JwtAuthenticationDto usuarioDto = (JwtAuthenticationDto) authResult.getPrincipal();

		String token = JWT.create().withSubject(usuarioDto.getEmail())
				.withExpiresAt(new Date(System.currentTimeMillis() + expiration))
				.sign(Algorithm.HMAC512(secret));

		response.getWriter().write(token);
		response.getWriter().flush();
	}

	static void addAuthentication(HttpServletResponse response, String username) {
		String JWT = Jwts.builder().setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
		response.addHeader(TOKEN_HEADER, BEARER_PREFIX + JWT);

	}

	static Authentication getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(TOKEN_HEADER);

		if (token != null) {
			// faz parse do token
			String user = Jwts.parser().setSigningKey(secret).parseClaimsJws(token.replace(TOKEN_HEADER, "")).getBody()
					.getSubject();
			if (user != null) {
				return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
			}
		}
		return null;

	}

}
