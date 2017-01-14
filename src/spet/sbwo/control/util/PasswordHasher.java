package spet.sbwo.control.util;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spet.sbwo.control.ControlError;
import spet.sbwo.control.ControlException;
import spet.sbwo.control.channel.UserChannel;

public class PasswordHasher {
	private static final Logger LOG = LoggerFactory.getLogger(PasswordHasher.class);

	private static final int DEFAULT_ITERATIONS = 100;
	private static final int DEFAULT_SALT_LENGTH = 32;
	private static final int DEFAULT_HASH_LENGTH = 512;

	private int iterations;
	private int saltLength;
	private int hashLength;

	public PasswordHasher() {
		this(DEFAULT_ITERATIONS, DEFAULT_SALT_LENGTH, DEFAULT_HASH_LENGTH);
	}

	public PasswordHasher(int iterations, int saltLength, int hashLength) {
		this.iterations = iterations;
		this.saltLength = saltLength;
		this.hashLength = hashLength;
	}

	public boolean checkPassword(String input, String hash, String salt) throws ControlException {
		try {
			byte[] saltb = Base64.getDecoder().decode(salt);
			byte[] hashb = Base64.getDecoder().decode(hash);
			SecretKey key = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
					.generateSecret(new PBEKeySpec(input.toCharArray(), saltb, this.iterations, this.hashLength));
			return Arrays.equals(hashb, key.getEncoded());
		} catch (Exception e) {
			LOG.error("Unable to check a password.", e);
			throw new ControlException(ControlError.TECHNICAL, UserChannel.class);
		}
	}

	public HashedPasswordInfo hashPassword(String password) throws ControlException {
		try {
			byte[] salt = this.buildSalt();
			SecretKey key = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
					.generateSecret(new PBEKeySpec(password.toCharArray(), salt, this.iterations, this.hashLength));
			return new HashedPasswordInfo(key.getEncoded(), salt);
		} catch (Exception e) {
			LOG.error("Unable to hash a password.", e);
			throw new ControlException(ControlError.TECHNICAL, UserChannel.class);
		}
	}

	protected byte[] buildSalt() throws ControlException {
		try {
			return SecureRandom.getInstance("SHA1PRNG").generateSeed(this.saltLength);
		} catch (Exception e) {
			LOG.error("Unable to build salt for hashing a password.", e);
			throw new ControlException(ControlError.TECHNICAL, UserChannel.class);
		}
	}

	public static class HashedPasswordInfo {
		private String hash;
		private String salt;

		HashedPasswordInfo(byte[] hash, byte[] salt) {
			this.hash = Base64.getEncoder().encodeToString(hash);
			this.salt = Base64.getEncoder().encodeToString(salt);
		}

		public String getHash() {
			return hash;
		}

		public String getSalt() {
			return salt;
		}

	}
}
