package com.springboot.restProject.Student.Management.controllers;

import com.springboot.restProject.Student.Management.models.Department;
import com.springboot.restProject.Student.Management.models.Student;
import com.springboot.restProject.Student.Management.repositories.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DepartmentController
{
    @Autowired
    private DepartmentRepository rep;
    @GetMapping("/departments")
    public List<Department> show()
    {
        return rep.findAll();
    }

    @GetMapping("/departments/{id}")
    public Department showByID(@PathVariable Integer id)
    {
        return rep.getOne(id);
    }

    @PostMapping("/departments")
    public Department create(@RequestBody Department s)
    {
        return rep.saveAndFlush(s);
    }

    @PutMapping("/departments/{id}")
    public Department update(@PathVariable Integer id,@RequestBody Department s)
    {
        return rep.save(s);
    }

    @DeleteMapping("/departments/{id}")
    public void delete(@PathVariable Integer id)
    {
        rep.deleteById(id);
    }
}
