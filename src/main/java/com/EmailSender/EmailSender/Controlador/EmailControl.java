package com.EmailSender.EmailSender.Controlador;

import com.EmailSender.EmailSender.Dominio.EmailDTO;
import com.EmailSender.EmailSender.Dominio.EmailFileDTO;
import com.EmailSender.EmailSender.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1")
public class EmailControl {

    @Autowired
    private EmailService emailService;

    @PostMapping("/sendMessage")
    public ResponseEntity<?> receiveRequestEmail(@RequestBody EmailDTO emailDTO){

        System.out.println("mensaje: " + emailDTO);
        emailService.sendEmail(emailDTO.getToSend(),emailDTO.getAsunto(),emailDTO.getMsj());


        emailService.sendEmail(new String[]{""}, "TEST", "PROBANDO UNO DOS TRES");

        Map<String, String> response = new HashMap<>();
        response.put("estado", "Enviado");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/sendMessageFile")
    public ResponseEntity<?> receiveRequestEmailConFile(@ModelAttribute EmailFileDTO emailFileDTO ){

        try{
            String fileName=emailFileDTO.getFile().getName();
            Path path= Paths.get("src/mail/resourse/files/" + fileName);

            Files.createDirectories(path.getParent());
            Files.copy(emailFileDTO.getFile().getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            File file = path.toFile();
            emailService.sendEmailCon_Adjunto(emailFileDTO.getToSend(), emailFileDTO.getAsunto(), emailFileDTO.getMsj(), file);

            Map<String, String> response = new HashMap<>();
            response.put("estado", "Enviado");
            response.put("Archivo", fileName);

            return ResponseEntity.ok(response);
        }catch(Exception e){

            throw new RuntimeException("Error al enviar con Adjunto" + e.getMessage());
        }


    }


}
