package com.jdmv.springboot.backend.apirest.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartException;

@RestControllerAdvice
public class ErrorHandlerController {
	
	
	@ExceptionHandler(MultipartException.class)
	@ResponseStatus(value = HttpStatus.PAYLOAD_TOO_LARGE, reason="file too large")
	public MultipartException handleMultipartException(MultipartException ex, WebRequest request) {

        return ex;

	}

}
