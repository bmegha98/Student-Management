package com.springboot.restProject.Student.Management.models.requests;

import com.springboot.restProject.Student.Management.exceptions.ResourceNotFoundException;
import com.springboot.restProject.Student.Management.models.entities.Student;
import com.springboot.restProject.Student.Management.services.StudentService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data @NoArgsConstructor @AllArgsConstructor
@Component
public class RequestClass
{
    private Integer id;
    private String name;
    private Integer dnum;

}
