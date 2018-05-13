package org.intuit.customersales.config;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.intuit.customersales.entity.Customer;
import org.intuit.customersales.entity.Sale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import scala.Tuple2;

@Component
public class SparkJoinRDD {
	
	@Autowired
	private JavaSparkContext sparkContext; 
	
	private final static String CUSTOMER = "customer";
	private final static String SALES = "sales";
    
	/**
	 * Gets the Java RDD for Customer from the text file
	 * @throws FileNotFoundException
	 * @throws IllegalArgumentException
	 */
	public JavaRDD<String> getCustomerRddFromTextFile(String customerFilePath) throws FileNotFoundException, IllegalArgumentException {
		return getRDDFromTextFile(CUSTOMER, customerFilePath);
	}
	
	/**
	 * Gets the Java RDD for Sales from the text file
	 * @throws FileNotFoundException
	 * @throws IllegalArgumentException
	 */	
	public JavaRDD<String> getSalesRddFromTextFile(String salesFilePath) throws FileNotFoundException, IllegalArgumentException {
		return getRDDFromTextFile(SALES, salesFilePath);
	}
	
	/**
	 * Returns the list of Joined pair RDD
	 * @return List<Tuple2<Integer, Tuple2<Sale, Customer>>>
	 * @throws Exception
	 */
	public List<Tuple2<Integer, Tuple2<Sale, Customer>>> getListOfJoinedPairedRDD(JavaRDD<String> customerRdd, JavaRDD<String> salesRdd, String delimiter) throws Exception {	
		return getJoinedPairRdd(customerRdd, salesRdd, delimiter).collect();		
	}
	
	/**
	 * Joined paired RDD returns result in <Key,Value> pair like [customerId, Tuple2<Sale, Customer>]
	 * Once RDD is merged w.r.t customerId, we need to group by states to calculate the total price.
	 * So taking values from the joined paired RDD and again creating pair RDD for String and Price like [state,price]
	 * GroupBy gives RDD with [state, iterable<price>].
	 * Looping this values and adding gives the total price for that state.
	 * @return
	 * @throws Exception
	 */
	public Set<Map.Entry<String, Iterable<Long>>> getSetOfJoinedPairedRDDValues(JavaRDD<String> customerRdd, JavaRDD<String> salesRdd, String delimiter) throws Exception {
		JavaRDD<Tuple2<Sale, Customer>> valuesRdd = getJoinedPairRdd(customerRdd, salesRdd, delimiter).values();
        
        JavaPairRDD<String, Long> stateCustomerSalesRdd = valuesRdd.mapToPair(t -> {        	
            	String state = t._2.getState();
            	long salesPrice = t._1().getSalesPrice();               
                return new Tuple2<String, Long>(state, salesPrice);
        });

        JavaPairRDD<String, Iterable<Long>> salesByState = stateCustomerSalesRdd.groupByKey();  
        return salesByState.collectAsMap().entrySet();				
	}
	/**
	 * Returns the Java RDD for customer/sales from text file
	 * @param param
	 * @param filePath
	 * @return
	 * @throws FileNotFoundException
	 * @throws IllegalArgumentException
	 */
	private JavaRDD<String> getRDDFromTextFile(String param, String filePath) throws FileNotFoundException, IllegalArgumentException {
		if(filePath == null) {
			throw new FileNotFoundException("Text file path for "+param+" is empty");
		}    	
		JavaRDD<String> rdd = sparkContext.textFile(filePath); 	
		
		if(rdd == null) {
			throw new IllegalArgumentException(param+ " RDD is empty");
		}
		return rdd;
	}
	
	/**
	 * Returns the the Joined pair RDD from the customer and sales text file
	 */
	private JavaPairRDD<Integer, Tuple2<Sale, Customer>> getJoinedPairRdd(JavaRDD<String> customerRdd, JavaRDD<String> salesRdd, String delimiter) throws Exception {			        
        JavaPairRDD<Integer, Sale> idSales = pairSalesRdd(salesRdd, delimiter);
        JavaPairRDD<Integer, Customer> idCustomers = pairCustomerRdd(customerRdd, delimiter);         
        return idSales.join(idCustomers);		
	}
	
	/**
	 * Returns the Java pair RDD mapping customerId with Sales object from Text file
	 */
	private JavaPairRDD<Integer, Sale> pairSalesRdd(JavaRDD<String> salesRdd, String delimiter) throws Exception {
		JavaPairRDD<Integer, Sale> idSales = salesRdd.mapToPair(s -> {
            String[] words = s.split(Pattern.quote(delimiter));
            Sale sale = new Sale(words[1], Long.parseLong(words[0]), Long.parseLong(words[2]));                
            return new Tuple2<Integer, Sale>(Integer.parseInt(words[1]), sale);
		});
		return idSales;
	}
	
	/**
	 * Returns the Java pair RDD mapping customerId with Customer object from Text file
	 */
	private JavaPairRDD<Integer, Customer> pairCustomerRdd(JavaRDD<String> customerRdd, String delimiter) throws Exception {
		JavaPairRDD<Integer, Customer> idCustomers = customerRdd.mapToPair(s -> {
            String[] words = s.split(Pattern.quote(delimiter));
            Customer customer = new Customer(words[4]);
            return new Tuple2<Integer, Customer>(Integer.parseInt(words[0]), customer);
		});
		return idCustomers;
	}

}
