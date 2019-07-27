package com.wityo.modules.restaurant.Exception;

public class NoRestaurantFoundException extends RuntimeException{
	public NoRestaurantFoundException(String msg) {
		super(msg);
	}
}
