package org.intuit.customersales.service;

import java.util.List;

public interface CustomerSalesService {

	public List<String> getStateTotalSales() throws Exception;

	public List<String> getSalesByHour() throws Exception;

	public List<String> getSalesByMonth() throws Exception;

	public List<String> getSalesByDay() throws Exception;

	public List<String> getSalesByYear() throws Exception;

}
