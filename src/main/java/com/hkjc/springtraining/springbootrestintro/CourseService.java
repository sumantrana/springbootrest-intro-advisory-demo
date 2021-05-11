package com.hkjc.springtraining.springbootrestintro;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CourseService {

    private CourseRepository repository;

    public CourseService(CourseRepository repository) {
        this.repository = repository;
    }

    public List<Course> getAllCourses() {
        return Collections.emptyList();
    }

    public Course createCourse(Course course) {
        return null;
    }

    public Course getCourse(int i) {
        return null;
    }

    public Course updateCourse(Course course) {
        return null;
    }

    public Object deleteCourse(Integer id) {
        return null;
    }
}
