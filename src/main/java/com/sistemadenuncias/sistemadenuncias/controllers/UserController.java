package com.sistemadenuncias.sistemadenuncias.controllers;

import com.sistemadenuncias.sistemadenuncias.DTO.UserRequestDTO;
import com.sistemadenuncias.sistemadenuncias.DTO.UserResponseDTO;
import com.sistemadenuncias.sistemadenuncias.models.User;
import com.sistemadenuncias.sistemadenuncias.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> criar(@RequestBody UserRequestDTO dto) {
        User user = userService.criar(dto);
        UserResponseDTO response = userService.toResponseDTO(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/reparticao/{id}")
    public ResponseEntity<List<UserResponseDTO>> listarPorReparticao(@PathVariable Integer id) {
        List<UserResponseDTO> lista = userService.listarPorReparticao(id);
        return ResponseEntity.ok(lista);
    }
}
