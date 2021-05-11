package com.hkjc.springtraining.springbootrestintro;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;

public class CourseServiceTests {

    private CourseService courseService;

    @Mock
    CourseRepository courseRepository;

    @BeforeEach
    public void setup(){
        courseService = new CourseService(courseRepository);
    }

    @Test
    public void getCourses_WillReturn_AllCourses(){

                List<Course> courseList = Arrays.asList(new Course(1, "Spring Boot", 10),
                new Course(2, "Spring Boot Advanced", 15));

//        given(courseRepository.findAll()).willReturn(courseList);
//
//        List<Course> courseList = courseService.getAllCourses();
//
//        assertThat(courseList, hasSize(0));
    }

    @Test
    public void createCourse_WillReturn_CreatedCourse(){

        Course course = Course.builder()
                .name("Spring Boot")
                .duration(10)
                .build();

        Course createdCourse = courseService.createCourse(course);

        //assertThat(createdCourse, )
    }
}
