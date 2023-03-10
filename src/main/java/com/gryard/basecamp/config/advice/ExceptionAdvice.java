package com.gryard.basecamp.config.advice;

import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.gryard.basecamp.dto.CommonResult;
import com.gryard.basecamp.service.ResponseService;

import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {

	
	private final ResponseService responseService;
	
	// 잘못된 method로 접근 시
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult defaultException(HttpServletRequest request, Exception e) {

        e.printStackTrace();
        // 예외 처리의 메시지를 MessageSource에서 가져오도록 수정
        Integer code = -9999;
        String 	msg	 = "An unknown error has occurred.";
                
        return responseService.getFailResult(code, msg);
    }
	
	// 존재하지 않는 URL로 접근 시 
	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
    public CommonResult handle404(NoHandlerFoundException exception) {
		
		Integer code = -1000;
        String msg = "존재하지 않는 URL입니다. : " +  exception.getRequestURL();
        return responseService.getFailResult(code, msg);
    }
	
}
