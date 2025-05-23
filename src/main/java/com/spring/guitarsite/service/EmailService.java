package com.spring.guitarsite.service;

import com.spring.guitarsite.exception.EmailSendException;

public interface EmailService {

    void sendPasswordResetEmail(String to, String resetUrl) throws EmailSendException;

}
