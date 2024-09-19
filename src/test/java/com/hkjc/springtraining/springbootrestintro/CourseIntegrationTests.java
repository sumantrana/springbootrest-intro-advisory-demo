package com.hkjc.springtraining.springbootrestintro;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("/data.sql")
public class CourseIntegrationTests {

    TestRestTemplate testRestTemplate = new TestRestTemplate();

    @LocalServerPort
    int port;

    @Test
    public void getAllCourses_WillReturn_AllCourses(){

        String baseURI = "http://localhost:" + port;

        Course[] courseArray = testRestTemplate.getForEntity(baseURI + "/api/v1/courses", Course[].class).getBody();

        assertThat(courseArray.length, is(9));
    }

}
