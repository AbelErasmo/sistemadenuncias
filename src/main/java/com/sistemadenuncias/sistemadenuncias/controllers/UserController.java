package com.sistemadenuncias.sistemadenuncias.controllers;

import com.sistemadenuncias.sistemadenuncias.models.User;
import com.sistemadenuncias.sistemadenuncias.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/reparticao/{id}")
    public ResponseEntity<List<User>> listarUsuariorPorReparticao(@PathVariable Integer id) {
        List<User> usuarios = userService.listarUsuariosPorReparticao(id);
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping
    public ResponseEntity<User> cadastrarUsuario(@RequestBody User user) {
        User newUser = userService.cadastrar(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }
}
