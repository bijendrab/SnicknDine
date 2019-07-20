package com.wityo.modules.product.exception;

import com.wityo.common.exception.WityoGenericException;

@SuppressWarnings("serial")
public class NoProductsFoundException extends WityoGenericException{

	public NoProductsFoundException(String msg) {
		super(msg);
	}
}
