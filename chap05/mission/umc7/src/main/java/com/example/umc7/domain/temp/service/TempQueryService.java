package com.example.umc7.domain.temp.service;

import com.example.umc7.apipayload.code.status.ErrorStatus;
import com.example.umc7.apipayload.exception.handler.TempHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TempQueryService {

    public void CheckFlag(Integer flag) {
        if (flag == 1)
            throw new TempHandler(ErrorStatus.TEMP_EXCEPTION);
    }
}