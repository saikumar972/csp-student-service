package com.esrx.student.util;

import lombok.experimental.UtilityClass;
import org.springframework.core.io.ClassPathResource;

import java.nio.file.Files;

@UtilityClass
public class JsonConverter {
    public String convertToJson(String jsonFilePath) throws Exception{
        ClassPathResource classPathResource=new ClassPathResource(jsonFilePath);
        return new String(Files.readAllBytes(classPathResource.getFile().toPath()));
    }
}
