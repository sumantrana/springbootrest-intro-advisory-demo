package com.hkjc.springtraining.springbootrestintro;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

public class CourseServiceTests {

    @Mock
    CourseJPARepository courseJPARepository;

    private CourseService courseService;

    @BeforeEach
    public void setup(){
        openMocks(this);
        courseService = new CourseService(courseJPARepository);
    }

    @Test
    public void getAllCourses_WillReturn_AllCourses(){

        List<Course> courseList = Arrays.asList(new Course(1, "Spring Boot", 10),
                new Course(2, "Spring Boot Advanced", 15));

        List<CourseEntity> courseEntityList = Arrays.asList(new CourseEntity(1, "Spring Boot", 10),
                new CourseEntity(2, "Spring Boot Advanced", 15));

        given(courseJPARepository.findAll()).willReturn(courseEntityList);

        List<Course> fetchedCourseList = courseService.getAllCourses();

        verify(courseJPARepository, times(1)).findAll();
        assertThat(fetchedCourseList, is(courseList));
    }

    @Test
    public void createCourse_WillCreate_CourseEntity(){

        Course course = Course.builder()
                .name("Spring Boot")
                .duration(10)
                .build();

        CourseEntity rawCourseEntity = CourseEntity.builder()
                .name("Spring Boot")
                .duration(10)
                .build();

        CourseEntity createdCourseEntity = CourseEntity.builder()
                .id(1)
                .name("Spring Boot")
                .duration(10)
                .build();

        given(courseJPARepository.save(rawCourseEntity)).willReturn(createdCourseEntity);

        Course savedCourse = courseService.createCourse(course);

        verify(courseJPARepository, times(1)).save(rawCourseEntity);

        assertThat(savedCourse.getId(), is(1));
        assertThat(savedCourse, samePropertyValuesAs(course, "id"));

    }
}
