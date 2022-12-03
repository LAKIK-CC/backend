package com.backend.lakik.security.filter;

import org.springframework.context.ApplicationContext;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.backend.lakik.model.UserModel;
import com.backend.lakik.repository.UserDb;
import com.backend.lakik.util.JWTUtils;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    UserDb userDb;
    private AuthenticationManager authenticationManager;

    public AuthenticationFilter(AuthenticationManager authenticationManager, ApplicationContext ctx) {
      this.authenticationManager = authenticationManager;
      this.userDb = (UserDb) ctx.getBean(UserDb.class);
      setFilterProcessesUrl("/v1/user/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
		try {
			UserModel temp = new ObjectMapper().readValue(req.getInputStream(), UserModel.class);
			Optional<UserModel> optUser = userDb.findByUsername(temp.getUsername());
            if (optUser.isEmpty()){
                HashMap<String, Object> data = new HashMap<>();
        		data.put("error", "username and password doesn't match");

        		String error = new ObjectMapper().writeValueAsString(data);
                res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                res.setContentType(APPLICATION_JSON_VALUE);
                res.getWriter().write(error);
                res.getWriter().flush();
                return null;
            } else {
                UserModel applicationUser = optUser.get();
                applicationUser.setPassword(temp.getPassword());
                Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
                grantedAuthorities.add(new SimpleGrantedAuthority(applicationUser.getRole().getNamaRole()));
                return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(applicationUser.getUsername(),
                    applicationUser.getPassword(), grantedAuthorities));
            }
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
		Authentication auth) throws IOException {
        User userDetail = ((User) auth.getPrincipal());

        UserModel user = userDb.findByUsername(userDetail.getUsername()).get();

        String accessToken = JWTUtils.generateAccessToken(user);
        String refreshToken = JWTUtils.generateRefreshToken(user);

        HashMap<String, Object> data = new HashMap<>();
        data.put("accessToken", accessToken);
        data.put("refreshToken", refreshToken);
        data.put("role", userDetail.getAuthorities());
        String body = new ObjectMapper().writeValueAsString(data);

        res.setContentType(APPLICATION_JSON_VALUE);
        res.getWriter().write(body);
        res.getWriter().flush();
    }
}


