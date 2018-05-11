# CustomerSales-Demo

#### Problem Statement:

You have given two data sets in HDFS as mentioned below.

o	Customer information <customer_id,name,street,city,state,zip> 

o	Sales information <timestamp,customer_id,sales_price>
     
Implement a Spark application using Spark Core (not with Spark SQL) to get <state,total_sales> for (year, month, day and hour) granularities. Be prepared to present your code and demonstrate it running with appropriate input and output.

Notes: 
1.	You can consider input/output data set in any one of the below format
a.	Text(with any DELIMITER)
b.	AVRO
c.	PARQUET
2.	Consider timestamp in epoch (for example 1500674713)
3.	We encourage you to consider all possible cases of datasets like number of states are small(finitely known set) OR huge(unknown set) and come with appropriate solutions.

You can you use any of PYTHON/SCALA/JAVA API’s of your choice.

#### Assumptions:
1. Customer and Sales datasets are provided from HDFS and stored in local file system under input folder with name customers.txt and sales.txt<br>
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

#### Run the Application Locally:
git clone git clone https://github.com/lakshmi-mahabaleshwara/CustomerSales-Demo.git<br>
mvn clean install<br>
java -jar ~/CustomerSales-Demo/target/customersales-0.0.1-SNAPSHOT.jar<br>

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

![aH3lt text](/Users/lakshmimahabaleshwara/Desktop/Screen Shot 2018-05-11 at 1.53.15 PM.png?raw=true "Title")

#### Not Covered:
1. https call (SSL certificate) <br>
2. User Authentication and Authorization for Rest call <br>
3. Dynamic update of text file. (If the text file gets updated, we need to restart the Application to load the latest data)<br>
4. Caching the data.<br>

#### References:
https://www.udemy.com/apache-spark-course-with-java/learn/v4/overview<br>
https://www.toptal.com/spark/introduction-to-apache-spark<br>
http://www.tothenew.com/blog/how-to-use-groupby-and-join-in-apache-spark/<br>
https://docs.spring.io/spring-boot/docs/1.5.12.RELEASE/reference/html/<br>

