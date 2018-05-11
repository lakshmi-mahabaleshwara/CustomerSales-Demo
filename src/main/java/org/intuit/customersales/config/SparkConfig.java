package org.intuit.customersales.config;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Class to hold all spark configurations
 * @author lakshmimahabaleshwara
 *
 */
@Configuration
@PropertySource("classpath:application.properties")
public class SparkConfig {
	
    @Value("${app.name:CustomerSales}")
    private String appName;

    @Value("${master.uri:local}")
    private String masterUri;
    
    @Bean
    public SparkConf sparkConf() {
        SparkConf sparkConf = new SparkConf()
                .setAppName(appName)
                .setMaster(masterUri);
        return sparkConf;
    }
    
    @Bean
    public JavaSparkContext javaSparkContext() {
        return new JavaSparkContext(sparkConf());
    }
}
