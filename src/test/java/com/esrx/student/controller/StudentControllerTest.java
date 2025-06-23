package com.esrx.student.controller;

import com.esrx.student.dto.StudentDto;
import com.esrx.student.service.StudentService;
import com.esrx.student.util.JsonConverter;
import com.esrx.student.utility.InvalidStudentIdException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
public class StudentControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockitoBean
    StudentService studentService;
    private static final ObjectMapper objectMapper=new ObjectMapper();
    @Nested
    class AddStudentTests {
        @Test
        public void shouldCreateStudent_whenValidInputProvided() throws Exception{
            String fileName="StudentInputDetails.json";
            objectMapper.registerModule(new JavaTimeModule());
            String jsonInput= JsonConverter.convertToJson(fileName);
            StudentDto inputStudentDto=objectMapper.readValue(jsonInput, StudentDto.class);
            when(studentService.addStudent(inputStudentDto)).thenReturn(inputStudentDto);
            mockMvc.perform(MockMvcRequestBuilders.post("/student/add")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonInput))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.name").value(inputStudentDto.getName()));
        }
        @Test
        public void shouldReturnBadRequest_whenInvalidInputProvided() throws Exception{
            String fileName="StudentInvalidInput.json";
            objectMapper.registerModule(new JavaTimeModule());
            String jasonInput=JsonConverter.convertToJson(fileName);
            StudentDto inputStudent=objectMapper.readValue(jasonInput, StudentDto.class);
            mockMvc.perform(MockMvcRequestBuilders.post("/student/add")
                    .contentType(MediaType.APPLICATION_JSON).content(jasonInput))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.fees").value("Fees must not exceed 10000"));
        }
    }
    @Nested
    class GetStudentByIdTests {
        @Test
        public void shouldReturnStudentDetails_whenValidIdProvided() throws Exception{
            String fileName="StudentByIdData.json";
            objectMapper.registerModule((new JavaTimeModule()));
            long input=1;
            String jsonInput=JsonConverter.convertToJson(fileName);
            StudentDto outputStudent=objectMapper.readValue(jsonInput,StudentDto.class);
            when(studentService.getStudentDetailsById(input)).thenReturn(outputStudent);
            mockMvc.perform(MockMvcRequestBuilders.get("/student/id/"+input))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name").value("saikumar"));
        }

        @ParameterizedTest
        @ValueSource(longs = {0, -1, 999})
        public void shouldReturnBadRequest_whenInvalidIdProvided(long invalidInput) throws Exception{
            when(studentService.getStudentDetailsById(invalidInput)).thenThrow(new InvalidStudentIdException("student id is invalid"));
            mockMvc.perform(MockMvcRequestBuilders.get("/student/id/"+invalidInput))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string("student id is invalid"));
        }
    }


    @Test
    public void shouldReturnAllStudents_whenRequested()throws Exception{
        String fileName="studentList.json";
        objectMapper.registerModule(new JavaTimeModule());
        String jsonInput=JsonConverter.convertToJson(fileName);
        List<StudentDto> expectedOutput=objectMapper.readValue(jsonInput,new TypeReference<List<StudentDto>>() {});
        when(studentService.getStudentDetails()).thenReturn(expectedOutput);
        mockMvc.perform(MockMvcRequestBuilders.get("/student/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[1].name").value("sai"));
    }

    @Test
    public void shouldUpdateStudentDetails_whenValidInputProvided() throws Exception {
        String fileName="StudentInputDetails.json";
        objectMapper.registerModule(new JavaTimeModule());
        String jsonInput= JsonConverter.convertToJson(fileName);
        StudentDto inputStudentDto=objectMapper.readValue(jsonInput, StudentDto.class);
        when(studentService.updateStudentDetails(inputStudentDto)).thenReturn(inputStudentDto);
        mockMvc.perform(MockMvcRequestBuilders.put("/student/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInput))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(inputStudentDto.getName()))
                .andExpect(jsonPath("$.id").value(inputStudentDto.getId()))
                .andExpect(jsonPath("$.course").value(inputStudentDto.getCourse()));
    }
    @Nested
    class DeleteStudentTestcases{
        @Test
        public void shouldDeleteStudent_whenValidIdProvided() throws Exception {
            long input=1;
            String expectedMessage="Student details with "+input+" is deleted successfully";
            when(studentService.deleteStudentById(input)).thenReturn(expectedMessage);
            mockMvc.perform(MockMvcRequestBuilders.delete("/student/id/"+input))
                    .andExpect(status().isOk())
                    .andExpect(content().string(expectedMessage));

        }
        @Test
        public void shouldReturnBadRequest_whenDeletingInvalidStudentId() throws Exception {
            long invalidId = 999L;
            when(studentService.deleteStudentById(invalidId))
                    .thenThrow(new InvalidStudentIdException("student id is invalid"));

            mockMvc.perform(MockMvcRequestBuilders.delete("/student/id/" + invalidId))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string("student id is invalid"));
        }
    }


}
