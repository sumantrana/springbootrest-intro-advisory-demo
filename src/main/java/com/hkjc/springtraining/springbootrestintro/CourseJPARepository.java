package com.hkjc.springtraining.springbootrestintro;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseJPARepository extends JpaRepository<CourseEntity, Integer> {
}
