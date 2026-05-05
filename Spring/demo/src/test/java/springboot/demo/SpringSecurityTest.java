package springboot.demo;


import org.junit.jupiter.api.Test;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.FormLoginRequestBuilder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SpringSecurityTest {
    @Autowired
	private MockMvc mockMvc;


    @Test
    void unsecuredPaths() throws Exception {
        mockMvc.perform(get("/")).andExpect(status().isOk());
        mockMvc.perform(get("/greeting")).andExpect(status().isOk());
        mockMvc.perform(get("/home")).andExpect(status().isOk());

    }

    @Test
	void securedPathsRedirectToLogin() throws Exception {
		mockMvc.perform(get("/hello"))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/login"));
	}

    @Test
	@WithMockUser
	void securedPathsLoggedIn() throws Exception {
		var result = mockMvc.perform(get("/hello")).andExpect(status().isOk()).andReturn();

		assertThat(result.getResponse().getContentAsString()).contains("Hello user!");
	}
}
