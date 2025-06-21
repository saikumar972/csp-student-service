package com.esrx.student.controller;

import com.esrx.student.dto.StudentDto;
import com.esrx.student.service.StudentService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentService studentService;

    @PostMapping("/add")
    public StudentDto addStudent(@RequestBody @Valid StudentDto studentDto){
        return studentService.addStudent(studentDto);
    }

    @PutMapping("/update")
    public StudentDto updateStudentDetails(@RequestBody @Valid StudentDto studentDto){
        return studentService.updateStudentDetails(studentDto);
    }

    @GetMapping("/id/{id}")
    public StudentDto getStudentDetailsById(@PathVariable Long id){
        return studentService.getStudentDetailsById(id);
    }

    @GetMapping("/add")
    public List<StudentDto> studentList(){
        return studentService.getStudentDetails();
    }

    @DeleteMapping("/id/{id}")
    public String deleteStudentById(@PathVariable Long id){
        return studentService.deleteStudentById(id);
    }
}
