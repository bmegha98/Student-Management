package com.springboot.restProject.Student.Management.controllers;

import com.springboot.restProject.Student.Management.models.Department;
import com.springboot.restProject.Student.Management.repositories.DepartmentRepository;
import com.springboot.restProject.Student.Management.repositories.StudentRepository;
import com.springboot.restProject.Student.Management.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentController
{
    //To implement Singleton java class
    @Autowired                                      //Inject object of StudentService class
    private StudentRepository rep;

    @Autowired
    private DepartmentRepository drep;

    @GetMapping("/students")
    public List<Student> showAll()
    {
        return rep.findAll();
    }
    @GetMapping("/department/{departmentId}/students")
    public List<Student> show(@PathVariable Integer departmentId) {
        return rep.findByDept_dnumber(departmentId);
    }

    @GetMapping("/students/{id}")
    public Student showByID(@PathVariable Integer id)
    {
        return rep.getOne(id);
    }

    @PostMapping("/department/{id}/students")
    public Student create(@PathVariable Integer id,@RequestBody Student s)
    {
        Department d = drep.getOne(id);
        s.setDept(d);
        return rep.saveAndFlush(s);
    }

    @PutMapping("/students/{id}/department/{deptId}")
    public Student update(@PathVariable Integer id,@PathVariable Integer deptId,@RequestBody Student s)
    {
        Department d = drep.getOne(deptId);
        Student stu = rep.getOne(id);
        stu.setName(s.getName());
        stu.setId(s.getId());
        stu.setDept(d);
        rep.save(stu);
        return stu;
    }

    @DeleteMapping("/students/{id}")
    public void delete(@PathVariable Integer id)
    {
        rep.deleteById(id);
    }
}
