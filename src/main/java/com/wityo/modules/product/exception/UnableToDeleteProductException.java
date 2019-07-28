package com.wityo.modules.product.exception;

import com.wityo.common.exception.WityoGenericException;

public class UnableToDeleteProductException extends WityoGenericException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7579831055127192464L;

	public UnableToDeleteProductException(String msg) {
		super(msg);
	}
	
}
