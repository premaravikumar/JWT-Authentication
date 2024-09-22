package com.SpringBootJwtAuthentication;

import javax.crypto.SecretKey;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import io.jsonwebtoken.Jwts;
import jakarta.xml.bind.DatatypeConverter;

@SpringBootTest
public class JwtSecretKey {
	
	@Test
	public void SecretKeyGenerator() {
		
	SecretKey key=Jwts.SIG.HS512.key().build();
	String encodedKey=DatatypeConverter.printHexBinary(key.getEncoded());
	System.out.printf("\n key=[%s]\n",encodedKey);
		
	}

}
