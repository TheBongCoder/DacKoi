package com.thebongcoder.dackoi.service;

import com.thebongcoder.dackoi.utils.EmailConstants;
import com.thebongcoder.dackoi.utils.MessageConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String sender;

    public String sendOTP(String receiverEmail, String receiverName, String otp) {
        log.info("Account verify link send to email - {}", receiverEmail);
        Context context = new Context();
        context.setVariable(EmailConstants.FULLNAME, receiverName);
        context.setVariable(EmailConstants.OTP, otp);
        sendEmail(receiverEmail, context, EmailConstants.SUBJECT_ACCOUNT_VERIFY, EmailConstants.TEMPLATE_ACCOUNT_VERIFY);
        return MessageConstant.EMAIL_SENT_SUCCESSFULLY;
    }

    public void sendEmail(String receiverEmail, Context context, String subject, String template) {
        CompletableFuture.runAsync(() -> {
            try {
                String emailBody = templateEngine.process(template, context);
                MimeMessage mimeMessage = javaMailSender.createMimeMessage();
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                message.setSubject(subject);
                message.setText(emailBody, true);
                message.setTo(receiverEmail);
                message.setFrom(sender);
                javaMailSender.send(mimeMessage);

            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
