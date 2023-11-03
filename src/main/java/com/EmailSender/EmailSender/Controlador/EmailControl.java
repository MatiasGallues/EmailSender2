package com.EmailSender.EmailSender.Controlador;

import com.EmailSender.EmailSender.Dominio.EmailDTO;
import com.EmailSender.EmailSender.Dominio.EmailFileDTO;
import com.EmailSender.EmailSender.Security.jwt.JwtUtils;
import com.EmailSender.EmailSender.Service.EmailService;
import com.EmailSender.EmailSender.models.RoleEntity;
import com.EmailSender.EmailSender.models.UserEntity;
import com.EmailSender.EmailSender.repositorios.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
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
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/v1")
public class EmailControl {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    JwtUtils jwtUtils;

    String username;
    String token;

    UserEntity userNew=new UserEntity();

    Map<String, String> response = new HashMap<>();
    @PostMapping("/sendMessage")
    public ResponseEntity<?> receiveRequestEmail(@RequestBody EmailDTO emailDTO, HttpServletRequest request) {

        System.out.println("mensaje: " + emailDTO);
        String tokenHeader = request.getHeader("Authorization");

        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            token = tokenHeader.substring(7);
        }


        if (jwtUtils.isTokenValid(token)) {
             username = jwtUtils.getUsernameFromToken(token);

            System.out.println(username);
        }

        Optional<UserEntity> user=userRepository.findByUsername(username);

        System.out.println(user.get().getEmail());
        String email=user.get().getEmail();
        Integer countEmail = user.get().getCantEmail();
        if(countEmail<1000) {
            emailService.sendEmail(email, emailDTO.getToSend(), emailDTO.getAsunto(), emailDTO.getMsj());

           // userNew.setUsername(user.get().getUsername());
           // userNew.setPassword(user.get().getPassword());
           // userNew.setEmail(user.get().getEmail());
           // userNew.setRoles(user.get().getRoles());
           // userNew.setCantEmail(countEmail + 1);
           // userRepository.save(userNew);

            response.put("estado", "Enviado");
            return ResponseEntity.ok(response);
        }
        else{

            return ResponseEntity.badRequest().build();
        }



    }

    @PostMapping("/sendMessageFile")
    public ResponseEntity<?> receiveRequestEmailConFile(@ModelAttribute EmailFileDTO emailFileDTO) {

        try {
            String fileName = emailFileDTO.getFile().getName();
            Path path = Paths.get("src/mail/resourse/files/" + fileName);

            Files.createDirectories(path.getParent());
            Files.copy(emailFileDTO.getFile().getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            File file = path.toFile();
            emailService.sendEmailCon_Adjunto(emailFileDTO.getToSend(), emailFileDTO.getAsunto(), emailFileDTO.getMsj(), file);

            Map<String, String> response = new HashMap<>();
            response.put("estado", "Enviado");
            response.put("Archivo", fileName);

            return ResponseEntity.ok(response);
        } catch (Exception e) {

            throw new RuntimeException("Error al enviar con Adjunto" + e.getMessage());
        }


    }

    public UserEntity save(UserEntity user){
        return this.userRepository.save(user);
    }
}

