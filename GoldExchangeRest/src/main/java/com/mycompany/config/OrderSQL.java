package com.mycompany.config;

public class OrderSQL {
	
	public static final String INSERT_QUERY = "insert into seller (sellerName, timestamp, rate,availableAmt) "
			+ "  values (:sellerName, :timestamp, :rate,:availableAmt)";
	
	public static final String VALIDATEAmt_QUERY  = "SELECT availableAmt FROM seller";
	
	public static final String SELECTSeller_QUERY  = "SELECT * FROM seller";
	
	

}
