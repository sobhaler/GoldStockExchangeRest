package com.mycompany.dto;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class SellerBeanList {

	private List<SellerBean> sellerRequest = null;

	public List<SellerBean> getSellerRequest() {
		return sellerRequest;
	}
	public void setSellerRequest(List<SellerBean> sellerRequest) {
		this.sellerRequest = sellerRequest;
	}
}
