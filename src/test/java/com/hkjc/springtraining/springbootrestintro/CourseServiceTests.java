package com.hkjc.springtraining.springbootrestintro;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CourseServiceTests {

    CourseService courseService;

    @BeforeEach
    public void setup(){
        courseService = new CourseService();
    }

    @Test
    public void getCourses_WillReturn_AllCourses(){
        List<Course> courseList = courseService.getCourses();

        assertThat(courseList, is(empty()));
    }

    @Test
    public void createCourse_WillCreate_AndReturnCourse(){
        Course course = Course.builder()
                .name("Spring Boot")
                .duration(5)
                .build();

        Course savedCourse = courseService.createCourse(course);

        List<Course> courseList = courseService.getCourses();

        assertThat(savedCourse, samePropertyValuesAs(course, "id"));

        assertThat(courseList, hasSize(1));
        assertThat(courseList, hasItem(course));
    }

    @Test
    public void postCourse_WillGenerate_UniqueId(){

        Course course1 = Course.builder()
                .name("Spring Boot")
                .duration(10)
                .build();

        Course course2 = Course.builder()
                .id(2)
                .name("Spring Boot Advanced")
                .duration(15)
                .build();

        Course savedCourse1 = courseService.createCourse(course1);
        Course savedCourse2 = courseService.createCourse(course2);

        assertThat(savedCourse2.getId(), is(savedCourse1.getId() + 1));

    }
}
