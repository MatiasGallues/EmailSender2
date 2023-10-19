package com.EmailSender.EmailSender.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Value("${email.sender}")
    private String emailUser;

    @Value("${email.Pass}")
    private String emailPass;

    @Bean
    public JavaMailSender getJavaMailSender(){

        JavaMailSenderImpl emailSender = new JavaMailSenderImpl();

        try{

            emailSender.setHost("smtp.gmail.com");

        }catch (Exception e){
            emailSender.setHost("smtp.office365.com");
        }
        emailSender.setPort(587);
        emailSender.setUsername(emailUser);
        emailSender.setPassword(emailPass);

        Properties prop=emailSender.getJavaMailProperties();
        prop.put("mail.transport.protocol", "smtp");
        prop.put("mail.smtp.auth","true");
        prop.put("mail.smtp.starttls.enable","true");
        prop.put("mail.debug","true");

        return emailSender;
    }
}
