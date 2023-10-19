package com.EmailSender.EmailSender.Dominio;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmailDTO {

    private String[] toSend;

    private String asunto;

    private String msj;
}
