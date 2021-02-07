package com.springboot.restProject.Student.Management.services;

import com.springboot.restProject.Student.Management.exceptions.ResourceNotFoundException;
import com.springboot.restProject.Student.Management.models.entities.Department;
import com.springboot.restProject.Student.Management.models.entities.Student;
import com.springboot.restProject.Student.Management.repositories.DepartmentRepository;
import com.springboot.restProject.Student.Management.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentService
{
    @Autowired
    private DepartmentRepository rep;

    @Autowired
    private StudentRepository srep;

    public Integer deleteDepartment(Integer srcId, Integer destId) throws ResourceNotFoundException {

        Department Sdept = rep.findById(srcId).orElseThrow(
                () -> new ResourceNotFoundException("Source Department "+ srcId+" not found :("));

        Department Tdept = rep.findById(destId).orElseThrow(
                () -> new ResourceNotFoundException("Target Department "+ destId+" not present :("));

        List<Student> stuList = srep.findAllByDept(Sdept);
        List<Student> updatedList = new ArrayList<>();
        for(Student s : stuList)
        {
            s.setDept(Tdept);
            updatedList.add(srep.save(s));
        }
        rep.deleteById(srcId);
        return updatedList.size();
    }
}
