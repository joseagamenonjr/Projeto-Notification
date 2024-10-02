package com.desafio.resources;

import com.desafio.entities.User;
import com.desafio.entities.enums.UserRole;
import com.desafio.repositories.UserRepository;
import com.desafio.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/users")
public class UserResources {

    @Autowired
    private UserService service;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    //retorna a lista de usuarios
    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        List<User> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id,@AuthenticationPrincipal UserDetails userDetails) {
        User user = service.findById(id);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        User currentUser = userRepository.findByLogin(userDetails.getUsername());
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        boolean isAdmin = currentUser.getRole() == UserRole.ADMIN;

        boolean targetIsAdmin = user.getRole() == UserRole.ADMIN;

        if (!isAdmin && !currentUser.getId().equals(user.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        if (!isAdmin && targetIsAdmin) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        }
        User obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    //Delete um user - ResquestBody pra converter para obj o JSON -- Anotação do path pra reconhecer que é uma variavel
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/optout")
    public ResponseEntity<User> updateOptOut(@PathVariable Long id, @RequestBody Map<String, Boolean> body) {
        Boolean optOut = body.get("optOut");
        User user = service.findById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        user.setOptOut(optOut);
        service.save(user);

        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateData(@PathVariable Long id, @RequestBody User obj, @AuthenticationPrincipal UserDetails userDetails) {
        User user = service.findById(id);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        User currentUser = userRepository.findByLogin(userDetails.getUsername());
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        boolean isAdmin = currentUser.getRole() == UserRole.ADMIN;

        boolean targetIsAdmin = user.getRole() == UserRole.ADMIN;

        if (!isAdmin && !currentUser.getId().equals(user.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        if (!isAdmin && targetIsAdmin) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        }
        User updatedUser = service.update(id, obj);
        return ResponseEntity.ok().body(updatedUser);
    }
}