package com.desafio.resources;

import com.desafio.entities.User;
import com.desafio.repositories.UserRepository;
import com.desafio.resources.dto.AuthenticationDTO;
import com.desafio.resources.dto.LoginResponseDTO;
import com.desafio.resources.dto.RegisterDTO;
import com.desafio.services.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationResources {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository repository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder; 

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
        System.out.println("Login attempt for user: " + data.login());
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody @Valid RegisterDTO data) {
        if (this.repository.findByLogin(data.login()) != null) {
            return ResponseEntity.badRequest().body(null);
        }
        String encryptedPassword = passwordEncoder.encode(data.password());
        User user = new User(data.login(), encryptedPassword, data.role());
        user.setName(data.name());
        user.setEmail(data.email());
        user.setCell(data.cell());
        this.repository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}
