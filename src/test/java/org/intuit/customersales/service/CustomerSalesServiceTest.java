package org.intuit.customersales.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.intuit.customersales.entity.Customer;
import org.intuit.customersales.entity.Sale;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import scala.Tuple2;

@RunWith(SpringRunner.class)
public class CustomerSalesServiceTest {

	@InjectMocks
	private CustomerSalesServiceImpl customerSalesService;

	@Before
	public void setup() throws Exception {
		ReflectionTestUtils.setField(customerSalesService, "list", getListOfJoinedPairedRDD());
		ReflectionTestUtils.setField(customerSalesService, "set", getSetOfJoinedPairedRDDValues());
	}

	private List<Tuple2<Integer, Tuple2<Sale, Customer>>> getListOfJoinedPairedRDD() throws Exception {
		List<Tuple2<Integer, Tuple2<Sale, Customer>>> list = new ArrayList<Tuple2<Integer, Tuple2<Sale, Customer>>>();
		Sale s = new Sale("123", 1454313600l, 123456l);
		Customer c = new Customer("AL");
		Tuple2<Integer, Tuple2<Sale, Customer>> t = new Tuple2<Integer, Tuple2<Sale, Customer>>(123,
				new Tuple2<Sale, Customer>(s, c));
		list.add(t);
		return list;
	}

	private Set<Map.Entry<String, Iterable<Long>>> getSetOfJoinedPairedRDDValues() throws Exception {
		Map<String, Iterable<Long>> map = new HashMap<>();
		Long[] values = { 100l, 200l, 300l };
		Iterable<Long> list = Arrays.asList(values);
		map.put("AL", list);
		return map.entrySet();
	}

	@Test
	public void testTotalSales() throws Exception {
		List<String> list = customerSalesService.getStateTotalSales();
		assertThat(list).isNotEmpty();
		assertThat(list.size()).isEqualByComparingTo(1);
		assertThat(list.get(0)).isEqualTo("AL#600");
	}

	@Test
	public void testSalesByHour() throws Exception {
		List<String> list = customerSalesService.getSalesByHour();
		assertThat(list).isNotEmpty();
		assertThat(list.size()).isEqualByComparingTo(1);
		assertThat(list.get(0)).isEqualTo("AL#2016#2#1#8#123456");
	}

	@Test
	public void getSalesByDay() throws Exception {
		List<String> list = customerSalesService.getSalesByDay();
		assertThat(list).isNotEmpty();
		assertThat(list.size()).isEqualByComparingTo(1);
		assertThat(list.get(0)).isEqualTo("AL#2016#2#1##123456");
	}

	@Test
	public void testSalesByMonth() throws Exception {
		List<String> list = customerSalesService.getSalesByMonth();
		assertThat(list).isNotEmpty();
		assertThat(list.size()).isEqualByComparingTo(1);
		assertThat(list.get(0)).isEqualTo("AL#2016#2###123456");
	}

	@Test
	public void testSalesByYear() throws Exception {
		List<String> list = customerSalesService.getSalesByYear();
		assertThat(list).isNotEmpty();
		assertThat(list.size()).isEqualByComparingTo(1);
		assertThat(list.get(0)).isEqualTo("AL#2016####123456");
	}

	@Test
	public void testTotalSalesWhenSetIsNull() throws Exception {
		ReflectionTestUtils.setField(customerSalesService, "set", null);
		List<String> list = customerSalesService.getStateTotalSales();
		assertThat(list).isEmpty();
	}

	@Test
	public void testSalesByMonthWhenListIsNull() throws Exception {
		ReflectionTestUtils.setField(customerSalesService, "list", null);
		List<String> list = customerSalesService.getSalesByMonth();
		assertThat(list).isEmpty();
	}

	@Test
	public void testSalesByDayWhenListIsNull() throws Exception {
		ReflectionTestUtils.setField(customerSalesService, "list", null);
		List<String> list = customerSalesService.getSalesByDay();
		assertThat(list).isEmpty();
	}

	@Test
	public void testSalesByHourWhenListIsNull() throws Exception {
		ReflectionTestUtils.setField(customerSalesService, "list", null);
		List<String> list = customerSalesService.getSalesByHour();
		assertThat(list).isEmpty();
	}

	@Test
	public void testSalesByYearWhenListIsNull() throws Exception {
		ReflectionTestUtils.setField(customerSalesService, "list", null);
		List<String> list = customerSalesService.getSalesByYear();
		assertThat(list).isEmpty();
	}

}
