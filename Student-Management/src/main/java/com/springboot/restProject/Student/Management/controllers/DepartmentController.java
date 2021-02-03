package com.springboot.restProject.Student.Management.controllers;

import com.springboot.restProject.Student.Management.exceptions.ResourceNotFoundException;
import com.springboot.restProject.Student.Management.models.Department;
import com.springboot.restProject.Student.Management.models.Student;
import com.springboot.restProject.Student.Management.repositories.DepartmentRepository;
import com.springboot.restProject.Student.Management.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import java.util.List;
import java.util.Map;

@RestController
public class DepartmentController
{
    @Autowired
    private DepartmentService dept;
    @GetMapping("/departments")
    public List<Department> get()
    {
        return dept.showAll();
    }

    @GetMapping("/departments/{id}")
    public ResponseEntity<Department> getbyid(@PathVariable Integer id) throws ResourceNotFoundException {
        return dept.showByID(id);
    }

    @PostMapping("/departments")
    public Department create(@RequestBody Department d)
    {
        return dept.createDepartment(d);
    }

    @PutMapping("/departments/{id}")
    public ResponseEntity<Department> update(@PathVariable Integer id, @RequestBody Department d) throws ResourceNotFoundException {
        return dept.updateDepartment(id,d);
    }

    @DeleteMapping("/departments")
    public void delete(@RequestParam Map<String,String> params) throws ResourceNotFoundException {
        Integer srcId = Integer.parseInt(params.get("sourceDept"));
        Integer destId = Integer.parseInt(params.get("targetDept"));

        dept.deleteDepartment(srcId,destId);
    }
}
