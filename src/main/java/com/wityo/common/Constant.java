package com.wityo.common;

public class Constant {
	public static final String API_SECRET = "APISecretKey";
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_TOKEN_KEY = "Authorization";
	public static final Long EXPIRY_TIME = 300_0000L;
	
	
	/*-----------------------------Constants for API MAPPING--------------------------------*/
	public static final String PRODUCT_API = "/api/product";
	public static final String CART_API = "/api/cart";
	public static final String ORDER_API = "/api/order";
	public static final String ORDER_ITEM_API = "/api/orderitem";
	public static final String RESTAURANT_SERVER_API = "/api/restaurant";
	public static final String BIND_API = "/api/bind";
	/*-----------------------------Constants for API MAPPING--------------------------------*/
	
	/*-----------------------------Constants for server address--------------------------------*/
	//public static final String RESTAURANT_SERVER_URL = "http://localhost:8081/";
	/*-----------------------------Constants for OTP 2Factor--------------------------------*/

	public static final String TWO_FACTOR_API_SECRET = "5ce82e95-9bd9-11e9-ade6-0200cd936042";
	public static final String TWO_FACTOR_API_URL = "https://2factor.in/API/V1/";
}
