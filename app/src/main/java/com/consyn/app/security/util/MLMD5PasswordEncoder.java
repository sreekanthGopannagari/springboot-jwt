package com.consyn.app.security.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.consyn.app.exception.ConsynRuntimeException;

@Component
public class MLMD5PasswordEncoder implements PasswordEncoder {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public String encode(CharSequence rawPassword) {
		String password = rawPassword + "";
		String myHash = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(password.getBytes());

			byte[] digest = md.digest();
			myHash = DatatypeConverter.printHexBinary(digest);
		} catch (NoSuchAlgorithmException e) {
			logger.error("NoSuchAlgorithm  error", e);
			throw new ConsynRuntimeException("NoSuchAlgorithmException");
		}

		return myHash;
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		if (rawPassword == null) {
			throw new IllegalArgumentException("rawPassword cannot be null");
		}
		if (encodedPassword == null || encodedPassword.length() == 0) {
			this.logger.warn("Empty encoded  password");
			return false;
		}
		return encodedPassword.equalsIgnoreCase(encode(rawPassword));
	}

}
