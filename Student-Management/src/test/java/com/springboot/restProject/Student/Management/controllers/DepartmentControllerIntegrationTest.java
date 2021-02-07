package com.springboot.restProject.Student.Management.controllers;

import com.springboot.restProject.Student.Management.models.entities.Department;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class DepartmentControllerIntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void TestGetAll()
    {
        ResponseEntity<List> responseEntity = this.testRestTemplate.getForEntity("/departments",List.class);
        assertThat(responseEntity.getStatusCode(),equalTo(HttpStatus.OK));
    }

    @Test
    public void TestGetById()
    {
        int id = 6;
        ResponseEntity<Department> response = this.testRestTemplate.getForEntity("/departments/{id}",Department.class,id);
        assertThat(response.getStatusCode(),equalTo(HttpStatus.OK));
        assertThat(response.getBody().getDname(),equalTo("BMMMC"));
    }

    @Test
    public void TestUpdate()
    {
        Department d = new Department();
        d.setDnumber(6);
        d.setDname("Mass Media");
        d.setDlocation("Paris");
        this.testRestTemplate.put("/departments/{id}",d,d.getDnumber());
    }

    @Test
    public void TestDelete()
    {
        Map<String,String> params = new HashMap<>();
        params.put("sourceDept","8");
        params.put("targetDept","5");

        this.testRestTemplate.delete("/departments",params);
    }

}