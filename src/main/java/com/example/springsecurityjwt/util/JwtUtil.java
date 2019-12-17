package com.example.springsecurityjwt.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtil {

	private static final String SECRET_KEY = "secret";

	private <T> T extractClaim(String token, Function<Claims, T> claimFunction) {
		Claims claims = extractAllClaims(token);
		return claimFunction.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}

	private boolean isTokenExpired(String token) {
		Date date= extractExpiration(token);
		
		return date.before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	private String createToken(Map<String, Object> claims, String subject) {
		
		Date date= new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10);
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(date)
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	}

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public String generatToken(UserDetails userDetails) {
		Map<String, Object> map = new HashMap<String, Object>();
		return createToken(map, userDetails.getUsername());
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
}
