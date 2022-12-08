package com.backend.lakik.security.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.backend.lakik.util.JWTUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class AuthorizationFilter extends BasicAuthenticationFilter {

  	public AuthorizationFilter(AuthenticationManager authManager) {
		super(authManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
		throws IOException, ServletException {
		String header = request.getHeader("Authorization");

		if (header != null && header.startsWith("Bearer ")) {
			try {
				UsernamePasswordAuthenticationToken authentication = authenticate(request);

				SecurityContextHolder.getContext().setAuthentication(authentication);
			} catch (JWTVerificationException e) {
				log.error(e.getMessage());
				HashMap<String, Object> data = new HashMap<>();
				data.put("error", "there is something wrong with the JWT");

				String error = new ObjectMapper().writeValueAsString(data);
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				response.setContentType(APPLICATION_JSON_VALUE);
				response.getWriter().write(error);
				response.getWriter().flush();
				return;
			}
		}
		chain.doFilter(request, response);
	}

	private UsernamePasswordAuthenticationToken authenticate(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String user = JWTUtils.decodeJWTToken(token).getSubject();

		String role = JWTUtils.decodeJWTToken(token).getClaim("role").asString();

		if (user == null) {
			return null;
		}

		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		grantedAuthorities.add(new SimpleGrantedAuthority(role));
		return new UsernamePasswordAuthenticationToken(user, null, grantedAuthorities);
	}
}
