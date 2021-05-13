package com.hkjc.springtraining.springbootrestintro;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {

    private CourseJPARepository courseJPARepository;

    public CourseService(CourseJPARepository courseJPARepository) {
        this.courseJPARepository = courseJPARepository;
    }

    public List<Course> getAllCourses() {
        List<CourseEntity> courseEntityList =  courseJPARepository.findAll();
        return courseEntityList.stream()
                .map(ce -> new Course(ce.getId() , ce.getName(), ce.getDuration()))
                .collect(Collectors.toList());
    }

    public Course createCourse(Course course) {
        CourseEntity savedEntity =
                courseJPARepository.save(new CourseEntity(course.getId(),
                        course.getName(), course.getDuration()));

        return Course.builder()
                .id(savedEntity.getId())
                .name(savedEntity.getName())
                .duration(savedEntity.getDuration())
                .build();
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
