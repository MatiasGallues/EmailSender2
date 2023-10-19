package com.EmailSender.EmailSender.Controlador;

import com.EmailSender.EmailSender.models.UserEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TestRolesController {

    @GetMapping("/accessAdmin")
    public List<UserEntity> accessAdmin(){
        List <UserEntity> users = new ArrayList();

        return users;
    }

}
