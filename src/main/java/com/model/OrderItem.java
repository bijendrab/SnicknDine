package com.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Data
@Entity
@Table(name = "orderitem")
public class OrderItem implements Serializable {


    private static final long serialVersionUID = -2455760938054036364L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int orderItemId;

    @Temporal(value= TemporalType.TIMESTAMP)
    private Date orderCreationTime;

    private int quality;

    private double price;

    private String status;

    private int cartId;

    private int productId;

    /*private Date OrderEndTime;*/

    private double waitTime;



    public OrderItem() {
    }

    protected boolean canEqual(Object other) {
        return other instanceof com.model.CartItem;
    }

}
