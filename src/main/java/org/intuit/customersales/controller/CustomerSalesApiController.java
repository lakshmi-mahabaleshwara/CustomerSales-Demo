package org.intuit.customersales.controller;

import java.util.List;

import org.intuit.customersales.service.CustomerSalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

/**
 * Controller class
 * 
 * @author lakshmimahabaleshwara
 *
 */
@RestController()
@RequestMapping(value = "spark")
public class CustomerSalesApiController {

	@Autowired
	private CustomerSalesService customerSalesService;

	@ApiOperation(value = "Get the total sales for the states", response = List.class)
	@RequestMapping(method = RequestMethod.GET, value = "/totalSales")
	public List<String> getTotalSalesForState() throws Exception {
		return customerSalesService.getStateTotalSales();
	}

	@ApiOperation(value = "Get the sales by hour for the state", response = List.class)
	@RequestMapping(method = RequestMethod.GET, value = "/salesByHour")
	public List<String> getSalesByHour() throws Exception {
		return customerSalesService.getSalesByHour();
	}

	@ApiOperation(value = "Get the sales by month for the state", response = List.class)
	@RequestMapping(method = RequestMethod.GET, value = "/salesByMonth")
	public List<String> getSalesByMonth() throws Exception {
		return customerSalesService.getSalesByMonth();
	}

	@ApiOperation(value = "Get the sales by day for the state", response = List.class)
	@RequestMapping(method = RequestMethod.GET, value = "/salesByDay")
	public List<String> getSalesByDay() throws Exception {
		return customerSalesService.getSalesByDay();
	}

	@ApiOperation(value = "Get the sales by year for the state", response = List.class)
	@RequestMapping(method = RequestMethod.GET, value = "/salesByYear")
	public List<String> getSalesByYear() throws Exception {
		return customerSalesService.getSalesByYear();
	}

}
