package com.hkjc.springtraining.springbootrestintro;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Example;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DataJpaTest
@Sql("/data.sql")
public class CourseRepositoryTests {

    @Autowired
    CourseJPARepository courseJPARepository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    public void saveCourse_WillSaveCourse_AndReturn_SavedCourse(){

        CourseEntity courseEntity = CourseEntity.builder()
                .name("SpringBoot")
                .duration(10)
                .build();

        courseJPARepository.save(courseEntity);

        CourseEntity emCourseEntity = entityManager.find(CourseEntity.class, 1);

        assertThat(emCourseEntity.getId(), is(1));
        assertThat(emCourseEntity, samePropertyValuesAs(courseEntity, "id"));
    }

    @Test
    public void findByNameAndDuration_WillReturn_AllCoursesWithNameAndDuration(){

        List<CourseEntity> courseEntityList = courseJPARepository.findByNameAndDuration("Spring Boot", 10);

        assertThat(courseEntityList, hasSize(1));
    }

    @Test
    public void findByNameLike_WillReturn_AllCoursesMatchingName(){

        List<CourseEntity> courseEntityList = courseJPARepository.findByNameLike("Spring Boot%");

        assertThat(courseEntityList, hasSize(9));
    }

    @Test
    public void findByDurationBetween_WillReturn_AllCoursesMatchingDuration(){

        List<CourseEntity> courseEntityList = courseJPARepository.findByDurationBetween(10, 15);

        assertThat(courseEntityList, hasSize(6));
    }

    @Test
    public void findByQuery_WillReturn_AllCoursesMatchingQuery(){

        List<CourseEntity> courseEntityList = courseJPARepository.findSpecificDuration(16);

        assertThat(courseEntityList, hasSize(3));
    }

    @Test
    public void findByExample_WillReturn_AllCoursesMatchingExample(){

        CourseEntity courseEntity = CourseEntity.builder()
                .name("Spring Boot4")
                .build();

        List<CourseEntity> courseEntityList = courseJPARepository.findAll(Example.of(courseEntity));

        assertThat(courseEntityList, hasSize(3));
    }
}
