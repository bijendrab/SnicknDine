package com.wityo.modules.restaurant.Exception;

public class NoRestaurantFoundException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoRestaurantFoundException(String msg) {
		super(msg);
	}
}
