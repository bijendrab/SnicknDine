package com.wityo.modules.product.exception;

import com.wityo.common.exception.WityoGenericException;

public class DuplicateProductException extends WityoGenericException {

	public DuplicateProductException(String msg) {
		super(msg);
	}

}
