package com.SpringBootJwtAuthentication.WebToken;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class jwtUtil {
	
 private  static final String key="[D8CEAAAE2529A028D2C82BC8FC4A0C7067E5535DD51E148736BD62599DD0D084052CAF892B56D728ECDA991B5706E8247FD91F10DA40448AAB4B1DB5499172B5]";
  private static final long validate=TimeUnit.MINUTES.toMillis(30);
  
 public String GenerateToken(UserDetails userDetails) {
	 Map<String,Object> claims=new HashMap<>();
	 return Jwts.builder()
	 .claims()
	 .add(claims)
	 .subject(userDetails.getUsername())
	 .issuedAt(Date.from(Instant.now()))
	 .expiration(Date.from(Instant.now().plusMillis(validate)))
	 .and()
	 .signWith(GenerateKey())
	 .compact();
 }
    private SecretKey GenerateKey() {
	  byte[] decodedkey=Decoders.BASE64.decode(key);
	   return Keys.hmacShaKeyFor(decodedkey);
 }
	public String extractUsername(String jwt) {
		Claims claims= getClaims(jwt);
		return claims.getSubject();
	}
		
		private Claims getClaims(String jwt) {
			return Jwts.parser()
					.verifyWith(GenerateKey())
					.build()
					.parseSignedClaims(jwt)
					.getPayload();
		}
		
	public boolean isTokenValid(String jwt) {
		Claims claims=getClaims(jwt);
		return claims.getExpiration().after(Date.from(Instant.now()));
	}

}
