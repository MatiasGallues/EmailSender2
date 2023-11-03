package com.EmailSender.EmailSender.Service;

import com.EmailSender.EmailSender.Security.filters.JwtAuthenticationFilter;
import com.EmailSender.EmailSender.Security.filters.JwtAuthorizationFilter;
import com.EmailSender.EmailSender.config.Configuration;
import com.EmailSender.EmailSender.models.UserEntity;
import com.EmailSender.EmailSender.repositorios.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
public class EmailServiceImpl implements EmailService{

    @Value("${email.sender}")
    private String emailUser;


    @Autowired
    private JavaMailSender emailSender;



    @Override
    public void sendEmail(String from, String[] toSend, String asunto, String msj) {


        SimpleMailMessage emailMsj=new SimpleMailMessage();

        emailMsj.setFrom(emailUser);
        emailMsj.setTo(toSend);
        emailMsj.setSubject(asunto);
        emailMsj.setText(msj);
        emailSender.send(emailMsj);
    }

    @Override
    public void sendEmailCon_Adjunto(String[] toSend, String asunto, String msj, File arch) {

        try {
            MimeMessage mimeMessage= emailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper= new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.name());

            mimeMessageHelper.setFrom(emailUser);
            mimeMessageHelper.setTo(toSend);
            mimeMessageHelper.setSubject(asunto);
            mimeMessageHelper.setText(msj);
            mimeMessageHelper.addAttachment(arch.getName(), arch);


            emailSender.send(mimeMessage);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
