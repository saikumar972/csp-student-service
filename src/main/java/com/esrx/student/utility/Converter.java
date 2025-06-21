package com.esrx.student.utility;

import com.esrx.student.dto.StudentDto;
import com.esrx.student.entity.StudentEntity;
import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.stream.Collectors;

@UtilityClass
public class Converter {
    public StudentDto convertEntityToDto(StudentEntity studentEntity){
        StudentDto studentDto = new StudentDto();
        studentDto.setId(studentEntity.getId());
        studentDto.setName(studentEntity.getName());
        studentDto.setSubjects(Arrays.stream(studentEntity.getSubjects().split(",")).toList());
        studentDto.setJoiningDate(studentEntity.getJoiningDate());
        studentDto.setFees(studentEntity.getFees());
        studentDto.setCourse(studentEntity.getCourse());
        return studentDto;
    }

    public StudentEntity convertDtoToEntity(StudentDto studentDto){
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setName(studentDto.getName());
        studentEntity.setSubjects(String.join(",", studentDto.getSubjects()));
        studentEntity.setJoiningDate(studentDto.getJoiningDate());
        studentEntity.setFees(studentDto.getFees());
        studentEntity.setCourse(studentDto.getCourse());
        return studentEntity;
    }
}
