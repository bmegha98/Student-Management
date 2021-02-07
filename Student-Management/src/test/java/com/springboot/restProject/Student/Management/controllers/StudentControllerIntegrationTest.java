package com.springboot.restProject.Student.Management.controllers;

import com.springboot.restProject.Student.Management.models.entities.Department;
import com.springboot.restProject.Student.Management.models.entities.Student;
import com.springboot.restProject.Student.Management.models.requests.RequestClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc

public class StudentControllerIntegrationTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    public void TestGetAll()
    {
        ResponseEntity<List> responseEntity = this.restTemplate.getForEntity("/students", List.class);
        assertThat(responseEntity.getStatusCode(),equalTo(HttpStatus.OK));
    }

    @Test
    public void TestShowByID()
    {
        Department d = new Department();
        d.setDname("Electrical");
        d.setDlocation("Rome");
        d.setDnumber(8);

        Student s = new Student();
        s.setId(8);
        s.setName("David");
        s.setDept(d);

        ResponseEntity<Student> responseEntity = this.restTemplate.getForEntity("/students/{id}",Student.class,s.getId());
        assertThat(responseEntity.getStatusCode(),equalTo(HttpStatus.OK));
        assertThat(responseEntity.getBody().getName(),equalTo(s.getName()));
        assertThat(responseEntity.getBody().getDept(),equalTo(s.getDept()));
    }


    @Test
    public void TestGetByDepartment()
    {
        ResponseEntity<List> responseEntity = this.restTemplate.getForEntity("/department/{departmentId}/students",List.class,8);
        assertThat(responseEntity.getBody().size(),equalTo(6));
        assertThat(responseEntity.getStatusCode(),equalTo(HttpStatus.OK));
    }

    @Test
    public void TestCreate()
    {
        Department d = new Department();
        d.setDnumber(5);
        d.setDlocation("Berlin");
        d.setDname("Grooming");

        RequestClass s = new RequestClass();
        s.setId(10);
        s.setName("Richard");
        s.setDnum(5);

        ResponseEntity<Student> responseEntity = this.restTemplate.postForEntity("/students",s,Student.class);
        assertThat(responseEntity.getStatusCode(),equalTo(HttpStatus.CREATED));
        assertThat(responseEntity.getBody().getDept(),equalTo(d));
        assertThat(responseEntity.getBody().getName(),equalTo(s.getName()));
    }

    @Test
    public void TestUpdate()
    {
        RequestClass s = new RequestClass();
        s.setId(10);
        s.setName("Richard");
        s.setDnum(6);

        this.restTemplate.put("/students/{id}",s,10);

    }

    @Test
    public void TestDelete()
    {
        int id = 9;
        this.restTemplate.delete("/students/{id}",id);
    }



}