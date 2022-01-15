package com.consyn.app.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.consyn.app.model.ErrorDTO;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ErrorDTO> generateNotFoundException(NotFoundException ex) {
		ErrorDTO errorDTO = new ErrorDTO();
		errorDTO.setMessage(ex.getMessage());
		errorDTO.setStatus(String.valueOf(ex.getStatus().value()));
		errorDTO.setTime(new Date().toString());

		return new ResponseEntity<ErrorDTO>(errorDTO, ex.getStatus());
	}
}