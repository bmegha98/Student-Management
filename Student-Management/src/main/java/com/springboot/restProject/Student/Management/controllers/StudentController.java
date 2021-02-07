package com.springboot.restProject.Student.Management.controllers;

import com.springboot.restProject.Student.Management.exceptions.ResourceNotFoundException;
import com.springboot.restProject.Student.Management.models.entities.Department;
import com.springboot.restProject.Student.Management.models.entities.Student;
import com.springboot.restProject.Student.Management.models.requests.RequestClass;
import com.springboot.restProject.Student.Management.repositories.DepartmentRepository;
import com.springboot.restProject.Student.Management.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
public class StudentController
{
    @Autowired
    private StudentRepository rep;

    @Autowired
    private DepartmentRepository drep;

    @Autowired
    private RequestClass rc;

    @GetMapping("/students")
    public List<Student> get()
    {
        return rep.findAll();
    }
    @GetMapping("/department/{departmentId}/students")
    public List<Student> getbyDepartment(@PathVariable Integer departmentId)  {
        Optional<Department> d = drep.findById(departmentId);
        if(d.isPresent())
            return rep.findAllByDept(d.get());
        else
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Department not found");
    }

    @GetMapping("/students/{id}")
    public Student showByID(@PathVariable Integer id)  {
        Optional<Student> s= rep.findById(id);
        if(s.isPresent())
            return s.get();
        else
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Student doesn't exist");
    }

    @PostMapping("/students")
    @ResponseStatus(HttpStatus.CREATED)
    public Student create(@RequestBody RequestClass s) {

        try {
            Department d = drep.findById(s.getDnum()).orElseThrow(
                    () -> new ResourceNotFoundException("Department " + s.getDnum() + "not found :("));
            Student newStudent = new Student(s.getId(),s.getName(),d);
            return rep.save(newStudent);
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/students/{id}")
    public Student update(@PathVariable Integer id,@RequestBody RequestClass s)
    {
        try
        {
            Department d = drep.findById(s.getDnum()).orElseThrow(
                    () -> new ResourceNotFoundException("Department not found :("));

            Student stu = rep.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("Student " + id+" not found :("));

            stu.setName(s.getName());
            stu.setId(s.getId());
            stu.setDept(d);
            return rep.save(stu);
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/students/{id}")
    public void delete(@PathVariable Integer id) {
        try
        {
            Student stu = rep.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("Student " + id+"not found :("));
            rep.deleteById(id);
        }
        catch (ResourceNotFoundException e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }
}
