package com.desafio.services;

import com.desafio.entities.Order;
import com.desafio.entities.Payment;
import com.desafio.entities.enums.Status;
import com.desafio.repositories.OrderRepository;
import com.desafio.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private PaymentRepository paymentRepository;

    public List<Order> findAll(){
        return repository.findAll();
    }

    public Order findById(long id){
        Optional<Order> obj = repository.findById(id);
        return obj.get();
    }

    public Order save(Order order) {
        Payment payment = new Payment();
        payment.setMoment(Instant.now());
        payment.setOrder(order);
        if(order.getOrderStatus() == Status.PENDING){
            order.setPayment(null);
        } else {
            order.setPayment(payment);
        }
        Order savedOrder = repository.save(order);
        return savedOrder;
    }

    public List<Order> findByClientId(Long clientId) {
        return repository.findByClientId(clientId);
    }

    public List<Order> findByUserId(Long userId) {
        return repository.findByClientId(userId);
    }
}
