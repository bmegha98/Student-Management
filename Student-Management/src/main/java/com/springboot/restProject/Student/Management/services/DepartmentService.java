package com.springboot.restProject.Student.Management.services;

import com.springboot.restProject.Student.Management.exceptions.ResourceNotFoundException;
import com.springboot.restProject.Student.Management.models.Department;
import com.springboot.restProject.Student.Management.models.Student;
import com.springboot.restProject.Student.Management.repositories.DepartmentRepository;
import com.springboot.restProject.Student.Management.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
public class DepartmentService
{
    @Autowired
    private DepartmentRepository rep;

    @Autowired
    private StudentRepository srep;
    public List<Department> showAll()
    {
        return rep.findAll();
    }

    public ResponseEntity<Department> showByID(Integer id) throws ResourceNotFoundException {
        Department d = rep.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Department "+ id+" not found :("));
        return ResponseEntity.ok().body(d);
    }

    public Department createDepartment(@RequestBody Department d)
    {
        return rep.save(d);
    }

    public ResponseEntity<Department> updateDepartment(Integer id,Department d) throws ResourceNotFoundException {
        Department dept = rep.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Department "+ id+" not found :("));
        dept.setDname(d.getDname());
        dept.setDlocation(d.getDlocation());
        dept.setDname(d.getDname());
        Department updatedDept = rep.save(dept);
        return ResponseEntity.ok(updatedDept);
    }

    public void deleteDepartment(Integer srcId,Integer destId) throws ResourceNotFoundException {
        Department Sdept = rep.findById(srcId).orElseThrow(
                () -> new ResourceNotFoundException("Source Department "+ srcId+" not found :("));

        Department Tdept = rep.findById(destId).orElseThrow(
                () -> new ResourceNotFoundException("Target Department "+ destId+" not present :("));

        List<Student> stuList = srep.findByDept_dnumber(srcId);
        for(Student s : stuList)
        {
            s.setDept(Tdept);
            srep.save(s);
        }
        rep.deleteById(srcId);
    }
}
