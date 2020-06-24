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
        response.put("message", cartItemService.addItemFromMenu(userCartItem));
        response.put("body", "");
        response.put("status", HttpStatus.ACCEPTED);
        response.put("error", false);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.ACCEPTED);
    }

    /*
     * @Description: Function to add Item from Cart
     *
     * */
    @PutMapping("/addItemFromCart/{cartItemId}")
    public ResponseEntity<?> addItemFromCart(@PathVariable String cartItemId) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", cartItemService.addItemFromCart(Long.parseLong(cartItemId)));
        response.put("body", "");
        response.put("status", HttpStatus.ACCEPTED);
        response.put("error", false);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.ACCEPTED);
    }

    /*
     * @Description: Function to remove CartItem completely from user's cart
     *
     * */
    @DeleteMapping("/delete/{cartItemId}")
    public ResponseEntity<?> removeCartItem(@PathVariable String cartItemId) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", cartItemService.deleteCartItemById(Long.parseLong(cartItemId)));
        response.put("body", "");
        response.put("status", HttpStatus.ACCEPTED);
        response.put("error", false);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.ACCEPTED);
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
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.ACCEPTED);
    }


    /*
     * @Description: Function to Decrement or Remove cartItem from cart
     *
     * */
    @DeleteMapping("/subtractItemFromCart/{cartItemId}")
    public ResponseEntity<?> decrementCartItemQuantity(@PathVariable String cartItemId) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", cartItemService.subtractCartItem(Long.parseLong(cartItemId)));
        response.put("body", "");
        response.put("status", HttpStatus.ACCEPTED);
        response.put("error", false);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.ACCEPTED);
    }
}
