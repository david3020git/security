package com.example.security.config;
import org.springframework.security.crypto.bcrypt.BCrypt;
public class PasswordUtil {

    public static String decodePassword(String encodedPassword) {
        return BCrypt.hashpw(encodedPassword, BCrypt.gensalt());
    }

}
