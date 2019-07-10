package com.cafe24.mysite.controller.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.cafe24.config.web.TestWebConfig;
import com.cafe24.mysite.config.AppConfig;
import com.cafe24.mysite.config.WebConfig;
import com.cafe24.mysite.service.GuestbookService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={AppConfig.class, WebConfig.class})
@WebAppConfiguration
public class GuestbookControllerTest {

	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Autowired
	private GuestbookService guestbookService;
	

//	@Autowired
//	private Calcurator cal;
//	
//	
//	@Test                                                                            
//    public void testSum(){                
//        double result = cal.sum(10, 50);                                  
//        assertEquals(60, result, 0);                                          
//    }
	
	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	public void testDIGuestbookService() {
		assertNotNull(guestbookService);
	}
	
	@Test
	public void testFetchGuestbookList() throws Exception {
		mockMvc
		.perform(get("/api/guestbook/list")) // 없는 url
		.andExpect(status().isNotFound());
	}
	
	
}