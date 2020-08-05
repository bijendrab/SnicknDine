package com.wityo.modules.cart.controller;

import com.wityo.modules.cart.model.UserCartItem;
import com.wityo.modules.cart.service.CartItemService;
import com.wityo.modules.product.model.Product;
import com.wityo.modules.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/cartitem")
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    /*
     * @Description: Function to add/update CartItem in user's cart.
     *
     * */
    @PostMapping("/addItemFromMenu")
    public ResponseEntity<?> addItemFromMenu(@RequestBody UserCartItem userCartItem) {
        Map<String, Object> response = new HashMap<>();
        String cartStatus = cartItemService.addItemFromMenu(userCartItem);
        if(cartStatus.contains("Cart Items exist") ||
            cartStatus.contains("Order is going on") ||
            cartStatus.contains("User has not selected restaurant")||
            cartStatus.contains("Binding is not there between")){
            response.put("message", "Add Item from Menu to Cart");
            response.put("body", cartStatus);
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
            response.put("error", true);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "add item from Menu to Cart");
        response.put("body", cartStatus);
        response.put("status", HttpStatus.ACCEPTED);
        response.put("error", false);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    /*
     * @Description: Function to add Item from Cart
     *
     * */
    @PutMapping("/addItemFromCart/{cartItemId}")
    public ResponseEntity<?> addItemFromCart(@PathVariable String cartItemId) {
        Map<String, Object> response = new HashMap<>();
        String cartStatus = cartItemService.addItemFromCart(Long.parseLong(cartItemId));
        if(cartStatus.contains("Binding")){
            response.put("message", "add item in cart");
            response.put("body", cartStatus);
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
            response.put("error", true);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "add item in cart");
        response.put("body", cartStatus);
        response.put("status", HttpStatus.ACCEPTED);
        response.put("error", false);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    /*
     * @Description: Function to remove CartItem completely from user's cart
     *
     * */
    @DeleteMapping("/delete/{cartItemId}")
    public ResponseEntity<?> removeCartItem(@PathVariable String cartItemId) {
        Map<String, Object> response = new HashMap<>();
        String Status = cartItemService.deleteCartItemById(Long.parseLong(cartItemId));
        if(!Status.equals("deleted")){
            response.put("message", "delete cartItem");
            response.put("body", Status);
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
            response.put("error", true);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "delete cartItem");
        response.put("body", Status);
        response.put("status", HttpStatus.ACCEPTED);
        response.put("error", false);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }


    /*
     * @Description: Function to remove all CartItems from user's cart
     *
     * */
    @DeleteMapping("/delete/cartItems")
    public ResponseEntity<?> removeAllCartItems() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String, Object> response = new HashMap<>();
        response.put("message", cartItemService.removeAllCartItems(user.getCustomer().getCart()));
        response.put("body", "");
        response.put("status", HttpStatus.ACCEPTED);
        response.put("error", false);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    /*
     * @Description: Function to Decrement or Remove cartItem from cart
     *
     * */
    @PutMapping("/subtractItemFromMenu")
    public ResponseEntity<?> decrementItemQuantityFromMenu(@RequestBody UserCartItem userCartItem) {
        Map<String, Object> response = new HashMap<>();
        String cartStatus = cartItemService.subtractItemFromMenu(userCartItem);
        if(cartStatus.contains("Binding")){
            response.put("message", "subtract item from Menu");
            response.put("body", cartStatus);
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
            response.put("error", true);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "subtract item from Menu");
        response.put("body", cartStatus);
        response.put("status", HttpStatus.ACCEPTED);
        response.put("error", false);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    /*
     * @Description: Function to Decrement or Remove cartItem from cart
     *
     * */
    @DeleteMapping("/subtractItemFromCart/{cartItemId}")
    public ResponseEntity<?> decrementCartItemQuantity(@PathVariable String cartItemId) {
        Map<String, Object> response = new HashMap<>();
        String cartStatus = cartItemService.subtractCartItem(Long.parseLong(cartItemId));
        if(cartStatus.contains("Binding")){
            response.put("message", "subtract item from Cart");
            response.put("body", cartStatus);
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
            response.put("error", true);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "subtract item from Cart");
        response.put("body", cartStatus);
        response.put("status", HttpStatus.ACCEPTED);
        response.put("error", false);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }
}
