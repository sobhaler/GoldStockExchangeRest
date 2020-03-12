
package com.mycompany.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import com.mycompany.config.ApplnConstant;
import com.mycompany.config.OrderSQL;
import com.mycompany.dto.SellerBean;
import com.mycompany.exceptionHandler.DBException;

@Repository
public class OrderDaoImpl {

	private static final Logger logger = LoggerFactory.getLogger(OrderDaoImpl.class);

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	SellerBean bean = null;
	List<SellerBean> sellerBeans = null;

	public void saveBean(SellerBean bean) {

		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("sellerName", bean.getSellerName());
			paramMap.put("timestamp", bean.getTimestamp());
			paramMap.put("rate", bean.getRate());
			paramMap.put("availableAmt", bean.getAvailableAmt());
			int size = namedParameterJdbcTemplate.update(OrderSQL.INSERT_QUERY, paramMap);
			System.out.println("exceuted" + size);
		} catch (DataAccessException e) {
			String msg = "JDBC Exception Occured ";
			logger.info(msg + e.getStackTrace());
			throw new DBException(ApplnConstant.DBException_MSG);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DBException(ApplnConstant.DBException_MSG);
		}
	}

	public boolean validateAmt(Integer rate) {
		List<Integer> amt = null;
		try {
			amt = namedParameterJdbcTemplate.query(OrderSQL.VALIDATEAmt_QUERY, new RowMapper<Integer>() {
				@Override
				public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
					return toSellerAmt(resultSet);
				}
			});
			return (Collections.min((amt.stream().map(Long::valueOf).collect(Collectors.toList())))) <= rate;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DBException(ApplnConstant.DBException_MSG);
		}
	}

	protected int toSellerAmt(ResultSet resultSet) throws SQLException {

		bean = new SellerBean();
		bean.setAvailableAmt((resultSet.getInt("availableAmt")));
		return bean.getAvailableAmt();

	}

	public List<SellerBean> selectAll() {
		try {
			sellerBeans = new ArrayList<>();
			sellerBeans = namedParameterJdbcTemplate.query(OrderSQL.SELECTSeller_QUERY, new RowMapper<SellerBean>() {
				@Override
				public SellerBean mapRow(ResultSet resultSet, int i) throws SQLException {
					return toSeller(resultSet);
				}
			});
			return sellerBeans;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DBException(ApplnConstant.DBException_MSG);
		}
	}

	protected SellerBean toSeller(ResultSet resultSet) throws SQLException {
		bean = new SellerBean();
		bean.setSellerName(resultSet.getString("sellerName"));
		bean.setTimestamp(resultSet.getDate("timestamp"));
		bean.setRate(resultSet.getInt("rate"));
		bean.setAvailableAmt(resultSet.getInt("availableAmt"));
		return bean;
	}
}
