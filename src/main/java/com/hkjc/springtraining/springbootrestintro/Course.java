package com.hkjc.springtraining.springbootrestintro;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    private Integer id;
    private String name;
    private int duration;
}
