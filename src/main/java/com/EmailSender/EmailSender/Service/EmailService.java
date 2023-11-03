package com.EmailSender.EmailSender.Service;

import java.io.File;

public interface EmailService {
    void sendEmail(String from, String[] toSend, String asunto, String msj);

    void sendEmailCon_Adjunto(String[] toSend, String asunto, String msj, File arch);


}
