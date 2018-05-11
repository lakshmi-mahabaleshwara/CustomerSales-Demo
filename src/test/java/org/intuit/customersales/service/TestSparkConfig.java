package org.intuit.customersales.service;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration
public class TestSparkConfig {
	@Bean
	public SparkConf sparkConf() {
		SparkConf sparkConf = new SparkConf().setAppName("CustomerSales").setMaster("local");
		return sparkConf;
	}

	@Bean
	public JavaSparkContext javaSparkContext() {
		return new JavaSparkContext(sparkConf());
	}

}
