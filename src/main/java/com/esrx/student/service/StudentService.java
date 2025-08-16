package com.esrx.student.service;

import com.esrx.student.dao.StudentRepo;
import com.esrx.student.dto.StudentDto;
import com.esrx.student.dto.StudentInput;
import com.esrx.student.entity.StudentEntity;
import com.esrx.student.utility.Converter;
import com.esrx.student.utility.CustomStudentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    StudentRepo studentRepo;

    public StudentDto addStudent(StudentDto studentDto){
        StudentEntity studentEntity= studentRepo.save(Converter.convertDtoToEntity(studentDto));
        return Converter.convertEntityToDto(studentEntity);
    }

    public StudentDto updateStudentDetails(StudentDto studentDto){
        StudentEntity studentEntity=studentRepo.findById(studentDto.getId()).orElseThrow(()->new CustomStudentException(studentDto.getId()+" Student id is invalid"));
        studentEntity.setCourse(studentDto.getCourse());
        studentEntity.setName(studentDto.getName());
        studentEntity.setFees(studentDto.getFees());
        studentEntity.setJoiningDate(studentDto.getJoiningDate());
        studentEntity.setSubjects(String.join(",", studentDto.getSubjects()));
        studentRepo.save(studentEntity);
        return Converter.convertEntityToDto(studentEntity);
    }

    public StudentDto getStudentDetailsById(Long id){
        StudentEntity studentEntity=studentRepo.findById(id).orElseThrow(()->new CustomStudentException(id+" Student id is invalid"));
        return Converter.convertEntityToDto(studentEntity);
    }

    public List<StudentDto> getStudentDetails(){
        List<StudentEntity> studentEntityList=studentRepo.findAll();
        List<StudentDto> studentList=studentEntityList.stream().map(Converter::convertEntityToDto).toList();
        return studentList;
    }

    public String deleteStudentById(Long id){
        StudentEntity studentEntity=studentRepo.findById(id).orElseThrow(()->new CustomStudentException(id+" Student id is invalid"));
        studentRepo.deleteById(id);
        return "Student details with "+id+" is deleted successfully";
    }
    public StudentDto getStudentDetailsByNameAndId(StudentInput studentInput){
            StudentEntity studentEntity=studentRepo.findStudentByNameAndId(studentInput.getId(),studentInput.getName());
            if(studentEntity==null){
                throw new CustomStudentException(studentInput.getId()+" and "+ studentInput.getName()+" were invalid");
            }
            return Converter.convertEntityToDto(studentEntity);
    }
}
