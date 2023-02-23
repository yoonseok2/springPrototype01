package com.gryard.basecamp.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 단일건 api 결과 처리
 * */
@Getter
@Setter
public class SingleResult<T> extends CommonResult {
	private T data;
}
