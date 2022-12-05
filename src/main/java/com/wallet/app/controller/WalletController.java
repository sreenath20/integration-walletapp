package com.wallet.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.wallet.app.dto.Wallet;
import com.wallet.app.dto.WalletDto;
import com.wallet.app.exception.WalletException;
import com.wallet.app.service.WalletService;
import com.wallet.app.service.WalletServiceImpl;


@RestController
public class WalletController {

	private WalletService walletService = new WalletServiceImpl();
	
	@GetMapping("/")
	public String Greet() {
		return "Welcome to Wallet App.";
	}
	
	@PostMapping("wallet")
	public Wallet addWallet(@RequestBody Wallet wallet) throws WalletException {
		return this.walletService.registerWallet(wallet);
	}
	
	@GetMapping("wallet/{id}")
	public Double getWalletBalanceById(@PathVariable("id") Integer id) throws WalletException {
		return this.walletService.showWalletBalanceById(id);
		
	}
	@PatchMapping("wallet")
	public Double addFundsToWallet(@RequestBody WalletDto walletDto) throws WalletException {
		return this.walletService.addFundsToWallet(walletDto.getIdOne(),walletDto.getAmount());
		
	}
	
	@PostMapping("wallet/fund")
	public Boolean fundTransfer(@RequestBody WalletDto walletDto) throws WalletException {
		return this.walletService.fundTransfer(walletDto.getIdOne(),walletDto.getIdTwo(),walletDto.getAmount());
		
	}
	
	
}
