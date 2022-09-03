package com.example.springsweater.controller;

import com.example.springsweater.service.PostService;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@AutoConfigureMockMvc
@SpringBootTest
@WithUserDetails("admin")
class PostControllerTest {

    @MockBean
    private PostService service;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext web;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(web)
                .apply(springSecurity())
                .build();
    }

    @Test
    void createdMockMvc(){
        assertNotNull(mockMvc);
    }

    @Test
    void homePage() throws Exception {
        mockMvc.perform(get("/")).andExpect(status().isOk());
    }

    @Test
    void authAdmin() throws Exception {
        mockMvc.perform((formLogin("/login").user("admin").password("admin123")))
                .andExpect(authenticated());
    }

    @Test
    void authUser() throws Exception {
        mockMvc.perform((formLogin("/login").user("user").password("user123")))
                .andExpect(authenticated());
    }

    @Test
    void getAll() throws Exception {
        mockMvc.perform(get("/post"))
                .andExpect(status().isOk());
    }

    @Test
    void findPost() throws Exception {
        mockMvc.perform(post("/post")
                        .param("content", "testing")
                        .param("tag", "testing"))
                .andExpect(status().isOk());
    }

    @Test
    void getAddPost() throws Exception {
        mockMvc.perform(get("/post/add"))
                .andExpect(status().isOk());
    }

    @Test
    void makePost() throws Exception {
        MockHttpServletRequestBuilder builder = multipart("/post/add")
                .param("content", "testing")
                .param("tag", "testing")
                .with(csrf());


        mockMvc.perform(builder)
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void getToEditPostByAdmin() throws Exception {
        mockMvc.perform(get("/post/edit/" + 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("postEdit"));
    }

    @Test
    void editPost() throws Exception {
        mockMvc.perform(post("/post/edit/" + 1)
                        .param("content", "testing")
                        .param("tag", "testing"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void getToDelete() throws Exception {
        mockMvc.perform(get("/post/delete/" + 25))
                .andExpect(status().is3xxRedirection());
    }
}