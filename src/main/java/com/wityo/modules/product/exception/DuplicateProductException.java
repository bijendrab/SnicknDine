package com.wityo.modules.product.exception;

import com.wityo.common.exception.WityoGenericException;

public class DuplicateProductException extends WityoGenericException {

	private static final long serialVersionUID = -813164345884088589L;

	public DuplicateProductException(String msg) {
		super(msg);
	}

}
