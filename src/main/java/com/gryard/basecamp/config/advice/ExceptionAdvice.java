package com.gryard.basecamp.config.advice;

import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.gryard.basecamp.dto.CommonResult;
import com.gryard.basecamp.exception.LoginCheckException;
import com.gryard.basecamp.service.ResponseService;

import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;

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
	
	// 아예 잘못된 형식으로 request 를 요청할 경우 예외 발생
	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult handleHttpMessageNotReadableException(HttpServletRequest request, Exception e) {
		 e.printStackTrace();
        // 예외 처리의 메시지를 MessageSource에서 가져오도록 수정
        Integer code = -1001;
        String 	msg	 = "Required request body is missing.";
                
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
	
	// 로그인 정보 불일치
	@ExceptionHandler(LoginCheckException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public CommonResult handleLoginCheckException(LoginCheckException e) {
		
		e.printStackTrace();
		
        // 예외 처리의 메시지를 MessageSource에서 가져오도록 수정
        Integer code = -2001;
        String 	msg	 = "아이디, 비밀번호를 다시 확인해주세요.";
                
        return responseService.getFailResult(code, msg);
	}
	
	// Content Type 오류
	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult handleHttpMediaTypeNotSupportedException(HttpServletRequest request, Exception e) {
		 e.printStackTrace();
		 
        // 예외 처리의 메시지를 MessageSource에서 가져오도록 수정
        Integer code = -1002;
        String 	msg	 = "Unsupported Media Type.";
                
        return responseService.getFailResult(code, msg);
    }
	
	// 유효성검사 에러
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public CommonResult handleMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException e) {
		 e.printStackTrace();
		 
	        // 예외 처리의 메시지를 MessageSource에서 가져오도록 수정
	        Integer code = -1003;
	        Map<String,String> msg = new HashMap<>();
	        
	        e.getBindingResult().getAllErrors().forEach(c->msg.put(((FieldError) c).getField(), c.getDefaultMessage()));
	                
	        return responseService.getFailResult(code, msg);
	}
	
}
