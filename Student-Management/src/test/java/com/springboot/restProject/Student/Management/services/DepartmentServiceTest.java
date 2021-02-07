package com.springboot.restProject.Student.Management.services;

import com.springboot.restProject.Student.Management.exceptions.ResourceNotFoundException;
import com.springboot.restProject.Student.Management.models.entities.Department;
import com.springboot.restProject.Student.Management.models.entities.Student;
import com.springboot.restProject.Student.Management.repositories.DepartmentRepository;
import com.springboot.restProject.Student.Management.repositories.StudentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class DepartmentServiceTest {

    @InjectMocks
    private DepartmentService dservice;

    @Mock
    private DepartmentRepository drep;

    @Mock
    private StudentRepository srep;

    @Test
    public void TestWhenDepartmentNotFound()
    {
        Mockito.when(drep.findById(1)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,() -> dservice.deleteDepartment(1,Mockito.any(Integer.class)));
    }
}