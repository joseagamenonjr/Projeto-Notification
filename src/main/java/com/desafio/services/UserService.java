package com.desafio.services;

import com.desafio.entities.User;
import com.desafio.exception.ResourceNotFoundException;
import com.desafio.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> findAll(){
        return repository.findAll();
    }

    public User findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public User save(User user) {
        return repository.save(user);
    }

    public void delete(Long id){
      repository.deleteById(id);
    }

    public User update(Long id, User obj) {
        User entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        updateData(entity, obj);
        return repository.save(entity);
    }

    private void updateData(User entity, User obj) {
        System.out.println("Updating user: " + entity.getId());
        if (obj.getName() != null) {
            entity.setName(obj.getName());
        }
        if (obj.getEmail() != null) {
            entity.setEmail(obj.getEmail());
        }
        if (obj.getCell() != null) {
            entity.setCell(obj.getCell());
        }
        if (obj.getPassword() != null) {
            entity.setPassword(passwordEncoder.encode(obj.getPassword()));
        }
        if (obj.getUsername() != null) {
            entity.setLogin(obj.getUsername());
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByLogin(username);
    }

    public User findByLogin(String login) {
        return repository.findByLogin(login);
    }

    public void registerOptOut(Long userId, boolean optOutValue) {
        User user = repository.findById(userId).orElseThrow(() ->
                new RuntimeException("Usuário não encontrado: " + userId));
        user.setOptOut(optOutValue);
        repository.save(user);
    }
}
