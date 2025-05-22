package com.ravenprojects.uchuva_bank.service;

import com.password4j.Hash;
import com.password4j.Password;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoder {


    @Value("${password.pepper}")
    private String pepper;

    public String protectPassword(String userPassword) {
        Hash hash = Password.hash(userPassword)
                .addPepper(pepper)
                .addRandomSalt(32)
                .withArgon2();

        return hash.getResult();
    }
}
