package com.example.umc7.domain.temp.converter;


import com.example.umc7.domain.temp.dto.TempResponse;

public class TempConverter {

    public static TempResponse.TempTestDTO toTempTestDTO(){
        return TempResponse.TempTestDTO.builder()
            .testString("This is Test!")
            .build();
    }

    public static TempResponse.TempExceptionDTO toTempExceptionDTO(Integer flag){
        return TempResponse.TempExceptionDTO.builder()
            .flag(flag)
            .build();
    }
}