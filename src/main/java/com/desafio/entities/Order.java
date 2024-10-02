package com.desafio.entities;

import com.desafio.entities.enums.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "pedido")
public class Order implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private LocalDateTime moment;

    private Integer orderStatus;

    private String notificationMessage;

    private Double total;
    //1 user pode ter muitos pedidos
    @ManyToOne
    @JoinColumn(name = "client_id")
    private User client;

    //1 pedido poder ter varios itens, identifico pelo id do pedido
    @OneToMany(mappedBy = "id.order")
    private Set<OrderItem> items = new HashSet<>();

    //1 pedido tem 1 pagamento
    @OneToOne(mappedBy = "order",cascade = CascadeType.ALL)
    private Payment payment;

    public Order() {
    }

    public Order(Long id, LocalDateTime moment, Status status, User client) {
        this.id = id;
        this.moment = moment;
        setStatus(status);
        this.client = client;
    }

    public Double getTotal(){
        double sum = 0.0;
        for(OrderItem item : items){
            sum += item.getSubTotal();
        }
        return sum;
    }

    public String getNotificationMessage() {
        return notificationMessage;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public void setNotificationMessage(String notificationMessage) {
        this.notificationMessage = notificationMessage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getMoment() {
        return moment;
    }

    public void setMoment(LocalDateTime moment) {
        this.moment = moment;
    }

    public Status getOrderStatus() {
        return Status.valueOf(orderStatus);
    }

    public void setStatus(Status status) {
        if(status != null) {
            this.orderStatus = status.getCode();
        }
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public Set<OrderItem> getItems(){
        return items;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id);
    }


    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
