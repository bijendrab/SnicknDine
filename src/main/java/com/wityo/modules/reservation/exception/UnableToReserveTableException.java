package com.wityo.modules.reservation.exception;

import com.wityo.common.exception.WityoGenericException;

public class UnableToReserveTableException extends WityoGenericException{

	public UnableToReserveTableException(String msg) {
		super(msg);
	}

}
