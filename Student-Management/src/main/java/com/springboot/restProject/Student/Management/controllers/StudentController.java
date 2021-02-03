package com.springboot.restProject.Student.Management.controllers;

import com.springboot.restProject.Student.Management.exceptions.ResourceNotFoundException;
import com.springboot.restProject.Student.Management.models.entities.Student;
import com.springboot.restProject.Student.Management.models.requests.RequestClass;
import com.springboot.restProject.Student.Management.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentController
{
    //To implement Singleton java class
    @Autowired
    StudentService stu;

    @Autowired
    RequestClass rc;

    @GetMapping("/students")
    public List<Student> get()
    {
        return stu.showAll();
    }
    @GetMapping("/department/{departmentId}/students")
    public List<Student> getbyid(@PathVariable Integer departmentId) {
        return stu.showByDept(departmentId);
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<Student> showByID(@PathVariable Integer id) throws ResourceNotFoundException {
        return stu.showByID(id);
    }

    @PostMapping("/students")
    public ResponseEntity<Student> create(@RequestBody RequestClass s) throws ResourceNotFoundException {
        return stu.createStudent(s);
    }

    @PutMapping("/students/{id}")
    public ResponseEntity<Student> update(@PathVariable Integer id,@RequestBody RequestClass s) throws ResourceNotFoundException {
        return stu.updateStudent(id,s);
    }

    @DeleteMapping("/students/{id}")
    public void delete(@PathVariable Integer id) throws ResourceNotFoundException {
        stu.deleteStudent(id);
    }
}
