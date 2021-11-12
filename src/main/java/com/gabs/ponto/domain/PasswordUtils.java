package com.gabs.ponto.domain;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtils {
	public PasswordUtils() {}
	
	public static String encrypt(String senha, int level) {
		if(senha == null) {
			return senha;
		}
		var encoder = new BCryptPasswordEncoder(level);
		return encoder.encode(senha);
	}
}