package com.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


@Data
@Entity
@Table(name = "orderitem")
public class OrderItem implements Serializable {


    private static final long serialVersionUID = -2455760938054036364L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int orderItemId;

    private int quality;

    private double price;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "cartId")
    @JsonIgnore
    private Cart cart;

    public OrderItem() {
    }

    protected boolean canEqual(Object other) {
        return other instanceof com.model.CartItem;
    }

}
