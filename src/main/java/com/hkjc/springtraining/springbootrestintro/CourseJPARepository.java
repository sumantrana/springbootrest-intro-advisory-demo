package com.hkjc.springtraining.springbootrestintro;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseJPARepository extends JpaRepository<CourseEntity, Integer> {

    List<CourseEntity> findByNameAndDuration(String name, Integer duration);

    List<CourseEntity> findByNameLike(String name);

    List<CourseEntity> findByDurationBetween(Integer start, Integer end);

    @Query("SELECT c from CourseEntity c where c.duration = ?1 ")
    List<CourseEntity> findSpecificDuration(Integer duration);
}
