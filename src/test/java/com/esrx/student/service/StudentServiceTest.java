package com.esrx.student.service;

import com.esrx.student.dao.StudentRepo;
import com.esrx.student.dto.StudentDto;
import com.esrx.student.entity.StudentEntity;
import com.esrx.student.utility.Converter;
import com.esrx.student.utility.InvalidStudentIdException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {
    @InjectMocks
    StudentService studentService;

    @MockitoBean
    StudentRepo studentRepo;

    private  List<StudentEntity> students;

    @BeforeEach
    public void setUp(){
        students=new ArrayList<>();
        StudentEntity student1 = new StudentEntity(1L, "Aarav Mehta", "maths,english,biology", LocalDate.of(2022, 6, 15), 1850, "science");
        StudentEntity student2 = new StudentEntity(2L, "Sneha Reddy", "physics,chemistry,maths", LocalDate.of(2021, 12, 10), 1900, "engineering");
        StudentEntity student3 = new StudentEntity(3L, "Karan Joshi", "commerce,economics,accountancy", LocalDate.of(2023, 2, 28), 2100, "commerce");
        students.add(student1);
        students.add(student2);
        students.add(student3);
    }

    @Test
    public void addStudentTest(){
        StudentEntity studentEntityInput=students.get(0);
        StudentDto studentDtoInput=Converter.convertEntityToDto(studentEntityInput);
        when(studentRepo.save(any(StudentEntity.class))).thenReturn(studentEntityInput);
        StudentDto studentOutput=studentService.addStudent(studentDtoInput);
        assertEquals("Aarav Mehta",studentOutput.getName());
        assertEquals(1L,studentOutput.getId());
    }

    @Test
    public void getStudentTest(){
        StudentEntity studentInput=students.get(0);
        when(studentRepo.findById(1L)).thenReturn(Optional.of(studentInput));
        StudentDto studentOutput=studentService.getStudentDetailsById(1L);
        assertEquals("Aarav Mehta",studentOutput.getName());
        assertEquals(1L, studentOutput.getId());
        assertEquals("science", studentOutput.getCourse());
    }

    @Nested
    class updateStudentTests {
        @Test
        public void updateStudent(){
            //arrange
            StudentEntity studentEntityInput=students.get(0);
            StudentDto studentDtoInput=Converter.convertEntityToDto(studentEntityInput);
            when(studentRepo.findById(1L)).thenReturn(Optional.of(studentEntityInput));
            when(studentRepo.save(any(StudentEntity.class))).thenReturn(studentEntityInput);
            //act
            StudentDto studentOutput=studentService.updateStudentDetails(studentDtoInput);
            //assert
            assertEquals("Aarav Mehta",studentOutput.getName());
            assertEquals(1L,studentOutput.getId());
        }

        @Test
        public void exceptionInUpdateTest() {
            StudentDto dto = new StudentDto();
            dto.setId(99L);
            when(studentRepo.findById(99L)).thenReturn(Optional.empty());
            assertThrows(InvalidStudentIdException.class,
                    () -> studentService.updateStudentDetails(dto));
        }
    }
    @Nested
    class DeleteTests{
        @Test
        public void deleteTest(){
            StudentEntity studentEntityInput=students.get(0);
            StudentDto studentDtoInput=Converter.convertEntityToDto(studentEntityInput);
            when(studentRepo.findById(1L)).thenReturn(Optional.of(studentEntityInput));
            // Mock deleteById (void method)
            doNothing().when(studentRepo).deleteById(1L);
            // Call the service method
            String result = studentService.deleteStudentById(1L);
            // Assert result
            assertEquals("Student details with 1 is deleted successfully", result);
        }

        @Test
        public void exceptionDeleteTest(){
            long id=99L;
            when(studentRepo.findById(id)).thenReturn(Optional.empty());
            assertThrows(InvalidStudentIdException.class,()->studentService.deleteStudentById(id));
        }
    }

    @Test
    public void getAllStudentsTest(){
        List<StudentDto> expected=students.stream().map(Converter::convertEntityToDto).toList();
        when(studentRepo.findAll()).thenReturn(students);
        List<StudentDto> actual=studentService.getStudentDetails();
        assertEquals(expected.size(),actual.size());
    }

}



