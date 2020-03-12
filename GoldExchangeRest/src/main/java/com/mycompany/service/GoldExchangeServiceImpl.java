package com.mycompany.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mycompany.config.ApplnConstant;
import com.mycompany.dto.BuyerBean;
import com.mycompany.dto.SellerBean;
import com.mycompany.dto.SellerBeanList;
import com.mycompany.exceptionHandler.BuyerNotFoundException;
import com.mycompany.repository.OrderDaoImpl;
import com.mycompany.util.SellerRateComparator;

@Service
public class GoldExchangeServiceImpl implements IGoldExchangeService  {

	static SimpleDateFormat etDf = new SimpleDateFormat("hh:mma");
	static TimeZone etTimeZone = TimeZone.getTimeZone("America/New_York");
	List<SellerBean> sellerBean = null;
	List<SellerBean> resultantList = null;
	static {
		etDf.setTimeZone(etTimeZone);
	}
	@Autowired
	OrderDaoImpl OrderDao;

	/**
	 * Description : This method validates time, restrict user to login after 9am EST
	 * @return boolean value
	 */
	public boolean validateTime() {
		try {
			Calendar currentTime = Calendar.getInstance(etTimeZone);
			System.out.println(etDf.format(currentTime.getTimeInMillis()));
			String am_pm = etDf.format(currentTime.getTimeInMillis()).substring(5);// PM
			if (((currentTime.get(Calendar.HOUR) >= 9)) && (am_pm.equals("AM"))
					&& (currentTime.get(Calendar.HOUR) <= 12)) {
							return true;
			} else if (((currentTime.get(Calendar.HOUR) >= 1) | (currentTime.get(Calendar.HOUR) == 0))
					&& (am_pm.equals("PM")) && (currentTime.get(Calendar.HOUR) <= 12)) {
					return true;
			} else
			return false;
		} catch (Exception e) {
			String msg = "Exception Occured";
			System.out.println(msg +e.getMessage());
		}
		return false;
	}

	public List<SellerBean> saveSeller(SellerBeanList sellers) {
		
		sellerBean = new ArrayList<>();
		ListIterator<SellerBean> iterator = sellers.getSellerRequest().listIterator();

		while (iterator.hasNext()) {
			SellerBean bean = iterator.next();
			OrderDao.saveBean(bean);
			sellerBean.add(bean);
		}
		return sellerBean;
	}

	public boolean validateRate(int ratevalue) {
		return OrderDao.validateAmt(Integer.valueOf(ratevalue));
	}

	public List<SellerBean> getCurrentCond() {
		return OrderDao.selectAll();
	}

	public ResponseEntity<Object> summaryCal(BuyerBean buyer) {
		if (buyer != null) {
			List<SellerBean> bean = OrderDao.selectAll();
			Collections.sort(bean, new SellerRateComparator());

			int netRate = 0;

			for (SellerBean beanAscOrder : bean) {

				if (buyer.getRate() == beanAscOrder.getRate()) {
					bean.remove(beanAscOrder);
					return new ResponseEntity<>(bean, HttpStatus.OK);
				}
				if (buyer.getRate() > beanAscOrder.getRate()) {
					resultantList = new ArrayList<>();
					netRate = (buyer.getRate() - beanAscOrder.getRate());
					resultantList.add(beanAscOrder);
					bean.remove(beanAscOrder);
					recuriveCal(netRate, bean);
				}
				if (buyer.getRate() < beanAscOrder.getRate()) {
					resultantList = new ArrayList<>();
					netRate = (beanAscOrder.getRate() - netRate);
					beanAscOrder.setRate(netRate);
					resultantList.add(beanAscOrder);
					return new ResponseEntity<>(resultantList, HttpStatus.OK);
				}
			}
		}
		throw new BuyerNotFoundException(ApplnConstant.Buyer_Null_MSG);
	}

	private ResponseEntity<Object> recuriveCal(int netRate, List<SellerBean> bean) {
		for (SellerBean beanAscOrder : bean) {

			if (netRate == beanAscOrder.getRate()) {
				bean.remove(beanAscOrder);
				return new ResponseEntity<>(bean, HttpStatus.OK);
			}
			if (netRate < beanAscOrder.getRate()) {

				resultantList = new ArrayList<>();
				netRate = (beanAscOrder.getRate() - netRate);
				beanAscOrder.setRate(netRate);
				resultantList.add(beanAscOrder);
				return new ResponseEntity<>(resultantList, HttpStatus.OK);
			}
			if (netRate > beanAscOrder.getRate()) {

				resultantList = new ArrayList<>();
				netRate = (netRate - beanAscOrder.getRate());
				resultantList.add(beanAscOrder);
				bean.remove(beanAscOrder);
				recuriveCal(netRate, bean);
				return new ResponseEntity<>(resultantList, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(bean, HttpStatus.OK);
	}

}
