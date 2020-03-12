package com.mycompany.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class BuyerBean {
	private String buyerName;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "hh:mm")
	private Date timestamp;
	private int rate;
	private int requiredAmt;

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
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

	public int getRequiredAmt() {
		return requiredAmt;
	}

	public void setRequiredAmt(int requiredAmt) {
		this.requiredAmt = requiredAmt;
	}
}
