package com.vart.psychoweb;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class HelloControllerTest {
    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

    @BeforeEach
    void setup (){
        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .apply(springSecurity())
                .build();
    }

    @Test
    void getHelloWorldAdminTest () throws Exception {
        mockMvc.perform(get("/api/ping")
                        .with(httpBasic("admin", "admin")
                        ))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello world!"));
    }

    @Test
    void getHelloWorldCustomerTest () throws Exception {
        mockMvc.perform(get("/api/ping")
                        .with(httpBasic("customer", "customer")
                        ))
                .andExpect(status().isForbidden());
    }

    @Test
    void getHelloWorldAnonymousTest () throws Exception {
        mockMvc.perform(get("/api/ping")
                        )
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getWorldHelloAdminTest () throws Exception {
        mockMvc.perform(get("/api/ping/1")
                        .with(httpBasic("user", "user")
                        ))
                .andExpect(status().isForbidden());
    }

    @Test
    void getWorldHelloCustomerTest () throws Exception {
        mockMvc.perform(get("/api/ping/1")
                        .with(httpBasic("customer", "customer")
                        ))
                .andExpect(status().isOk())
                .andExpect(content().string("World Hello!"));
    }


    @Test
    void getWorldHelloAnonymousTest () throws Exception {
        mockMvc.perform(get("/api/ping/1")
                )
                .andExpect(status().isUnauthorized());
    }
}
