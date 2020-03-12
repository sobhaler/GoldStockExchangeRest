package com.mycompany.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.dto.BuyerBean;
import com.mycompany.dto.SellerBean;
import com.mycompany.dto.SellerBeanList;
import com.mycompany.service.GoldExchangeServiceImpl;

/**
 * This class acts as Controller
 *
 */
@RestController
public class GoldExchangecontroller {

	@Autowired
	GoldExchangeServiceImpl GoldExchangeServiceImpl;

	@Autowired
	SellerBeanList SellerBean;

	/**
	 * @param buyer
	 * @return ResponseEntityObject
	 */
	@POST
	@RequestMapping("/placeOrder")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	public ResponseEntity<Object> placeOrder(@RequestBody BuyerBean buyer) {

		if (GoldExchangeServiceImpl.validateTime()) {
			if (GoldExchangeServiceImpl.validateRate(buyer.getRate())) {
				GoldExchangeServiceImpl.summaryCal(buyer);
			} else {
				List<SellerBean> currentSellerState = GoldExchangeServiceImpl.getCurrentCond();
				Map<String, Object> currentState = new LinkedHashMap<>();
				currentState.put("BuyerInfo", buyer);
				currentState.put("SellerInfo", currentSellerState);
				currentState.put("Message", "No seller is available to sell at the rate" + " " + buyer.getRate());
				return new ResponseEntity<>(currentState, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>("start logging into the exchange program from 9:00 AM (EST) onwards.",
				HttpStatus.OK);
	}

	/**
	 * @param sellers
	 * @return ResponseEntityObject
	 */
	@POST
	@RequestMapping("/createSeller")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	public ResponseEntity<Object> createBean(@RequestBody SellerBeanList sellers) {
		List<SellerBean> bean = GoldExchangeServiceImpl.saveSeller(sellers);
		return new ResponseEntity<>(bean, HttpStatus.CREATED);
	}
}
