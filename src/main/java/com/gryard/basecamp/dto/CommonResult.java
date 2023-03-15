package com.gryard.basecamp.dto;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


/**
 * API 공통응답 모델
 * */
@Getter
@Setter
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResult {
	
	@ApiModelProperty(value = "응답 성공여부 : true/false")
    private boolean success;

    @ApiModelProperty(value = "응답 코드 번호 : >= 0 정상, < 0 비정상")
    private int code;

    @ApiModelProperty(value = "응답 메시지")
    private String msg;

    @ApiModelProperty(value = "현재 날짜")
    private String today;
    
 
    @ApiModelProperty(value="응답 메시지(map)")
    private Map<String,String> msgMap;
}
