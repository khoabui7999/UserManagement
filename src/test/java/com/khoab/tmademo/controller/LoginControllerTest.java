package com.khoab.tmademo.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.khoab.tmademo.entity.LoginRequest;
import com.khoab.tmademo.entity.UserEntity;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    private String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    private <T> T mapFromJson(String json, Class<T> tClass)
            throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, tClass);
    }

    @Test
    public void testLogin() throws Exception {
        LoginRequest loginRequest = new LoginRequest("admin", "123");
        String json = mapToJson(loginRequest);

        mvc.perform(MockMvcRequestBuilders.post("/login")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().string("Account granted"));
    }

    @Test
    public void testGetAccessToken() throws Exception {
        String uri = "/oauth/token?grant_type=password&username=admin&password=123";
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri)
                .with(httpBasic("my-client-id", "secret"))
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        int status = result.getResponse().getStatus();
        assertEquals(200, status);
        String content = result.getResponse().getContentAsString();
        assertTrue(content.contains("access_token"));
    }

    @Test
    public void testFindAllAsAdmin() throws Exception {
        String tokenUri = "/oauth/token?grant_type=password&username=admin&password=123";
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(tokenUri)
                .with(httpBasic("my-client-id", "secret"))
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        int status = result.getResponse().getStatus();
        assertEquals(200, status);

        String tokenContent = result.getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(tokenContent);
        String accessToken = jsonObject.getString("access_token");

        result = mvc.perform(MockMvcRequestBuilders.get("/secured/findall")
                .header("Authorization", "Bearer " + accessToken)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        status = result.getResponse().getStatus();
        assertEquals(200, status);
        String userContent = result.getResponse().getContentAsString();
        UserEntity[] userEntities = mapFromJson(userContent, UserEntity[].class);
        assertTrue(userEntities.length > 0);
    }

    @Test
    public void testFindAllAsUser() throws Exception {
        String tokenUri = "/oauth/token?grant_type=password&username=user1&password=123";
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(tokenUri)
                .with(httpBasic("my-client-id", "secret"))
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        int status = result.getResponse().getStatus();
        assertEquals(200, status);

        String tokenContent = result.getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(tokenContent);
        String accessToken = jsonObject.getString("access_token");

        result = mvc.perform(MockMvcRequestBuilders.get("/secured/findall")
                .header("Authorization", "Bearer " + accessToken)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        status = result.getResponse().getStatus();
        assertEquals(403, status);
    }


}
