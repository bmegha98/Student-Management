package com.springboot.restProject.Student.Management.services;

import com.springboot.restProject.Student.Management.exceptions.ResourceNotFoundException;
import com.springboot.restProject.Student.Management.models.entities.Department;
import com.springboot.restProject.Student.Management.models.entities.Student;
import com.springboot.restProject.Student.Management.repositories.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
class StudentServiceTest {

    @InjectMocks
    StudentService service;

    @Mock
    StudentRepository rep;



    @Test
    void showByID() throws ResourceNotFoundException {
        Student actual = new Student(1,"Megha");


        Student expected = new Student(1,"Megha");


        Mockito.when(rep.findById(1)).thenReturn(java.util.Optional.of(expected));
        //given(rep.findById(1)).willReturn(java.util.Optional.of(expected));
        //assertEquals(actual.getId(),service.showByID(1).getBody().getId());
    }
}