package com.controller;

import com.dataTransferObjects.CheckRequestDTO;
import com.dataTransferObjects.ImmediateRequestDTO;
import com.dataTransferObjects.OrderRequestDTO;
import com.model.*;
import com.service.CustomerService;
import com.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import com.service.CartService;
import com.service.CustomerOrderService;

import java.util.*;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
public class OrderController {

	@Autowired
	private CartService cartService;
	@Autowired
	private CustomerService customerService;

	@Autowired
	private CustomerOrderService customerOrderService;

	@Autowired
	private OrderService orderRepo;

	@RequestMapping(value="/cart/checkout/{cartId}",method = RequestMethod.POST)
	public String createOrder(@PathVariable("cartId") int cartId, Model model) {

		/*CustomerOrder customerOrder = new CustomerOrder();

		Cart cart = cartService.getCartByCartId(cartId);
		// Update CartId for customerOrder - set CartId
		customerOrder.setCart(cart);

		Customer customer = cart.getCustomer();

		customerOrder.setCustomer(customer);*/
		// Set customerid
		// Set ShippingAddressId
		//customerOrder.setShippingAddress(customer.getShippingAddress());

		//customerOrder.setBillingAddress(customer.getBillingAddress());

		//customerOrderService.addCustomerOrder(customerOrder);

		Cart cart =cartService.validate(cartId);
		List<Map<String, Object>> orderItems= cartService.update(cart);
		model.addAttribute("OrderList", orderItems);
		return "jsonTemplate";
		//return "redirect:/checkout?cartId=" + cartId;
	}
	@RequestMapping(value="/cart/checkout/",method = RequestMethod.POST)
	public String RequestOrder(@RequestBody ImmediateRequestDTO immediateRequestDTO, Model model) throws Exception {
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String emailId = user.getUsername();
		Customer customer = customerService.getCustomerByEmailId(emailId);
		Order createdOrder = orderRepo.processOrderRequest(customer.getCart().getCartId(),customer.getCustomerId(),immediateRequestDTO);
		model.addAttribute("OrderList", createdOrder);
		return "jsonTemplate";
		//return "redirect:/checkout?cartId=" + cartId;
	}
	@RequestMapping(value = {"/orderList" })
	public String getCustomerOrder(Model model) {
		List<Map<Integer,List<OrderItem>>> customerOrd = customerOrderService.getCustomerOrder();
		List<Map<Integer,List<HashMap<String, Object>>>> orderedItems=new ArrayList<>();
		for (Map<Integer, List<OrderItem>> map : customerOrd) {
			Map<Integer,List<HashMap<String, Object>>>oiListMap=new HashMap<>();
			for (Map.Entry<Integer,  List<OrderItem>> entry : map.entrySet()) {
				List<HashMap<String, Object>> oIList=new ArrayList<>();
				for(OrderItem oI:entry.getValue()){
					HashMap<String, Object> opt = new HashMap<>();
					opt.put("orderItemId",oI.getOrderItemId());
					opt.put("orderCreationTime",oI.getOrderCreationTime());
                    opt.put("quantity",oI.getQuantity());
                    opt.put("price",oI.getPrice());
                    opt.put("status",oI.getStatus());
                    opt.put("itemName",oI.getItemName());
                    opt.put("waitTime",oI.getWaitTime());
                    opt.put("quantityOption",oI.getQuantityOption());
					List<HashMap<String, Object>> quantityOptions=new ArrayList<>();
					for (ProductQuantityOptions quantO:oI.getProduct().getQuantityOption()){
						HashMap<String, Object> optcart = new HashMap<>();
						optcart.put("option",quantO.getOption());
						optcart.put("price",quantO.getPrice());
						optcart.put("quantity",quantO.getQuantity());
						quantityOptions.add(optcart);
					}
					opt.put("quantityOptions",quantityOptions);
					oIList.add(opt);
				}
				oiListMap.put(entry.getKey(),oIList);
			}
			orderedItems.add(oiListMap);
		}
		model.addAttribute("orderList", orderedItems);
		return "jsonTemplate";
	}

	@RequestMapping(value = "/editOrderItem",method = RequestMethod.PUT,consumes="application/json")
	public String updateCustomerOrder(@RequestBody OrderItem orderitem, Model model) {
		/*if (result.hasErrors())
			return "CustomerOrderPage";*/
		//orderitem.setStatus("Processed");
		customerOrderService.updateCustomerOrder(orderitem);
		model.addAttribute("updateOrderList", orderitem);
		return "jsonTemplate";
	}

    @RequestMapping(value = "/deleteOrderItem",method = RequestMethod.DELETE,consumes="application/json")
    public String deleteCustomerOrder(@RequestBody OrderItem orderitem, Model model) {
		/*if (result.hasErrors())
			return "CustomerOrderPage";*/
        //orderitem.setStatus("Processed");
        customerOrderService.deleteCustomerOrderItem(orderitem);
        model.addAttribute("deleted", "item deleted");
        return "jsonTemplate";
    }

}
