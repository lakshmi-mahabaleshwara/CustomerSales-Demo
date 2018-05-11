package org.intuit.customersales.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.spark.api.java.JavaRDD;
import org.intuit.customersales.config.SparkJoinRDD;
import org.intuit.customersales.entity.Customer;
import org.intuit.customersales.entity.Sale;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import scala.Tuple2;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { TestSparkConfig.class, SparkJoinRDD.class })
public class SparkJoinRDDTest {

	@Autowired
	private SparkJoinRDD sparkJoinRDD;

	@Test
	public void testGetCustomerRddFromTextFile() throws Exception {
		JavaRDD<String> customerRdd = sparkJoinRDD.getCustomerRddFromTextFile("input/customers_test.txt");
		assertThat(customerRdd).isNotNull();
	}

	@Test
	public void testGetSalesRddFromTextFile() throws Exception {
		JavaRDD<String> salesRdd = sparkJoinRDD.getSalesRddFromTextFile("input/sales_test.txt");
		assertThat(salesRdd).isNotNull();
	}

	@Test
	public void testGetCustomerRddFromTextFileWhenFileNotExists() throws Exception {
		try {
			sparkJoinRDD.getCustomerRddFromTextFile("input/doesnot_exists.txt");
		} catch (FileNotFoundException e) {
			assertThat(e.getMessage()).isEqualTo("Text file path for customer is empty");
		}
	}

	@Test
	public void testGetSalesRddFromTextFileWhenFileNotExists() throws Exception {
		try {
			sparkJoinRDD.getSalesRddFromTextFile("input/doesnot_exists.txt");
		} catch (FileNotFoundException e) {
			assertThat(e.getMessage()).isEqualTo("Text file path for sales is empty");
		}
	}

	@Test
	public void testGetListOfJoinedPairedRDD() throws Exception {
		JavaRDD<String> customerRdd = sparkJoinRDD.getCustomerRddFromTextFile("input/customers_test.txt");
		JavaRDD<String> salesRdd = sparkJoinRDD.getSalesRddFromTextFile("input/sales_test.txt");
		List<Tuple2<Integer, Tuple2<Sale, Customer>>> list = sparkJoinRDD.getListOfJoinedPairedRDD(customerRdd,
				salesRdd);
		assertThat(list).isNotEmpty();
		assertThat(list.size()).isEqualByComparingTo(1);
	}

	@Test
	public void testGetListOfJoinedPairedRDDWhenNoContent() throws Exception {
		JavaRDD<String> customerRdd = sparkJoinRDD.getCustomerRddFromTextFile("input/customers_test_nocontent.txt");
		JavaRDD<String> salesRdd = sparkJoinRDD.getSalesRddFromTextFile("input/sales_test_nocontent.txt");
		List<Tuple2<Integer, Tuple2<Sale, Customer>>> list = sparkJoinRDD.getListOfJoinedPairedRDD(customerRdd,
				salesRdd);
		assertThat(list).isEmpty();
		assertThat(list.size()).isEqualByComparingTo(0);
	}

	@Test
	public void testGetSetOfJoinedPairedRDDValues() throws Exception {
		JavaRDD<String> customerRdd = sparkJoinRDD.getCustomerRddFromTextFile("input/customers_test.txt");
		JavaRDD<String> salesRdd = sparkJoinRDD.getSalesRddFromTextFile("input/sales_test.txt");
		Set<Map.Entry<String, Iterable<Long>>> set = sparkJoinRDD.getSetOfJoinedPairedRDDValues(customerRdd, salesRdd);
		assertThat(set).isNotEmpty();
		assertThat(set.size()).isEqualByComparingTo(1);
	}

	@Test
	public void testGetSetOfJoinedPairedRDDValuesWhenNoContent() throws Exception {
		JavaRDD<String> customerRdd = sparkJoinRDD.getCustomerRddFromTextFile("input/customers_test_nocontent.txt");
		JavaRDD<String> salesRdd = sparkJoinRDD.getSalesRddFromTextFile("input/sales_test_nocontent.txt");
		Set<Map.Entry<String, Iterable<Long>>> set = sparkJoinRDD.getSetOfJoinedPairedRDDValues(customerRdd, salesRdd);
		assertThat(set).isEmpty();
		assertThat(set.size()).isEqualByComparingTo(0);
	}

}
