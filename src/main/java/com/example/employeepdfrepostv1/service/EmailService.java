package com.example.employeepdfrepostv1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@Async
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendMessage(String subject, String email, String text, InputStreamSource report) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
            helper.setFrom("lanregolowo@gmail.com");
            helper.setTo(email);
            helper.setText(text, true);
            helper.setSubject(subject);
            helper.addAttachment("report_test.pdf", report);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new IllegalStateException("failed to send email");
        }
    }
}
