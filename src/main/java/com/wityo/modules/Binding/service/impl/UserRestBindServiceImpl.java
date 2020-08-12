package com.wityo.modules.Binding.service.impl;

import java.util.List;
import com.wityo.modules.Binding.dto.UserRestBindInput;
import com.wityo.modules.Binding.dto.UserRestBindOutput;
import com.wityo.modules.Binding.model.UserRestaurantBind;
import com.wityo.modules.Binding.repository.UserRestBindRepository;
import com.wityo.modules.Binding.service.UserRestBindService;
import com.wityo.modules.user.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserRestBindServiceImpl implements UserRestBindService {
    @Autowired
    private UserRestBindRepository userRestBindRepository;

    private Logger logger = LoggerFactory.getLogger(UserRestBindServiceImpl.class);

    public UserRestBindOutput getUserBindRestaurant(){
        try{
            User userDetail = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            List<UserRestaurantBind> userRestaurantBindList = userRestBindRepository.findAllByUserId(userDetail.getUserId());
            for(UserRestaurantBind userRestaurantBind: userRestaurantBindList){
                if(userRestaurantBind.getActive()==true && (userRestaurantBind.getCartStatus()==true || userRestaurantBind.getOrderStatus()==true)){
                    UserRestBindOutput userRestBindOutput = new UserRestBindOutput();
                    userRestBindOutput.setRestaurantId(userRestaurantBind.getRestaurantId());
                    return userRestBindOutput;
                }
            }
        }catch (Exception e) {
            logger.error("Exception in getting past orders- {}", e.getMessage());
        }
        return null;
    }

    public String bindUserToRestaurant(UserRestBindInput userRestBindInput){
        try {
            User userDetail = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            UserRestaurantBind userRestaurantBindUpdate = userRestBindRepository.findAllByUserIdAndRestaurantId(userDetail.getUserId(),userRestBindInput.getRestaurantId());
            List<UserRestaurantBind> userRestaurantBindList = userRestBindRepository.findAllByUserIdAndRestaurantIdNot(userDetail.getUserId(),userRestBindInput.getRestaurantId());
            if(userRestaurantBindUpdate !=null){
                if(userRestaurantBindUpdate.getActive()==true){
                    return "restaurant:"+ userRestBindInput.getRestaurantId() + " is active for this user already";
                }
                userRestaurantBindUpdate.setActive(Boolean.TRUE);
                userRestBindRepository.save(userRestaurantBindUpdate);
                for (UserRestaurantBind userRestaurantBindInactive: userRestaurantBindList){
                    userRestaurantBindInactive.setActive(false);
                    userRestBindRepository.save(userRestaurantBindInactive);
                    logger.info("restaurant:" + userRestBindInput.getRestaurantId() + " is not active now for the user" );
                }
                return "restaurant:"+ userRestBindInput.getRestaurantId() + " is active for this user now";
            }
            UserRestaurantBind userRestaurantBindCreate = new UserRestaurantBind();
            userRestaurantBindCreate.setUserId(userDetail.getUserId());
            userRestaurantBindCreate.setRestaurantId(userRestBindInput.getRestaurantId());
            userRestaurantBindCreate.setActive(Boolean.TRUE);
            for (UserRestaurantBind userRestaurantBindInactive: userRestaurantBindList){
                userRestaurantBindInactive.setActive(false);
                userRestBindRepository.save(userRestaurantBindInactive);
                logger.info("restaurant:" + userRestBindInput.getRestaurantId() + " is not active now for the user" );
            }
            userRestBindRepository.save(userRestaurantBindCreate);
            return "restaurant:"+ userRestBindInput.getRestaurantId() + " is active for this user now";
        }catch (Exception e) {
            return e.getMessage();
        }
    }

    public String bindUserToRestaurantCart(Long restaurantId) {
        try {
            User userDetail = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            UserRestaurantBind userRestaurantBindCurrent = userRestBindRepository.findAllByUserIdAndRestaurantId(userDetail.getUserId(), restaurantId);
            if (userRestaurantBindCurrent.getActive() == true && (userRestaurantBindCurrent.getCartStatus() == true && userRestaurantBindCurrent.getOrderStatus() == true)) {
                return "Go Ahead";
            }
            if (userRestaurantBindCurrent.getActive() == true && (userRestaurantBindCurrent.getCartStatus() == false && userRestaurantBindCurrent.getOrderStatus() == true)) {
                userRestaurantBindCurrent.setCartStatus(true);
                userRestBindRepository.save(userRestaurantBindCurrent);
                return "Start Adding Cart Items";
            }
            List<UserRestaurantBind> userRestaurantBindOther = userRestBindRepository.findAllByUserIdAndRestaurantIdNot(userDetail.getUserId(), restaurantId);
            Long getActiveCart = getCartActive(userRestaurantBindOther);
            Long getActiveOrder = getOrderActive(userRestaurantBindOther);
            if (userRestaurantBindCurrent.getActive() == true) {
                if (getActiveCart.equals(0L) && getActiveOrder.equals(0L)) {
                    userRestaurantBindCurrent.setCartStatus(true);
                    userRestBindRepository.save(userRestaurantBindCurrent);
                    return "Start Adding Cart Items";
                } else if (getActiveCart.equals(0L) && !getActiveOrder.equals(0L)) {
                    return "Order is going on progress in restaurant: " + getActiveOrder;
                } else if (!getActiveCart.equals(0L) && getActiveOrder.equals(0L)) {
                    return "Cart Items exist in another restaurant: " + getActiveCart;
                } else if (!getActiveCart.equals(0L) && !getActiveOrder.equals(0L)) {
                    return "Cart Items exist and order is going on progress restaurant: " + getActiveOrder;
                }
            }
            return "User has not selected restaurant";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String bindUserToRestaurantOrder(Long restaurantId) {
        try {
            User userDetail = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            UserRestaurantBind userRestaurantBindCurrent = userRestBindRepository.findAllByUserIdAndRestaurantId(userDetail.getUserId(), restaurantId);
            if (userRestaurantBindCurrent.getActive() == true) {
                userRestaurantBindCurrent.setOrderStatus(true);
                userRestBindRepository.save(userRestaurantBindCurrent);
                return "order status set as true";
            }
            return "User has not selected restaurant";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String unBindUserToRestaurantCart(Long restaurantId){
        try {
            User userDetail = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            UserRestaurantBind userRestaurantBindCurrent = userRestBindRepository.findAllByUserIdAndRestaurantId(userDetail.getUserId(), restaurantId);
            userRestaurantBindCurrent.setCartStatus(false);
            userRestBindRepository.save(userRestaurantBindCurrent);
            return "remove binding from user to cart";
        } catch(Exception e){
            return e.getMessage();
        }
    }

    public String unBindUserToRestaurantOrder(Long restaurantId){
            try {
                User userDetail = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                UserRestaurantBind userRestaurantBindCurrent = userRestBindRepository.findAllByUserIdAndRestaurantId(userDetail.getUserId(), restaurantId);
                userRestaurantBindCurrent.setOrderStatus(false);
                userRestBindRepository.save(userRestaurantBindCurrent);
                return "remove binding from user to order";
            } catch (Exception e) {
                return e.getMessage();
            }
        }


    private Long getCartActive(List<UserRestaurantBind> userRestaurantBindOther){
        for(UserRestaurantBind userRestaurantBind:userRestaurantBindOther){
            if(userRestaurantBind.getCartStatus()==true){
                return userRestaurantBind.getRestaurantId();
            }
        }
        return 0L;
    }

    private Long getOrderActive(List<UserRestaurantBind> userRestaurantBindOther){
        for(UserRestaurantBind userRestaurantBind:userRestaurantBindOther){
            if(userRestaurantBind.getOrderStatus()==true){
                return userRestaurantBind.getRestaurantId();
            }
        }
        return 0L;
    }
}
