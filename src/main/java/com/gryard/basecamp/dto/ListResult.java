package com.gryard.basecamp.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 여러건  api 결과처리
 * */
@Getter
@Setter
public class ListResult<T> extends CommonResult {
	private List<T> list;
}
