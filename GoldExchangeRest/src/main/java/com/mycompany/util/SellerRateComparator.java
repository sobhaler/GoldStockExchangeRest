package com.mycompany.util;

import java.util.Comparator;
import com.mycompany.dto.SellerBean;

public class SellerRateComparator implements Comparator<SellerBean> {

	@Override
	public int compare(SellerBean seller1, SellerBean Seller2) {
		return seller1.getRate() - Seller2.getRate();
	}
}
