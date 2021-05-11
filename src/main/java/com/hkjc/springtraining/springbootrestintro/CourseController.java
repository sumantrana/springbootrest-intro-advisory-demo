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

    private CourseService courseService;

    public CourseController(CourseService courseService){
        this.courseService = courseService;
    }

    @GetMapping
    public Collection<Course> getCourses(){
        return courseService.getCourses();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Course createCourse(@RequestBody Course course) {
        if(course != null && ObjectUtils.isEmpty(course.getName()))
            throw new BadCourseRequestException("Course name cannot be empty");

        return courseService.createCourse(course);
    }

    @PutMapping("/{id}")
    public Course updateCourse(){
        return null;
    }
}
