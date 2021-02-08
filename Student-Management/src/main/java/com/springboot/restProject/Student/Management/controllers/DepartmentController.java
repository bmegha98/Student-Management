package com.springboot.restProject.Student.Management.controllers;

import com.springboot.restProject.Student.Management.exceptions.ResourceNotFoundException;
import com.springboot.restProject.Student.Management.models.entities.Department;
import com.springboot.restProject.Student.Management.models.entities.Student;
import com.springboot.restProject.Student.Management.repositories.DepartmentRepository;
import com.springboot.restProject.Student.Management.repositories.StudentRepository;
import com.springboot.restProject.Student.Management.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class DepartmentController
{
    @Autowired
    private DepartmentService dept;

    @Autowired
    private DepartmentRepository drep;

    @Autowired
    private StudentRepository srep;

    @GetMapping("/departments")
    public List<Department> get()
    {
        return drep.findAll();
    }

    @GetMapping("/departments/{id}")
    public Department getbyid(@PathVariable Integer id) throws ResourceNotFoundException {
        Optional<Department> d = drep.findById(id);
        if(d.isPresent())
            return d.get();
        else
            throw new ResourceNotFoundException("Department "+ id+" not found :(");
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/departments")
    public Department create(@RequestBody Department d)
    {
        return drep.save(d);
    }

    @PutMapping("/departments/{id}")
    public Department update(@PathVariable Integer id, @RequestBody Department d) throws ResourceNotFoundException {
        Department dept = drep.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Department "+ id+" not found :("));

        dept.setDname(d.getDname());
        dept.setDlocation(d.getDlocation());
        dept.setDname(d.getDname());
        return drep.save(dept);
    }

    @DeleteMapping("/departments")
    public List<Student> delete(@RequestParam Map<String,String> params) throws ResourceNotFoundException {
        Integer srcId = Integer.parseInt(params.get("sourceDept"));
        Integer destId = Integer.parseInt(params.get("targetDept"));

        Department Sdept = drep.findById(srcId).orElseThrow(
                () -> new ResourceNotFoundException("Source Department "+ srcId+" not found :("));

        Department Tdept = drep.findById(destId).orElseThrow(
                () -> new ResourceNotFoundException("Target Department "+ destId+" not present :("));

        List<Student> stuList = srep.findAllByDept(Sdept);
        List<Student> updatedList = new ArrayList<>();
        for(Student s : stuList)
        {
            s.setDept(Tdept);
            updatedList.add(srep.save(s));
        }
        drep.deleteById(srcId);
        return updatedList;
    }
}
