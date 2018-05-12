package org.intuit.customersales.service;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.PostConstruct;
import org.apache.spark.api.java.JavaRDD;
import org.intuit.customersales.config.SparkJoinRDD;
import org.intuit.customersales.entity.Customer;
import org.intuit.customersales.entity.Sale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import scala.Tuple2;

/**
 * Service class containing Business logic
 * 
 * @author lakshmimahabaleshwara
 */
@Service
public class CustomerSalesServiceImpl implements CustomerSalesService {

	private static Logger LOGGER = LoggerFactory.getLogger(CustomerSalesServiceImpl.class);

	@Autowired
	private SparkJoinRDD sparkJoinRDD;

	@Value("${customer.file.path:input/customers.txt}")
	private String CUSTOMER_FILE_PATH;

	@Value("${sales.file.path:input/sales.txt}")
	private String SALES_FILE_PATH;

	private final static String DELIMITER = "#";
	private final static String MONTH = "month";
	private final static String HOUR = "hour";
	private final static String DAY = "day";
	private final static String YEAR = "year";

	private static List<Tuple2<Integer, Tuple2<Sale, Customer>>> list;
	private static Set<Map.Entry<String, Iterable<Long>>> set;

	@PostConstruct
	public void initializeFile() throws Exception {
		try {
			JavaRDD<String> customerRdd = sparkJoinRDD.getCustomerRddFromTextFile(CUSTOMER_FILE_PATH);
			JavaRDD<String> salesRdd = sparkJoinRDD.getCustomerRddFromTextFile(SALES_FILE_PATH);
			list = sparkJoinRDD.getListOfJoinedPairedRDD(customerRdd, salesRdd);
			set = sparkJoinRDD.getSetOfJoinedPairedRDDValues(customerRdd, salesRdd);
		} catch (Exception e) {
			LOGGER.error("Error occured while joining the customer and sales RDD : " + e.getMessage());
			throw e;
		}
	}

	/**
	 * Returns the total sales for the state.
	 * 
	 * @returns List<String> in the format "AL#247316"
	 */
	@Override
	public List<String> getStateTotalSales() throws Exception {
		List<String> result = new ArrayList<>();
		try {

			if (set == null) {
				LOGGER.error("Set is empty while calculating total sales");
				return result;
			}

			for (Map.Entry<String, Iterable<Long>> sales : set) {
				long total = 0;
				Iterator<Long> itr = sales.getValue().iterator();
				while (itr.hasNext()) {
					total = total + itr.next();
				}
				StringBuilder builder = new StringBuilder();
				builder.append(sales.getKey()).append(DELIMITER).append(total);
				result.add(builder.toString());
			}
			LOGGER.info("Result size :" + result.size());
		} catch (Exception e) {
			LOGGER.error("Exception occured in while getting total sales for state: " + e);
			throw e;
		}
		return result;
	}

	/**
	 * Returns the Sales by hour for given state
	 * 
	 * @returns List<String> in the format "AL#2018#5#9#12#100"
	 */
	@Override
	public List<String> getSalesByHour() throws Exception {
		return getResultsFromRdd(HOUR);
	}

	/**
	 * Returns the Sales by month for given state
	 * 
	 * @returns List<String> in the format "AL#2018#5###100"
	 */
	@Override
	public List<String> getSalesByMonth() throws Exception {
		return getResultsFromRdd(MONTH);
	}

	/**
	 * Returns the Sales by day for given state
	 * 
	 * @returns List<String> in the format ""AL#2018#5#9##100""
	 */
	@Override
	public List<String> getSalesByDay() throws Exception {
		return getResultsFromRdd(DAY);
	}

	/**
	 * Returns the Sales by year for given state
	 * 
	 * @returns List<String> in the format ""AL#2018####100""
	 */
	@Override
	public List<String> getSalesByYear() throws Exception {
		return getResultsFromRdd(YEAR);
	}

	/**
	 * Returns the List of results looping through the Joined pair RDD.
	 */
	private List<String> getResultsFromRdd(String param) throws Exception {
		List<String> result = new ArrayList<>();
		try {
			if (list == null) {
				LOGGER.error("List is empty while getting sales by " + param + " for state");
				return result;
			}

			for (Tuple2<Integer, Tuple2<Sale, Customer>> tuple : list) {
				Tuple2<Sale, Customer> tuple2 = (Tuple2<Sale, Customer>) tuple._2();
				Sale sale = (Sale) tuple2._1();
				Customer customer = (Customer) tuple2._2();
				if (param.equalsIgnoreCase(MONTH)) {
					addYearMonth(result, customer.getState(), sale.getSalesPrice(), sale.getTimeStamp());
				} else if (param.equalsIgnoreCase(DAY)) {
					addYearMonthDay(result, customer.getState(), sale.getSalesPrice(), sale.getTimeStamp());
				} else if (param.equalsIgnoreCase(HOUR)) {
					addYearMonthDayHour(result, customer.getState(), sale.getSalesPrice(), sale.getTimeStamp());
				} else {
					addYear(result, customer.getState(), sale.getSalesPrice(), sale.getTimeStamp());
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured while getting sales by " + param + " for state: " + e);
		}
		return result;
	}

	/**
	 * Returns the state and sales price with delimiter for hour granularity
	 */
	private static List<String> addYearMonthDayHour(List<String> result, String state, Long sales, long epoch) {
		ZonedDateTime date = getDate(epoch);
		StringBuilder builder = new StringBuilder();
		builder.append(state).append(DELIMITER).append(date.getYear()).append(DELIMITER).append(date.getMonthValue())
				.append(DELIMITER).append(date.getDayOfMonth()).append(DELIMITER).append(date.getHour())
				.append(DELIMITER).append(sales);
		result.add(builder.toString());
		return result;
	}

	/**
	 * Returns the state and sales price with delimiter for day granularity
	 */
	private static List<String> addYearMonthDay(List<String> result, String state, Long sales, long epoch) {
		ZonedDateTime date = getDate(epoch);
		StringBuilder builder = new StringBuilder();
		builder.append(state).append(DELIMITER).append(date.getYear()).append(DELIMITER).append(date.getMonthValue())
				.append(DELIMITER).append(date.getDayOfMonth()).append(DELIMITER).append(DELIMITER).append(sales);
		result.add(builder.toString());
		return result;
	}

	/**
	 * Returns the state and sales price with delimiter for month granularity
	 */
	private static List<String> addYearMonth(List<String> result, String state, Long sales, long epoch) {
		ZonedDateTime date = getDate(epoch);
		StringBuilder builder = new StringBuilder();
		builder.append(state).append(DELIMITER).append(date.getYear()).append(DELIMITER).append(date.getMonthValue())
				.append(DELIMITER).append(DELIMITER).append(DELIMITER).append(sales);
		result.add(builder.toString());
		return result;
	}

	/**
	 * Returns the state and sales price with delimiter for year granularity
	 */
	private static List<String> addYear(List<String> result, String state, Long sales, long epoch) {
		ZonedDateTime date = getDate(epoch);
		StringBuilder builder = new StringBuilder();
		builder.append(state).append(DELIMITER).append(date.getYear()).append(DELIMITER).append(DELIMITER)
				.append(DELIMITER).append(DELIMITER).append(sales);
		result.add(builder.toString());
		return result;
	}

	/**
	 * Returns the ZonedDateTime from epoch time
	 */
	private static ZonedDateTime getDate(long epoch) {
		Instant instant = Instant.ofEpochSecond(epoch);
		ZonedDateTime date = ZonedDateTime.ofInstant(instant, ZoneOffset.UTC);
		return date;
	}
}
