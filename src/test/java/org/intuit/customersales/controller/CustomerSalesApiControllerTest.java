package org.intuit.customersales.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Arrays;
import org.intuit.customersales.controller.CustomerSalesApiController;
import org.intuit.customersales.service.CustomerSalesService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerSalesApiController.class)
public class CustomerSalesApiControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CustomerSalesService customerSalesService;

	@Test
	public void getTotalSalesForState() throws Exception {
		String jsonContent = "[\"AL#247316\"]";
		when(customerSalesService.getStateTotalSales()).thenReturn(Arrays.asList("AL#247316"));
		this.mockMvc.perform(get("/spark/totalSales")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().json(jsonContent));
	}

	@Test
	public void getSalesByHour() throws Exception {
		String jsonContent = "[\"AL#2018#5#9#12#100\"]";
		when(customerSalesService.getSalesByHour()).thenReturn(Arrays.asList("AL#2018#5#9#12#100"));
		this.mockMvc.perform(get("/spark/salesByHour")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().json(jsonContent));
	}

	@Test
	public void getSalesByMonth() throws Exception {
		String jsonContent = "[\"AL#2018#5###100\"]";
		when(customerSalesService.getSalesByMonth()).thenReturn(Arrays.asList("AL#2018#5###100"));
		this.mockMvc.perform(get("/spark/salesByMonth")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().json(jsonContent));
	}

	@Test
	public void getSalesByDay() throws Exception {
		String jsonContent = "[\"AL#2018#5#9##10\"]";
		when(customerSalesService.getSalesByDay()).thenReturn(Arrays.asList("AL#2018#5#9##10"));
		this.mockMvc.perform(get("/spark/salesByDay")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().json(jsonContent));
	}

	@Test
	public void getSalesByYear() throws Exception {
		String jsonContent = "[\"AL#2018####100\"]";
		when(customerSalesService.getSalesByYear()).thenReturn(Arrays.asList("AL#2018####100"));
		this.mockMvc.perform(get("/spark/salesByYear")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().json(jsonContent));
	}

}
