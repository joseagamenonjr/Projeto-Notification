package com.desafio.repositories;

import com.desafio.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
        User findByLogin(String login);
        Optional<User> findByOrders_Id(Long orderId);

}
