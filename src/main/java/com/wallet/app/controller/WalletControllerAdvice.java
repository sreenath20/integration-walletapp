package com.wallet.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.wallet.app.exception.WalletException;

@RestControllerAdvice
public class WalletControllerAdvice {
	
	@ExceptionHandler({WalletException.class})
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)	
	public String walletExceptionHandler(WalletException e) {
		return e.getMessage();
	}

}
