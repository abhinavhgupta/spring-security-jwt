package com.example.springsecurityjwt.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.springsecurityjwt.model.AuthenticateRequest;
import com.example.springsecurityjwt.model.AuthenticateResponse;
import com.example.springsecurityjwt.services.MyUserDetailsService;
import com.example.springsecurityjwt.util.JwtUtil;

@RestController
public class HomeResource {

	@Autowired(required = true)
	private AuthenticationManager authenticationManager;

	@Autowired
	private MyUserDetailsService myUserDetailsService;

	@Autowired
	private JwtUtil jwtUtil;

	@GetMapping("/")
	public String home() {
		return ("<h1>Welcome</h1>");
	}

	@GetMapping("/user")
	public String user() {
		return ("<h1>Welcome User</h1>");
	}

	@GetMapping("/admin")
	public String admin() {
		return ("<h1>Welcome Admin</h1>");
	}

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> creatAuthenticationToken(@RequestBody AuthenticateRequest authenticateRequest) {
		// UsernamePasswordAuthenticationToken is a standard token that spring MVC uses
		// for username and password so if this authentication fails there is an
		// exception so we can put exception handling around this code
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticateRequest.getUsername(),
				authenticateRequest.getPassword()));

		UserDetails userDetails = myUserDetailsService.loadUserByUsername(authenticateRequest.getUsername());
		String jwt = jwtUtil.generatToken(userDetails);

		return ResponseEntity.ok(new AuthenticateResponse(jwt));
	}

}
