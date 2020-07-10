package com.javapropjects.ccf;

import static org.mockito.Mockito.when;

import com.javapropjects.ccf.model.PathStatus;
import com.javapropjects.ccf.services.ConnectionCheckService;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class CityConnectionsFinderApplicationTests {

	@Autowired
	private MockMvc mockmvc;

	

	@Before
	public void setUp() {

	}

	@Test
	public void checkInvalidRequest() throws Exception {
		mockmvc.perform(MockMvcRequestBuilders.get("/connected").param("origin", "").param("destination", "")
				.accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	public void checkFoundConnectionBetweenCities() throws Exception {
		mockmvc.perform(MockMvcRequestBuilders.get("/connected").param("origin", "Boston").param("destination", "Newark")
				.accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.result").value(PathStatus.FOUND.name()));
	}

	@Test
	public void checkNotFoundConnectionBetween() throws Exception {
		mockmvc.perform(MockMvcRequestBuilders.get("/connected").param("origin", "Philadelphia").param("destination", "Albany")
		.accept(MediaType.APPLICATION_JSON)).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.result").value(PathStatus.NOT_FOUND.name()));
	}

}
