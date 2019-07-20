package com.wityo.modules.product.exception;

import com.wityo.common.exception.WityoGenericException;

public class UnableToDeleteProductException extends WityoGenericException{

	public UnableToDeleteProductException(String msg) {
		super(msg);
	}
	
}
