package springboot.demo;

import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class GreetingControllerTest {
    @Autowired
    private MockMvc mvc;

    void assertGreeting(String param) throws Exception {
        switch(param) {
            case null: {
                mvc.perform(get("/greeting"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("greeting"))
                    .andExpect(model().attribute("name", "World"))
                    .andExpect(content().string(
                        containsString("Hello, " + "World")
                ));
            }
            break;
                
            default: {
                mvc.perform(get("/greeting").param("name", param))
                    .andExpect(status().isOk())
                    .andExpect(view().name("greeting"))
                    .andExpect(model().attribute("name", param))
                    .andExpect(content().string(
                        containsString("Hello, " + param)
                ));
            }
        };
    }

    @Test
    public void greetingDefault() throws Exception {
        assertGreeting(null);
    }

    @Test
    public void greetingParam() throws Exception {
        assertGreeting("hello");
    }
}
