package com.hkjc.springtraining.springbootrestintro;

import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private List<Course> courses;

    public CourseController(){
        courses = new ArrayList<Course>();
        courses.add(new Course(1,"SpringBoot",2));
        courses.add(new Course(2, "SpringFramework", 3));
    }

    @GetMapping
    public Collection<Course> getCourses(){
        return courses;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createCourse(@RequestBody Course course) {
        if(course != null && ObjectUtils.isEmpty(course.getName()))
            throw new BadCourseRequestException("Course name cannot be empty");

        courses.add(course);
    }
}
