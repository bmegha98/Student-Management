package com.springboot.restProject.Student.Management.controllers;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.restProject.Student.Management.exceptions.ResourceNotFoundException;
import com.springboot.restProject.Student.Management.models.entities.Department;
import com.springboot.restProject.Student.Management.models.entities.Student;
import com.springboot.restProject.Student.Management.repositories.DepartmentRepository;
import com.springboot.restProject.Student.Management.repositories.StudentRepository;
import com.springboot.restProject.Student.Management.services.DepartmentService;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.lang.reflect.Array;
import java.util.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DepartmentControllerTest
{

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartmentService dservice;

    @MockBean
    private DepartmentRepository drep;

    @MockBean
    private StudentRepository rep;

    @Test
    public void TestGetAllDepartments() throws Exception {
        Department d = new Department();
        d.setDnumber(12);
        d.setDlocation("London");
        d.setDname("Civil");
        List<Department> departmentList = Arrays.asList(d);
        Mockito.when(drep.findAll()).thenReturn(departmentList);
        mockMvc.perform(get("/departments").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(1)))
                .andExpect(jsonPath("$[0].dname",is(d.getDname())));
        verify(drep,times(1)).findAll();
        verifyNoMoreInteractions(drep);
    }

    @Test
    public void TestGetDepartmentById() throws Exception {
        Department d = new Department();
        d.setDnumber(12);
        d.setDlocation("London");
        d.setDname("Civil");
        Mockito.when(drep.findById(d.getDnumber())).thenReturn(java.util.Optional.of(d));

        mockMvc.perform(get("/departments/{id}",d.getDnumber()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dnumber",is(d.getDnumber())))
                .andExpect(jsonPath("$.dlocation",is(d.getDlocation())));
        verify(drep,times(1)).findById(d.getDnumber());
        verifyNoMoreInteractions(drep);
    }

    @Test
    public void TestCreateDepartment() throws Exception {
        Department d = new Department();
        d.setDnumber(12);
        d.setDlocation("London");
        d.setDname("Civil");
        Mockito.when(drep.save(Mockito.any(Department.class))).thenReturn(d);
        mockMvc.perform(post("/departments").contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(d)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.dnumber",is(d.getDnumber())))
                .andExpect(jsonPath("$.dlocation",is(d.getDlocation())));
        verify(drep,times(1)).save(Mockito.any(Department.class));
        verifyNoMoreInteractions(drep);
    }

    @Test
    public void TestUpdateDepartment() throws Exception {
        Department d = new Department();
        d.setDnumber(12);
        d.setDlocation("London");
        d.setDname("Civil");

        Department updatedDepartment = new Department();
        updatedDepartment.setDnumber(8);
        updatedDepartment.setDlocation("LA");
        updatedDepartment.setDname("Maths");

        Mockito.when(drep.findById(d.getDnumber())).thenReturn(java.util.Optional.of(d));
        Mockito.when(drep.save(Mockito.any(Department.class))).thenReturn(updatedDepartment);

        mockMvc.perform(put("/departments/{id}",d.getDnumber()).contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(d)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dnumber",is(updatedDepartment.getDnumber())))
                .andExpect(jsonPath("$.dlocation",is(updatedDepartment.getDlocation())));

        verify(drep,times(1)).findById(d.getDnumber());
        verify(drep,times(1)).save(Mockito.any(Department.class));
        verifyNoMoreInteractions(drep);
    }


    @Test
    public void TestDeleteDepartment() throws Exception {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.put("sourceDept", Collections.singletonList("1"));
        parameters.put("targetDept", Collections.singletonList("5"));

        Mockito.when(dservice.deleteDepartment(Mockito.any(Integer.class),Mockito.any(Integer.class))).thenReturn(true);
        mockMvc.perform(delete("/departments").contentType(MediaType.APPLICATION_JSON)
        .params(parameters))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",is(true)));

        verify(dservice,times(1)).deleteDepartment(Mockito.any(Integer.class),Mockito.any(Integer.class));
        verifyNoMoreInteractions(dservice);
    }



    @Test
    public void TestDepartmentMigration() throws Exception {
        Department srcDept = new Department();
        srcDept.setDnumber(1);
        srcDept.setDlocation("London");
        srcDept.setDname("Civil");

        Department targetDept = new Department();
        targetDept.setDnumber(5);
        targetDept.setDlocation("LA");
        targetDept.setDname("Maths");

        Student s = new Student();
        s.setId(2);
        s.setName("Carl");
        s.setDept(srcDept);
        List<Student> studentList = Arrays.asList(s);

        Student updatedStudent = new Student();
        updatedStudent.setId(2);
        updatedStudent.setName("Carl");
        updatedStudent.setDept(targetDept);

        Mockito.when(drep.findById(srcDept.getDnumber())).thenReturn(java.util.Optional.of(srcDept));
        Mockito.when(drep.findById(targetDept.getDnumber())).thenReturn(Optional.of(targetDept));
        Mockito.when(rep.findAllByDept(srcDept)).thenReturn(studentList);
        Mockito.when(rep.save(Mockito.any(Student.class))).thenReturn(updatedStudent);

        mockMvc.perform(delete("/departments").contentType(MediaType.APPLICATION_JSON)
                .param("sourceDept","1").param("targetDept","5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",is(1)));

    }

}