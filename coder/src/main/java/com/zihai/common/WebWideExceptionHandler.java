package com.zihai.common;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice
public class WebWideExceptionHandler{

	@ExceptionHandler(MaxUploadSizeExceededException.class)
	@ResponseBody
	public String deelMaxUpload(MaxUploadSizeExceededException ex){
		return "上传文件限制为"+ex.getMaxUploadSize()/1024/1024+"M";
	}
}
