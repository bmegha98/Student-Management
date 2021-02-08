package com.springboot.restProject.Student.Management.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.restProject.Student.Management.models.entities.Department;
import com.springboot.restProject.Student.Management.models.entities.Student;
import com.springboot.restProject.Student.Management.models.requests.RequestClass;
import com.springboot.restProject.Student.Management.repositories.DepartmentRepository;
import com.springboot.restProject.Student.Management.repositories.StudentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(StudentController.class)
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository rep;

    @MockBean
    private DepartmentRepository drep;

    @MockBean
    private RequestClass rc;

    @Test
    public void TestshowByID() throws Exception {

        Student s = new Student();
        s.setId(1);
        s.setName("Richard");

        //$ indicates root node of json
        when(rep.findById(s.getId())).thenReturn(Optional.of(s));

        mockMvc.perform(get("/students/{id}",s.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is(s.getName())));
        verify(rep,times(1)).findById(s.getId());
        verifyNoMoreInteractions(rep);
    }

    @Test
    public void TestgetAll() throws Exception {
        Student s = new Student();
        s.setId(8);
        s.setName("John");

        List<Student> l = Arrays.asList(s);
        when(rep.findAll()).thenReturn(l);

        mockMvc.perform(get("/students").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(1)))
                .andExpect(jsonPath("$[0].name",is(s.getName())));

        verify(rep,times(1)).findAll();
        verifyNoMoreInteractions(rep);
    }

    @Test
    public void TestCreate() throws Exception {
        RequestClass r = new RequestClass();
        r.setId(9);
        r.setName("Gunther");
        r.setDnum(5);

        Student s = new Student();
        s.setId(r.getId());
        s.setName(r.getName());

        Department d = new Department();
        d.setDnumber(4);
        d.setDname("Music");
        d.setDlocation("Mumbai");

        when(drep.findById(r.getDnum())).thenReturn(Optional.of(d));
        when(rep.save(Mockito.any(Student.class))).thenReturn(s);
        mockMvc.perform(post("/students").contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(r)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name",is(s.getName())))
                .andExpect(jsonPath("$.id",is(s.getId())));

        verify(rep,times(1)).save(Mockito.any(Student.class));
        verify(drep,times(1)).findById(r.getDnum());
        verifyNoMoreInteractions(rep);
        verifyNoMoreInteractions(drep);

    }

    @Test
    public void TestUpdate() throws Exception {
        RequestClass r = new RequestClass();
        r.setId(9);
        r.setName("Gunther");
        r.setDnum(5);

        Student s = new Student();
        s.setId(r.getId());
        s.setName(r.getName());

        Department d = new Department();
        d.setDnumber(5);
        d.setDname("Sports");
        d.setDlocation("Mumbai");

        Student updatedStudent = new Student();
        updatedStudent.setId(10);
        updatedStudent.setName("Prince");
        updatedStudent.setDept(d);

        when(drep.findById(r.getDnum())).thenReturn(Optional.of(d));
        when(rep.findById(s.getId())).thenReturn(Optional.of(s));
        when(rep.save(Mockito.any(Student.class))).thenReturn(updatedStudent);

        mockMvc.perform(put("/students/{id}",s.getId()).contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(r)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is(updatedStudent.getName())))
                .andExpect(jsonPath("$.id",is(updatedStudent.getId())));

        verify(drep,times(1)).findById(r.getDnum());
        verify(rep,times(1)).findById(s.getId());
        verify(rep,times(1)).save(Mockito.any(Student.class));
        verifyNoMoreInteractions(rep);
        verifyNoMoreInteractions(drep);
    }

    @Test
    public void testDelete() throws Exception {
        Student s = new Student();
        s.setId(2);
        s.setName("Carl");

        Mockito.when(rep.findById(s.getId())).thenReturn(Optional.of(s));
        mockMvc.perform(delete("/students/{id}",s.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(rep,times(1)).findById(s.getId());
        verify(rep,times(1)).deleteById(s.getId());
        verifyNoMoreInteractions(rep);
    }

    @Test
    public void TestGetByDepartment() throws Exception {
        Student s = new Student();
        s.setId(2);
        s.setName("Carl");

        List<Student> studentList = Arrays.asList(s);

        Department d = new Department();
        d.setDnumber(5);
        d.setDname("Sports");
        d.setDlocation("Mumbai");

        Mockito.when(drep.findById(d.getDnumber())).thenReturn(Optional.of(d));
        Mockito.when(rep.findAllByDept(d)).thenReturn(studentList);

        mockMvc.perform(get("/department/{departmentId}/students",d.getDnumber()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(1)))
                .andExpect(jsonPath("$[0].name",is(s.getName())));

        verify(drep,times(1)).findById(d.getDnumber());
        verify(rep,times(1)).findAllByDept(d);
        verifyNoMoreInteractions(drep);
        verifyNoMoreInteractions(rep);
    }

    @Test
    public void TestDepartmentNotFound() throws Exception
    {
        Mockito.when(drep.findById(1)).thenReturn(Optional.empty());
        mockMvc.perform(get("/department/{departmentId}/students",1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult -> assertTrue(mvcResult.getResolvedException() instanceof ResponseStatusException));
    }
}