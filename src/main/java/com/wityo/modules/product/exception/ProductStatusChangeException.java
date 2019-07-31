package com.wityo.modules.product.exception;

import com.wityo.common.exception.WityoGenericException;

public class ProductStatusChangeException extends WityoGenericException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7727962584490033439L;

	public ProductStatusChangeException(String msg) {
		super(msg);
	}
	
}
