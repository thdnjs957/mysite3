package com.cafe24.mysite.controller.api;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.cafe24.mysite.config.AppConfig;
import com.cafe24.mysite.config.WebConfig;
import com.cafe24.mysite.vo.GuestbookVo;
import com.google.gson.Gson;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={AppConfig.class, WebConfig.class})
@WebAppConfiguration
public class GuestbookControllerTest {

	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	
	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	public void testFetchGuestbookList() throws Exception {
		ResultActions resultActions =
		mockMvc
		.perform(get("/api/guestbook/list/{no}", 1L).contentType(MediaType.APPLICATION_JSON));
		
		resultActions
		.andExpect(status().isOk())
		.andDo(print())
		.andExpect(jsonPath("$.result", is("success")))
		.andExpect(jsonPath("$.data", hasSize(2)))
		.andExpect(jsonPath("$.data[0].no", is(1)))
		.andExpect(jsonPath("$.data[0].name", is("user1")))
		.andExpect(jsonPath("$.data[0].contents", is("test1")));
	}
	
	
	@Test
	public void testInsertGuestbook() throws Exception{
		
		GuestbookVo vo = new GuestbookVo();

		//그냥 집어넣어도 되는데 이렇게 쓰면 더 좋은점 지금은 vo로 했는데 나중에 기능이 좀 있는 애들 이렇게 함 ex) 이메일 sender 요청 받을때
		//Mockito.when(voMock.getNo2()).thenReturn(10L); //만약 없는 메소드면 껍데기만 만들어놓고
		//Long no2 = voMock.getNo2();

//		MailSender mailSender = mock(MailSender.class);
//		Mockito.when(mailSenderMock.sendMail("받는사람","주소","내용")).thenReturn(true);
//		isSuccess = mailSenderMock.sendMail("");
		
		vo.setName("user1");
		vo.setPassword("1234");
		vo.setContents("test1");
		
		ResultActions resultActions = 
			mockMvc
			.perform(post("/api/guestbook/add").contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(vo)));
				
		resultActions.andExpect(status().isOk())
		.andDo(print())
		.andExpect(jsonPath("$.result", is("success")))
		.andExpect(jsonPath("$.data.name", is(vo.getName())))
		.andExpect(jsonPath("$.data.contents", is(vo.getContents())));
	}
	
	@Test
	public void testDeleteGuestbook() throws Exception{
		
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("no", 3L);
		map.put("password", "1234");
	
		ResultActions resultActions = 
			mockMvc
			.perform(delete("/api/guestbook/delete").contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(map)));

		//delete 요청에 대한 응답이 왔을때 클라이언트 단에서 또 삭제를 해줘야하니깐 No 값을 받아야 함 
		resultActions.andExpect(status().isOk())
		.andDo(print())
		.andExpect(jsonPath("$.result", is("success")));
		
	}
	
}

