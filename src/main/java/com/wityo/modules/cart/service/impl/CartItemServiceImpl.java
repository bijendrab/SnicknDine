package com.wityo.modules.cart.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.wityo.modules.cart.model.Cart;
import com.wityo.modules.cart.model.CartItem;
import com.wityo.modules.cart.model.SelectCartAddOnItems;
import com.wityo.modules.cart.model.UserCartItem;
import com.wityo.modules.cart.repository.CartItemRepository;
import com.wityo.modules.cart.service.CartItemService;
import com.wityo.modules.product.model.Product;
import com.wityo.modules.product.model.ProductQuantityOption;
import com.wityo.modules.user.model.Customer;
import com.wityo.modules.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    CartItemRepository cartItemRepository;


    public String addOrUpdateCart(UserCartItem userCartItem) {
        try {
            User userDetail = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Customer customer = userDetail.getCustomer();
            Cart cart = customer.getCart();

            List<CartItem> userCartItems = cart.getCartItems();
            String productId = userCartItem.getProduct().getProductId();
            CartItem tempCartItem = null;
            for (CartItem cartItem : userCartItems) {
                String productJson = cartItem.getProductJson();
                Product p = new Gson().fromJson(productJson, Product.class);
                if ((productId.equalsIgnoreCase(p.getProductId()) && cartItem.getQuantityOption().equals(userCartItem.getUserSelectQuantityOption()))) {
                    if (compareAddOn(cartItem, userCartItem)) {
                        double newPrice = cartItem.getPrice() / cartItem.getQuantity();
                        cartItem.setQuantity(cartItem.getQuantity() + 1);
                        cartItem.setPrice(cartItem.getQuantity() * newPrice);
                        cartItemRepository.save(cartItem);
                        return "updated";
                    }
                }
            }
            CartItem newCartItem = new CartItem();
            newCartItem.setCart(cart);
            newCartItem.setItemName(userCartItem.getProduct().getProductName());
            newCartItem.setQuantity(1);
            newCartItem.setProductJson(new Gson().toJson(userCartItem.getProduct()));
            userCartItem.getProduct().getProductQuantityOptions().forEach(qOption2 -> {
                if (qOption2.getQuantityOption().equalsIgnoreCase(userCartItem.getUserSelectQuantityOption())) {
                    newCartItem.setPrice(qOption2.getPrice() * 1);
                    newCartItem.setQuantityOption(userCartItem.getUserSelectQuantityOption());
                }
            });
            newCartItem.setSelectCartAddOnItems(convertListToSet(userCartItem.getSelectCartAddOnItems()));
            for (SelectCartAddOnItems selectCartAddOnItems : newCartItem.getSelectCartAddOnItems()) {
                selectCartAddOnItems.setCartItem(newCartItem);
            }
            cartItemRepository.save(newCartItem);
            return "added";

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

    public static <T> Set<T> convertListToSet(List<T> list)
    {
        // create a set from the List
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
        }
        return "failure";
    }

    public String reduceCartItem(String productId, String quantityOption) {
        User userDetail = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Customer customer = userDetail.getCustomer();
        Cart cart = customer.getCart();
        for (CartItem cartItem : cart.getCartItems()) {
            String productJson = cartItem.getProductJson();
            Product product = new Gson().fromJson(productJson, Product.class);
            if (productId.equals(product.getProductId())) {
                int updatedQuantity = cartItem.getQuantity() - 1;
                if (updatedQuantity == 0) {
                    cartItemRepository.deleteById(cartItem.getCartItemId());
                    return "cart updated";
                }
                cartItem.setQuantity(updatedQuantity);
                for (ProductQuantityOption qOption : product.getProductQuantityOptions()) {
                    if (cartItem.getQuantityOption().equalsIgnoreCase(quantityOption)) {
                        cartItem.setPrice(updatedQuantity * qOption.getPrice());
                        cartItemRepository.save(cartItem);
                        return "cart updated";
                    }
                }

            }
        }
        return "operation failed";
    }
}
