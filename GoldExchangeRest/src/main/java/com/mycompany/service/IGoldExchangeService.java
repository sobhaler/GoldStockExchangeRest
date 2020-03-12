
package com.mycompany.service;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.mycompany.dto.BuyerBean;
import com.mycompany.dto.SellerBean;
import com.mycompany.dto.SellerBeanList;

@Service
public interface IGoldExchangeService {

	boolean validateTime();
	List<SellerBean> saveSeller(SellerBeanList sellers);
	public boolean validateRate(int ratevalue);
	public List<SellerBean> getCurrentCond();
	public ResponseEntity<Object> summaryCal(BuyerBean buyer);
}
