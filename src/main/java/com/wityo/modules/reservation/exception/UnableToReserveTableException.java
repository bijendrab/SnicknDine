package com.wityo.modules.reservation.exception;

import com.wityo.common.exception.WityoGenericException;

public class UnableToReserveTableException extends WityoGenericException {

    /**
     *
     */
    private static final long serialVersionUID = 7596749842707950127L;

    public UnableToReserveTableException(String msg) {
        super(msg);
    }

}
