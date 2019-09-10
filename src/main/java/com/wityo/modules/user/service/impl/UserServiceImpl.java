package com.wityo.modules.user.service.impl;

import com.wityo.modules.cart.model.Cart;
import com.wityo.modules.user.model.Customer;
import com.wityo.modules.user.model.User;
import com.wityo.modules.user.repository.UserRepository;
import com.wityo.modules.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> fetchAllUsers() {
        return null;
    }

    public User validateUser(String phoneNumber) {
        try {
            User user1 = userRepository.findByPhoneNumber(phoneNumber);
            if (null != user1) {
                return user1;
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            throw new RuntimeException("User not found in the records, needs to be registered!");
        }
    }

    public User registerUser(User user) {
        try {
            Customer customer = new Customer();
            customer.setCustomerId(user.getUserId());
            customer.setEmailId(user.getEmailId());
            customer.setFirstName(user.getFirstName());
            customer.setLastName(user.getLastName());
            customer.setPhoneNumber(user.getPhoneNumber());
            customer.setUser(user);
            Cart cart = new Cart();
            customer.setCart(cart);
            cart.setCustomer(customer);
            user.setCustomer(customer);
			
			/*CartItem item = new CartItem();
			item.setCart(cart);
			item.setItemName("lala");
			List<CartItem> items = new ArrayList<CartItem>();
			items.add(item);
			cart.setCartItems(items);*/

            User user1 = userRepository.save(user);
            if (null != user1) {
                return user1;
            } else {
                throw new RuntimeException();
            }
        } catch (Exception e) {
            throw new RuntimeException("Unable to register user, please try again");
        }
    }

    @Override
    public String removeUser(String username) {
        return null;
    }

    @Override
    public User getUserByUsername(String username) {
        return null;
    }

}
