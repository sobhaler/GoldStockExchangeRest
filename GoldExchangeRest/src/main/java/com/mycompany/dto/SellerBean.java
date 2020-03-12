package com.mycompany.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class SellerBean {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "hh:mm:ss")
	private Date timestamp;
	private int rate;
	private int availableAmt;
	private String sellerName;

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public int getAvailableAmt() {
		return availableAmt;
	}

	public void setAvailableAmt(int availableAmt) {
		this.availableAmt = availableAmt;
	}
}
