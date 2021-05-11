package com.hkjc.springtraining.springbootrestintro;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class CourseControllerTests {

    public static final String URL_BASE_PATH = "/api/v1";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CourseService courseService;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    public void init(){
        CourseController courseController = new CourseController(courseService);
        mockMvc = MockMvcBuilders.standaloneSetup(courseController).build();
    }

    @Test
    public void getCourses_WillReturn_200OK() throws Exception {

        mockMvc.perform(get(URL_BASE_PATH + "/courses"))
                .andExpect(status().isOk());

    }

    @Test
    public void getCourses_WillReturn_AllCourses() throws Exception {

        List<Course> courseList = Arrays.asList(new Course(1, "Spring Boot", 10),
                                                new Course(2, "Spring Boot Advanced", 15));

        given(courseService.getAllCourses()).willReturn(courseList);

        mockMvc.perform(get(URL_BASE_PATH + "/courses"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(notNullValue())))
                .andExpect(jsonPath("$[0].name", is("Spring Boot")))
                .andExpect(jsonPath("$[0].duration", is(10)))
                .andExpect(jsonPath("$[1].id", is(notNullValue())))
                .andExpect(jsonPath("$[1].name", is("Spring Boot Advanced")))
                .andExpect(jsonPath("$[1].duration", is(15)));

    }

    @Test
    public void postCourse_WillCreateCourse_AndReturn201Created() throws Exception {

        //Arrange
        Course course = Course.builder()
                .id(1)
                .name("Spring Boot")
                .duration(10)
                .build();

        given(courseService.createCourse(course)).willReturn(course);

        //Act & Assert
        mockMvc.perform(post(URL_BASE_PATH + "/courses")
                .contentType(MediaType.APPLICATION_JSON)
                 .content(objectMapper.writeValueAsString(course)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andExpect(jsonPath("$.name", is("Spring Boot")))
                .andExpect(jsonPath("$.duration", is(10)));

        verify(courseService, times(1)).createCourse(course);
    }

    @Test
    public void postCourse_WithIncompleteInformation_WillReturn_400BadRequest() throws Exception{

        //Arrange
        Course course = Course.builder()
                .duration(10)
                .build();

        //Act & Assert
        mockMvc.perform(post(URL_BASE_PATH + "/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(course)))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void getCourse_WillReturn_Course() throws Exception{

        //Arrange
        Course course = Course.builder()
                .id(1)
                .name("Spring Boot")
                .duration(2)
                .build();

        given(courseService.getCourse(1)).willReturn(course);

        mockMvc.perform(get(URL_BASE_PATH + "/courses/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Spring Boot")))
                .andExpect(jsonPath("$.duration", is(2)));

        verify(courseService, times(1)).getCourse(1);

    }

    @Test
    public void getCourse_WithIncorrectId_WillReturn_404NotFound() throws Exception {

        given(courseService.getCourse(2)).willThrow(new CourseNotFoundException("Course not found for id: 2"));

        mockMvc.perform(get(URL_BASE_PATH + "/courses/2"))
                .andExpect(status().isNotFound());

        verify(courseService, times(1)).getCourse(2);

    }

    @Test
    public void putCourse_WillUpdate_AndReturnCourse() throws Exception{

        //Arrange
        Course course = Course.builder()
                .id(1)
                .name("Spring Boot Updated")
                .duration(12)
                .build();

        given(courseService.updateCourse(course)).willReturn(course);

        mockMvc.perform(put(URL_BASE_PATH + "/courses/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(course)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Spring Boot Updated")))
                .andExpect(jsonPath("$.duration", is(12)));

        verify(courseService, times(1)).updateCourse(course);

    }

    @Test
    public void putCourse_WithInvalidId_WillReturn_404NotFound() throws Exception{

        Course course = Course.builder()
                .id(100)
                .name("Spring Boot Updated")
                .duration(12)
                .build();

        given(courseService.updateCourse(course))
                .willThrow(new CourseNotFoundException("Course not found for id: 100"));

        mockMvc.perform(put(URL_BASE_PATH + "/courses/100")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(course)))
                .andExpect(status().isNotFound());

        verify(courseService, times(1)).updateCourse(course);

    }

    @Test
    public void deleteCourse_WillReturn_204NoContent() throws Exception {

        Course course = Course.builder()
                .id(100)
                .name("Spring Boot Updated")
                .duration(12)
                .build();

        given(courseService.deleteCourse(1)).willReturn(course);

        mockMvc.perform(delete(URL_BASE_PATH + "/courses/1"))
                .andExpect(status().isNoContent());

        verify(courseService, times(1)).deleteCourse(1);
    }

    @Test
    public void deleteCourse_WithInvalidId_WillReturn_404NotFound() throws Exception{

        given(courseService.deleteCourse(100))
                .willThrow(new CourseNotFoundException("Course not found for id: 100"));

        mockMvc.perform(delete(URL_BASE_PATH + "/courses/100"))
                .andExpect(status().isNotFound());

        verify(courseService, times(1)).deleteCourse(100);

    }



}
