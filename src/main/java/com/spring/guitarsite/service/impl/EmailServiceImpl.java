package com.spring.guitarsite.service.impl;

import com.spring.guitarsite.exception.EmailSendException;
import com.spring.guitarsite.service.EmailService;
import lombok.RequiredArgsConstructor;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendPasswordResetEmail(String to, String resetUrl) throws EmailSendException {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("galyavet548@guitarguru.com");
            message.setTo(to);
            message.setSubject("Сброс пароля");
            message.setText("Для сброса пароля перейдите по ссылке: " + resetUrl);
            mailSender.send(message);
        } catch (MailException e) {
            System.err.println("Ошибка отправки email: " + e.getMessage());
            throw new EmailSendException("Не удалось отправить email", e);
        }
    }
}