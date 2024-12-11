package dha.libapp.services.authen;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Service class responsible for handling password-related operations,
 * such as hashing passwords before storing them securely.
 */
public class PasswordService {

    /**
     * Hashes the given password using the SHA-256 algorithm.
     *
     * @param password The password to be hashed.
     * @return The hashed password as a hexadecimal string.
     * @throws NoSuchAlgorithmException If the specified hashing algorithm (SHA-256) is not available.
     */
    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        byte[] hashedBytes = digest.digest(password.getBytes());

        StringBuilder hexString = new StringBuilder();
        for (byte b : hashedBytes) {
            hexString.append(String.format("%02x", b));
        }

        return hexString.toString();
    }
}
