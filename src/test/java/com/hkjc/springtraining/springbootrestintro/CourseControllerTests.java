package com.hkjc.springtraining.springbootrestintro;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CourseController.class)
public class CourseControllerTests {

    MockMvc mockMvc;

    @MockBean
    CourseService courseService;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    public void setup(){
        CourseController courseController = new CourseController(courseService);
        mockMvc = MockMvcBuilders.standaloneSetup(courseController).build();
    }

    @Test
    public void getCourses_WillReturn_200OK() throws Exception {

        mockMvc.perform(get("/courses"))
                .andExpect(status().isOk());

    }

    @Test
    public void getCourses_WillReturn_CourseList() throws Exception {

        List<Course> expectedCourses = new ArrayList<>();
        expectedCourses.add(new Course(1,"SpringBoot",2));
        expectedCourses.add(new Course(2, "SpringFramework", 3));

        given(courseService.getCourses()).willReturn(expectedCourses);

        mockMvc.perform( get("/courses"))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("SpringBoot")))
                .andExpect(jsonPath("$[0].duration", is(2)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("SpringFramework")))
                .andExpect(jsonPath("$[1].duration", is(3)));

    }


    @Test
    public void postCourse_WillReturn_201() throws Exception {

        Course course = Course.builder()
                .name("Spring Boot")
                .duration(10)
                .build();

        Course courseWithId = Course.builder()
                .id(1)
                .name("Spring Boot")
                .duration(10)
                .build();

        given(courseService.createCourse(course)).willReturn(courseWithId);

        mockMvc.perform(post("/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(course)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Spring Boot")))
                .andExpect(jsonPath("$.duration", is(10)));

    }

    @Test
    public void postCourse_WithIncompleteData_WillReturn_400() throws Exception {

        Course course = Course.builder()
                .duration(10)
                .build();

        mockMvc.perform(post("/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(course)))
                .andExpect(status().isBadRequest());
    }




}
