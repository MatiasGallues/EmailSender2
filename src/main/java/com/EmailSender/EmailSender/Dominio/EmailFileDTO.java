package com.EmailSender.EmailSender.Dominio;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmailFileDTO {

    private String[] toSend;
    private String asunto;
    private String msj;
    private MultipartFile file;
}
