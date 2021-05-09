package com.hkjc.springtraining.springbootrestintro;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CourseController.class)
public class CourseControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void getCourses_WillReturn_200OK() throws Exception {

        mockMvc.perform(get("/courses"))
                .andExpect(status().isOk());

    }

    @Test
    public void getCourses_WillReturn_CourseList() throws Exception {

        mockMvc.perform( get("/courses"))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("SpringBoot")))
                .andExpect(jsonPath("$[0].duration", is(2)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("SpringFramework")))
                .andExpect(jsonPath("$[1].duration", is(3)));

    }

    @Test
    public void postCreate_WillReturn_201() throws Exception {

        Course course = Course.builder()
                .name("Spring Boot")
                .duration(10)
                .build();

        mockMvc.perform(post("/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(course)))
                .andExpect(status().isCreated());

        mockMvc.perform( get("/courses"))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[2].name", is("Spring Boot")))
                .andExpect(jsonPath("$[2].duration", is(10)));
    }

    @Test
    public void postCreate_WithIncompleteData_WillReturn_400() throws Exception {

        Course course = Course.builder()
                .duration(10)
                .build();

        mockMvc.perform(post("/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(course)))
                .andExpect(status().isBadRequest());
    }

}
