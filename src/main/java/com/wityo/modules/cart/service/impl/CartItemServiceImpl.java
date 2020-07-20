package com.wityo.modules.cart.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.wityo.common.WityoRestAppProperties;
import com.wityo.modules.cart.model.Cart;
import com.wityo.modules.cart.model.CartItem;
import com.wityo.modules.cart.model.SelectCartAddOnItems;
import com.wityo.modules.cart.model.UserCartItem;
import com.wityo.modules.cart.repository.CartItemRepository;
import com.wityo.modules.cart.service.CartItemService;
import com.wityo.modules.product.model.Product;
import com.wityo.modules.user.model.Customer;
import com.wityo.modules.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    CartItemRepository cartItemRepository;
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WityoRestAppProperties wityoRestAppProperties;

    public String addItemFromMenu(UserCartItem userCartItem) {
        try {
            User userDetail = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Customer customer = userDetail.getCustomer();
            Cart cart = customer.getCart();

            Set<CartItem> userCartItems = cart.getCartItems();
            String productId = userCartItem.getProductId();
            Product product = restTemplate.getForObject
                (wityoRestAppProperties.getWityoUserAppUrl() + "api/menu/" + userCartItem.getRestaurantId() + "/"+ userCartItem.getProductId() ,
                    Product.class);
            for (CartItem cartItem : userCartItems) {
                String productJson = cartItem.getProductJson();
                Product p = new Gson().fromJson(productJson, Product.class);
                if ((productId.equalsIgnoreCase(p.getProductId()) && cartItem.getQuantityOption().equals(userCartItem.getUserSelectQuantityOption()))) {
                    if (compareAddOn(cartItem, userCartItem)) {
                        double newPrice = cartItem.getPrice() / cartItem.getQuantity();
                        cartItem.setQuantity(cartItem.getQuantity() + 1);
                        cartItem.setPrice(cartItem.getQuantity() * newPrice);
                        cartItem.setUpdateItemInCartTime(LocalDateTime.now());
                        cartItemRepository.save(cartItem);
                        return "Item Incremented";
                    }
                }
            }
            CartItem newCartItem = new CartItem();
            newCartItem.setCart(cart);
            newCartItem.setItemName(product.getProductName());
            newCartItem.setQuantity(1);
            newCartItem.setProductJson(new Gson().toJson(product));
            product.getProductQuantityOptions().forEach(qOption2 -> {
                if (qOption2.getQuantityOption().equalsIgnoreCase(userCartItem.getUserSelectQuantityOption())) {
                    newCartItem.setPrice(qOption2.getPrice() * 1);
                    newCartItem.setQuantityOption(userCartItem.getUserSelectQuantityOption());
                }
            });
            newCartItem.setSelectCartAddOnItems(convertListToSet(userCartItem.getSelectCartAddOnItems()));
            for (SelectCartAddOnItems selectCartAddOnItems : newCartItem.getSelectCartAddOnItems()) {
                selectCartAddOnItems.setCartItem(newCartItem);
            }
            newCartItem.setAddItemToCartTime(LocalDateTime.now());
            newCartItem.setUpdateItemInCartTime(LocalDateTime.now());
            cartItemRepository.save(newCartItem);
            return "Item Added";

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "error";

    }

    private boolean compareAddOn(CartItem cartItem, UserCartItem userCartItem) {
        try {
            ObjectMapper Obj = new ObjectMapper();
            ArrayList<String> selectUserCartAddOnItemsArrayList = new ArrayList<>();
            ArrayList<String> selectCartAddOnItemsArrayList = new ArrayList<>();
            for (int i = 0; i < userCartItem.getSelectCartAddOnItems().size(); i++) {
                String jsonStr = Obj.writeValueAsString(userCartItem.getSelectCartAddOnItems().get(i));
                JSONObject jSONObject = new JSONObject(jsonStr);
                jSONObject.remove("cartAddOnId");
                jSONObject.remove("cartItem");
                selectUserCartAddOnItemsArrayList.add(jSONObject.toString());
            }
            for (SelectCartAddOnItems selectCartAddOnItems:cartItem.getSelectCartAddOnItems()) {
                String jsonStr = Obj.writeValueAsString(selectCartAddOnItems);
                JSONObject jSONObject = new JSONObject(jsonStr);
                jSONObject.remove("cartAddOnId");
                jSONObject.remove("cartItem");
                selectCartAddOnItemsArrayList.add(jSONObject.toString());
            }

            if(cartItem.getSelectCartAddOnItems().size() >= userCartItem.getSelectCartAddOnItems().size()) {
                for (SelectCartAddOnItems selectCartAddOnItems : cartItem.getSelectCartAddOnItems()) {
                    String jsonStr = Obj.writeValueAsString(selectCartAddOnItems);
                    JSONObject jSONObject = new JSONObject(jsonStr);
                    jSONObject.remove("cartAddOnId");
                    jSONObject.remove("cartItem");
                    if (selectUserCartAddOnItemsArrayList.contains(jSONObject.toString())) {
                        continue;
                    } else {
                        return false;
                    }

                }
            }
            else{
                for (SelectCartAddOnItems selectCartAddOnItems : userCartItem.getSelectCartAddOnItems()) {
                    String jsonStr = Obj.writeValueAsString(selectCartAddOnItems);
                    JSONObject jSONObject = new JSONObject(jsonStr);
                    jSONObject.remove("cartAddOnId");
                    jSONObject.remove("cartItem");
                    if (selectCartAddOnItemsArrayList.contains(jSONObject.toString())) {
                        continue;
                    } else {
                        return false;
                    }

                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public static <T> Set<T> convertListToSet(List<T> list) {
        return list.stream().collect(Collectors.toSet());
    }
    public String deleteCartItemById(Long cartItemId) {
        try {
            cartItemRepository.deleteById(cartItemId);
            return "deleted";
        } catch (Exception e) {
        }
        return "failure";
    }

    public String removeAllCartItems(Cart cart) {
        try {
            cart.getCartItems().parallelStream()
                    .forEach(cartItem -> cartItemRepository.deleteById(cartItem.getCartItemId()));
            return "all items deleted";
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "failure";
    }

    public String subtractItemFromMenu(UserCartItem userCartItem) {
        try {
            User userDetail = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Customer customer = userDetail.getCustomer();
            Cart cart = customer.getCart();

            Set<CartItem> userCartItems = cart.getCartItems();
            String productId = userCartItem.getProductId();
            for (CartItem cartItem : userCartItems) {
                String productJson = cartItem.getProductJson();
                Product p = new Gson().fromJson(productJson, Product.class);
                if ((productId.equalsIgnoreCase(p.getProductId()) && cartItem.getQuantityOption().equals(userCartItem.getUserSelectQuantityOption()))) {
                    if (compareAddOn(cartItem, userCartItem)) {
                        double newPrice = cartItem.getPrice() / cartItem.getQuantity();
                        int updatedQuantity = cartItem.getQuantity() - 1;
                        if (updatedQuantity == 0) {
                            cartItemRepository.deleteById(cartItem.getCartItemId());
                            return "item removed as it is the only one present in cart";
                        }
                        cartItem.setQuantity(updatedQuantity);
                        cartItem.setPrice(cartItem.getQuantity() * newPrice);
                        cartItem.setUpdateItemInCartTime(LocalDateTime.now());
                        cartItemRepository.save(cartItem);
                        return "Item Decremented";
                    }
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "No Item Found to be Deleted";
    }

    public String subtractCartItem(Long cartItemId) {
        try {
            User userDetail = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Customer customer = userDetail.getCustomer();
            Cart cart = customer.getCart();
            for (CartItem cartItem : cart.getCartItems()) {
                System.out.println(cartItemId);
                System.out.println("cartItem ID:" + cartItem.getCartItemId());
                System.out.println("cartItem Id Input:"+ cartItem.getCartItemId().equals(cartItemId));
                if (cartItem.getCartItemId().equals(cartItemId)) {
                    double newPrice = cartItem.getPrice() / cartItem.getQuantity();
                    int updatedQuantity = cartItem.getQuantity() - 1;
                    if (updatedQuantity == 0) {
                        cartItemRepository.deleteById(cartItemId);
                        return "item removed as it is the only one present in cart";
                    }
                    cartItem.setQuantity(updatedQuantity);
                    cartItem.setPrice(updatedQuantity * newPrice);
                    cartItem.setUpdateItemInCartTime(LocalDateTime.now());
                    cartItemRepository.save(cartItem);
                    return "Item Decremented";
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "No Item Found to be Deleted";
    }

    public String addItemFromCart(Long cartItemId) {
        try {
            User userDetail = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Customer customer = userDetail.getCustomer();
            Cart cart = customer.getCart();
            for (CartItem cartItem : cart.getCartItems()) {
                System.out.println("cartItem ID:" + cartItem.getCartItemId());
                System.out.println("cartItem Id Input:"+ cartItem.getCartItemId().equals(cartItemId));
                System.out.println(cartItem.getCartItemId().equals(cartItemId));
                if (cartItem.getCartItemId().equals(cartItemId)) {
                    double newPrice = cartItem.getPrice() / cartItem.getQuantity();
                    int updatedQuantity = cartItem.getQuantity() + 1;
                    cartItem.setQuantity(updatedQuantity);
                    cartItem.setPrice(updatedQuantity * newPrice);
                    cartItem.setUpdateItemInCartTime(LocalDateTime.now());
                    cartItemRepository.save(cartItem);
                    return "Item Incremented";
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "No Item Found to be incremented";
    }


}
