package com.wityo.modules.product.exception;

import com.wityo.common.exception.WityoGenericException;

@SuppressWarnings("serial")
public class ProductNotFoundException extends WityoGenericException {

	public ProductNotFoundException(String msg) {
		super(msg);
	}

}
