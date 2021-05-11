package com.hkjc.springtraining.springbootrestintro;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/courses")
public class CourseController {

    private CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @Operation(summary = "Get All Courses")
    @ApiResponse(responseCode = "200", description = "All courses returned")
    @GetMapping
    public List<Course> getCourses() {
        return this.courseService.getAllCourses();
    }

//    @PostMapping
//    public ResponseEntity<Course> createCourse(@RequestBody Course course){
//        if(course != null && ObjectUtils.isEmpty(course.getName()))
//            return ResponseEntity.badRequest().build();
//
//        Course newCourse = courseService.createCourse(course);
//
//        return ResponseEntity.status(201).body(newCourse);
//    }

//  1. ResponseEntity
//    2. Remove status (201) with @ResponseStatus
//    3. Introduce Exception
//    4. Validate

    //MethodArgumentNotValidException
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Course createCourse(@Valid @RequestBody Course course) {
//        if(course != null && ObjectUtils.isEmpty(course.getName()))
//            throw new BadCourseRequestException("Course name cannot be empty");

        Course newCourse = courseService.createCourse(course);

        if(true)
            throw new CourseNotFoundException("Course 1 not found");

        return course;
    }

    @GetMapping("/{id}")
    public Course getCourse(@Parameter(name = "id", description = "Enter course id")  @PathVariable("id") Integer id) {
        return courseService.getCourse(id);
    }

    @PutMapping("/{id}")
    public Course updateCourse( @PathVariable("id") Integer id, @Valid @RequestBody Course course) {
        return courseService.updateCourse(course);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCourse(@PathVariable("id") Integer id) {
        courseService.deleteCourse(id);
    }
}
