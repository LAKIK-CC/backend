package com.backend.lakik.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.backend.lakik.security.filter.AuthenticationFilter;
import com.backend.lakik.security.filter.AuthorizationFilter;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter { 
  @Autowired
  UserDetailsServiceImpl userDetailsService;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors().and().csrf().disable().authorizeRequests()
        .anyRequest().permitAll().and()
        .addFilter(new AuthenticationFilter(authenticationManager(), getApplicationContext()))
        .addFilter(new AuthorizationFilter(authenticationManager()))
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }

  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
  }
  
  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

    CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
    corsConfiguration.setAllowedOrigins(Arrays.asList("*"));
    corsConfiguration.setAllowedHeaders(Arrays.asList("*"));
    corsConfiguration.addAllowedMethod(HttpMethod.DELETE);
    corsConfiguration.addAllowedMethod(HttpMethod.PUT);
    corsConfiguration.addAllowedMethod(HttpMethod.OPTIONS);
    corsConfiguration.addAllowedMethod(HttpMethod.HEAD);
    source.registerCorsConfiguration("/**", corsConfiguration);

    return source;
  }

}