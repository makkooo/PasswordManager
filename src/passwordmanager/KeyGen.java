package passwordmanager;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/*
 * The KeyGen class contains methods that 
 * derives a 256 bit AES key from a password using 
 * Password Based Key Derivation Algorithm 2 (PBKDF2).
 */
public class KeyGen {
	
	/*
	 * Algorithm used to derive an 256 bit AES key.
	 * Also computes a Hash-based Message Authentication Code (HMAC)
	 * using the SHA256 hashing function.
	 */
	private final String AES_KEY_ALGO = "PBKDF2WithHmacSHA256";
	
	/*
	 * The specification of the key using AES Encryption.
	 */
	private final String KEYSPEC_ALGO = "AES";
	
	/*
	 * Used for generating pseudorandom nonce (number used once).
	 * 
	 * @param numOfBytes number of bytes to be generated.
	 * 
	 * @return byte array containing nonce.
	 */
	public byte[] getRandomNonce(int numOfBytes) {
		
		byte[] nonce = new byte[numOfBytes];
		new SecureRandom().nextBytes(nonce);
		return nonce;
	}
	
	/*
	 * Derives an AES key from a password
	 * 
	 * @param password used for deriving key.
	 * @param salt pseudorandom nonce added to produce
	 * 			   unique password hashes.
	 * 
	 * @return a SecretKey.
	 * 
	 * @throw NoSuchAlgorithmException if algorithm is not available.
	 * @throw InvalidKeySpecException for invalid key specification.
	 */
	public SecretKey getAESKeyFromPass(char[] password, byte[] salt) 
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		
		/*
		 * Converts key(Key) into key specifications(KeySpec).
		 */
		SecretKeyFactory factory = SecretKeyFactory.getInstance(AES_KEY_ALGO);
		
		/*
		 * Derives key using function pbkdf2(password, salt, iterations, key length).
		 * Password and salt are hashed 100,000 times to produce 256bit key length.
		 */
		KeySpec keySpec = new PBEKeySpec(password, salt, 100000, 256);
		
		/*
		 * Generates the SecretKey.
		 */
		SecretKey secretKey = new SecretKeySpec(factory.generateSecret(keySpec).getEncoded(), KEYSPEC_ALGO);
		//System.out.println(hex(secretKey.getEncoded()));
		return secretKey;
	}
	
	/*
	 * Converts byte array to hexadecimal
	 * 
	 * @param bytes byte array
	 * 
	 * @return String value of hexadecimal
	 */
	public String hex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }
}