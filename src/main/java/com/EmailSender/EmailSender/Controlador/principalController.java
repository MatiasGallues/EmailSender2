package com.EmailSender.EmailSender.Controlador;

import com.EmailSender.EmailSender.Controlador.request.CreateUserDTO;
import com.EmailSender.EmailSender.models.ERole;
import com.EmailSender.EmailSender.models.RoleEntity;
import com.EmailSender.EmailSender.models.UserEntity;
import com.EmailSender.EmailSender.repositorios.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class principalController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/validar")
    public String validar(){
        return "este get es publico para pruebas";
    }

    @PostMapping("/createUser")
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserDTO createUserDTO){

        Set<RoleEntity> roles = createUserDTO.getRoles().stream()
                .map(role -> RoleEntity.builder()
                        .name(ERole.valueOf(role))
                        .build())
                .collect(Collectors.toSet());

        UserEntity userEntity = UserEntity.builder()
                .username(createUserDTO.getUsername())
                .password(passwordEncoder.encode(createUserDTO.getPassword()))
                .email(createUserDTO.getEmail())
                .cantEmail(createUserDTO.getCantEmail())
                .roles(roles)
                .build();

    userRepository.save(userEntity);

    return ResponseEntity.ok(userEntity);
    }

    @DeleteMapping("/deleteUser")
    public String deleteUser(@RequestParam String id) {

        userRepository.deleteById(Long.parseLong(id));

        return "se ha borrado el user con id: ".concat(id);

    }

}
