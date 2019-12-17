package com.example.springsecurityjwt.services;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService{

	
	/**
	 * Spring framework going to call this function in order to load a user by user name
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("MyUserDetailsService username "+username);
		return new User("foo","foo", new ArrayList<>()) ;
	}

}
