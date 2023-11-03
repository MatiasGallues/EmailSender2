package com.EmailSender.EmailSender.Controlador;

import com.EmailSender.EmailSender.models.UserEntity;
import com.EmailSender.EmailSender.repositorios.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TestRolesController {

    @Autowired
    private UserRepository userRepository;

   /* @GetMapping("/accessAdmin")
    public List<UserEntity> accessAdmin(){
        userRepository.findByEmailLessThan()

        return users;
    }*/

}
