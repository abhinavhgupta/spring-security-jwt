package com.example.springsecurityjwt.model;

public class AuthenticateResponse {
	private String jwt;

	public AuthenticateResponse(String jwt) {
		this.jwt = jwt;
		System.out.println("jwt "+jwt);
	}

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}
}
