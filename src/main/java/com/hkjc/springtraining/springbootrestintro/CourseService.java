package com.hkjc.springtraining.springbootrestintro;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService {

    private ArrayList<Course> courses;

    public CourseService(){
        courses = new ArrayList<>();
    }

    public List<Course> getCourses() {
        return courses;
    }

    public Course createCourse(Course course) {
        course.setId(courses.size() + 1);
        courses.add(course);
        return course;
    }
}
