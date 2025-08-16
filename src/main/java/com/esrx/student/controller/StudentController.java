package com.esrx.student.controller;

import com.esrx.student.dto.StudentDto;
import com.esrx.student.dto.StudentInput;
import com.esrx.student.service.StudentService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentService studentService;

    @PostMapping("/add")
    public ResponseEntity<StudentDto> addStudent(@RequestBody @Valid StudentDto studentDto){
        StudentDto studentDto1=studentService.addStudent(studentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(studentDto1);
    }

    @PutMapping("/update")
    public ResponseEntity<StudentDto>  updateStudentDetails(@RequestBody @Valid StudentDto studentDto){
        StudentDto studentDto1=studentService.updateStudentDetails(studentDto);
        return ResponseEntity.status(HttpStatus.OK).body(studentDto1);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<StudentDto> getStudentDetailsById(@PathVariable Long id){
        StudentDto studentDto1=studentService.getStudentDetailsById(id);
        return ResponseEntity.status(HttpStatus.OK).body(studentDto1);
    }

    @GetMapping("/all")
    public ResponseEntity<List<StudentDto>> studentList(){
        List<StudentDto> studentDtoList = studentService.getStudentDetails();
        return ResponseEntity.status(HttpStatus.OK).body(studentDtoList);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<String> deleteStudentById(@PathVariable Long id){
        String message=studentService.deleteStudentById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(message);
    }

    @PostMapping("/fetch")
    public ResponseEntity<StudentDto> getStudentDetailsByIdAndName(@RequestBody StudentInput studentInput){
        StudentDto studentDto=studentService.getStudentDetailsByNameAndId(studentInput);
        return ResponseEntity.status(HttpStatus.OK).body(studentDto);
    }
}
