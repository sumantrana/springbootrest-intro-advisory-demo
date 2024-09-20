package com.hkjc.springtraining.springbootrestintro;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    private Integer id;

    @NotEmpty
    private String name;

    @NotNull
    private Integer duration;

}
