package com.wallet.app.dto;

public class WalletDto { //POJO

	private Integer idOne;
	private Integer idTwo;
	private Double amount;
	public WalletDto() {
		super();
	}
	public WalletDto(Integer idOne, Integer idTwo, Double amount) {
		super();
		this.idOne = idOne;
		this.idTwo = idTwo;
		this.amount = amount;
	}
	public Integer getIdOne() {
		return idOne;
	}
	public void setIdOne(Integer idOne) {
		this.idOne = idOne;
	}
	public Integer getIdTwo() {
		return idTwo;
	}
	public void setIdTwo(Integer idTwo) {
		this.idTwo = idTwo;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	
	
}
