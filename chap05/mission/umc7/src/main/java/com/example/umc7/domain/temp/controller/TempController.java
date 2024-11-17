package com.example.umc7.domain.temp.controller;

import com.example.umc7.apipayload.ApiResponse;
import com.example.umc7.domain.temp.converter.TempConverter;
import com.example.umc7.domain.temp.dto.TempResponse;
import com.example.umc7.domain.temp.dto.TempResponse.TempTestDTO;
import com.example.umc7.domain.temp.service.TempQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/temp")
@RequiredArgsConstructor
public class TempController {

    private final TempQueryService tempQueryService;

    @GetMapping("/test")
    public ApiResponse<TempTestDTO> testAPI(){

        return ApiResponse.onSuccess(TempConverter.toTempTestDTO());
    }

    @GetMapping("/exception")
    public ApiResponse<TempResponse.TempExceptionDTO> exceptionAPI(@RequestParam Integer flag){
        tempQueryService.CheckFlag(flag);
        return ApiResponse.onSuccess(TempConverter.toTempExceptionDTO(flag));
    }
}
