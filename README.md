# CustomerSales-Demo

#### Problem Statement:

You have given two data sets in HDFS as mentioned below.

o	Customer information <customer_id,name,street,city,state,zip> 

o	Sales information <timestamp,customer_id,sales_price>
     
Implement a Spark application using Spark Core (not with Spark SQL) to get <state,total_sales> for (year, month, day and hour) granularities. Be prepared to present your code and demonstrate it running with appropriate input and output.

Notes: 
1.	You can consider input/output data set in any one of the below format<br>
a.	Text(with any DELIMITER)<br>
b.	AVRO<br>
c.	PARQUET<br>
2.	Consider timestamp in epoch (for example 1500674713)<br>
3.	We encourage you to consider all possible cases of datasets like number of states are small(finitely known set) OR huge(unknown set) and come with appropriate solutions.

You can you use any of PYTHON/SCALA/JAVA API’s of your choice.

#### Assumptions:
1. Customer and Sales datasets are provided in HDFS.<br>
2. No Headers in the customers and sales text file.<br>
3. “#” is used as delimiter in text files.<br>

#### Technologies Used:

Maven 3.5.0<br>
Java 1.8<br>
SpringBoot 1.5.12<br>
Spark 2.1.0<br>
Swagger 2<br>
Junit 4.12<br>
AWS

#### Configurable parameters:
Below are the configurable parameters in application.properties file (inside jar)<br>
	```
	app.name=CustomerSales	
	master.uri=local
	customer.file.path=input/customers.txt
	sales.file.path=input/sales.txt
	delimeter=#
	```

During the start of the application, if **--spring.config.location=property file name** is not given above parameters are used from application.properties file which is inside jar<br>

There is an application.override.properties file(outside jar file), where we can configure the file format, delimiter and path. When application is started as below it overrides the parameters inside jar file.<br>

**java -jar ~/CustomerSales-Demo/target/customersales-0.0.1-SNAPSHOT.jar --spring.config.location=application.override.properties**<br>

##### Use the file from s3
- source ./set-env.sh (export your AWS accesskey and accesssecret)<br>
- Configure below parameters to get from s3 in application.override.properties<br>
	```
	customer.file.path=s3n://bucket_name/filename
	sales.file.path=s3n://bucket_name/filename
	delimeter=delimiter
	```

Start application as **java -jar ~/CustomerSales-Demo/target/customersales-0.0.1-SNAPSHOT.jar --spring.config.location=application.override.properties**<br>


#### Run the Application Locally:
git clone https://github.com/lakshmi-mahabaleshwara/CustomerSales-Demo.git<br>
mvn clean install<br>
java -jar ~/CustomerSales-Demo/target/customersales-0.0.1-SNAPSHOT.jar (Uses the application.properties file from jar)<br>

#### Healthcheck endpoint
http://localhost:8080/health <br>
http://intuitcus-elasticl-11xxi0hlbd9bm-1213420232.us-west-1.elb.amazonaws.com/health <br>

#### Swagger URL's
Localhost and Cloud url<br>
http://localhost:8080/swagger-ui.html#/ <br>
http://intuitcus-elasticl-11xxi0hlbd9bm-1213420232.us-west-1.elb.amazonaws.com/swagger-ui.html#/<br>

#### RestAPI URL’s

HTTP GET Method <br>

Localhost url <br>
http://localhost:8080/spark/totalSales <br>
http://localhost:8080/spark/salesByHour <br>
http://localhost:8080/spark/salesByDay<br>
http://localhost:8080/spark/salesByYear<br>
http://localhost:8080/spark/salesByMonth<br>

Cloud url <br>

http://intuitcus-elasticl-11xxi0hlbd9bm-1213420232.us-west-1.elb.amazonaws.com/spark/totalSales<br>
http://intuitcus-elasticl-11xxi0hlbd9bm-1213420232.us-west-1.elb.amazonaws.com/spark/salesByHour<br>
http://intuitcus-elasticl-11xxi0hlbd9bm-1213420232.us-west-1.elb.amazonaws.com/spark/salesByDay<br>
http://intuitcus-elasticl-11xxi0hlbd9bm-1213420232.us-west-1.elb.amazonaws.com/spark/salesByYear<br>
http://intuitcus-elasticl-11xxi0hlbd9bm-1213420232.us-west-1.elb.amazonaws.com/spark/salesByMonth<br>

#### AWS Deployment Diagram:

![aH3lt text](https://github.com/lakshmi-mahabaleshwara/CustomerSales-Demo/blob/master/Deployment_Diagram.png?raw=true "Title")

#### Future Improvements:
1. https call (SSL certificate) <br>
2. User Authentication and Authorization for Rest call <br>
3. Dynamic update of text file. (If the text file gets updated, we need to restart the Application to load the latest data)<br>
4. AWS IAM profile use.<br>
5. Spark Cluster<br>
6. Amazon EMR <br>

#### References:
https://www.udemy.com/apache-spark-course-with-java/learn/v4/overview<br>
https://www.toptal.com/spark/introduction-to-apache-spark<br>
http://www.tothenew.com/blog/how-to-use-groupby-and-join-in-apache-spark/<br>
https://docs.spring.io/spring-boot/docs/1.5.12.RELEASE/reference/html/<br>

