package com.spring.guitarsite.exception;

import org.springframework.mail.MailException;

public class EmailSendException extends Throwable {
    public EmailSendException(String message, MailException e) {
    }
}
