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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class principalController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/validate")
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

    @PostMapping("/accessAdmin")
    public void listarUsuarios(){
        List<UserEntity> list = new ArrayList<>(userRepository.findAllByCantEmailIsNotNull());
        list.forEach(System.out::println);
    }

    @DeleteMapping("/deleteUser")
    public String deleteUser(@RequestParam String id) {

        userRepository.deleteById(Long.parseLong(id));

        return "se ha borrado el user con id: ".concat(id);

    }

}
