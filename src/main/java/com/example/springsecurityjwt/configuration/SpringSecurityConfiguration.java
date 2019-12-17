package com.example.springsecurityjwt.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.springsecurityjwt.services.MyUserDetailsService;
import com.example.springsecurityjwt.util.JwtRequestFilter;

@EnableWebSecurity
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private MyUserDetailsService myUserDetailsService;
	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	/**
	 * This method is used to Authentication
	 * 
	 * Spring security calling this method, whatever AuthenticationManagerBuilder is
	 * gona pass to myUserDetailsService use it for authentication, now spring
	 * security going to call userDetailService
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(myUserDetailsService);
	}

	/**
	 * By default spring put security on every URL so we write below function to
	 * disable security for that particular url that is /authenticate.
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf()
		.disable()
		.authorizeRequests()
		.antMatchers("/authenticate")
		.permitAll()
		.anyRequest()
		.authenticated()
		.and().sessionManagement()// Lines added to tell spring don't create/manage session
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(jwtRequestFilter,UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	/**
	 * It allows to use clear text in password, we are disabling the encoding on
	 * password.
	 * 
	 * @return
	 */
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

}
