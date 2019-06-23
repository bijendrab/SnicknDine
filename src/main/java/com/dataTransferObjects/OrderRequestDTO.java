package com.dataTransferObjects;

import com.model.CartItem;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

public class OrderRequestDTO implements Serializable {

   /* @NotNull
    private int reservationId;*/

    /*@NotNull
    private int orderId;*/

    @NotNull
    private int cartId;

    //private List<CartItem> orderedItems;

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    /*public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }*/

    /*public int getOrderId() {on
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }*/

    /*public List<CartItem> getOrderedItems() {
        return orderedItems;
    }

    public void setOrderedItems(List<CartItem> orderedItems) {
        this.orderedItems = orderedItems;
    }*/


}
