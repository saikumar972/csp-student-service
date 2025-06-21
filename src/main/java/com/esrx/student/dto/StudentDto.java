package com.esrx.student.dto;

import com.esrx.student.utility.CourseValidation;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank(message = "{student.name.notblank}")
    @Size(min = 1, max = 30, message = "{student.name.size}")
    private String name;

    @NotEmpty(message = "{student.subjects.notempty}")
    private List<String> subjects;

    @PastOrPresent(message = "{student.joiningDate.pastorpresent}")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate joiningDate;

    @Min(value = 2000, message = "{student.fees.min}")
    @Max(value = 10000, message = "{student.fees.max}")
    private double fees;

    @CourseValidation
    private String course;

}