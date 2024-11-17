package com.example.umc7.apipayload.exception.handler;

import com.example.umc7.apipayload.code.BaseErrorCode;
import com.example.umc7.apipayload.exception.GeneralException;

public class TempHandler extends GeneralException {

    public TempHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}