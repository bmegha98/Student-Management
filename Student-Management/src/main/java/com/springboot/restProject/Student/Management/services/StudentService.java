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
public class StudentService
{
    @Autowired                                      //Inject object of StudentService class
    private StudentRepository rep;

    @Autowired
    private DepartmentRepository drep;

    public List<Student> showAll()
    {
        return rep.findAll();
    }

    public List<Student> showByDept(Integer departmentId) {
        return rep.findByDept_dnumber(departmentId);
    }

    public ResponseEntity<Student> showByID(Integer id)throws ResourceNotFoundException
    {
         Student s= rep.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("Student not found :(" + id));
         return ResponseEntity.ok().body(s);
    }

    public Student createStudent(Integer DeptId,Student s)
    {
        Department d = drep.getOne(DeptId);
        s.setDept(d);
        return rep.save(s);
    }

    public ResponseEntity<Student> updateStudent(Integer id,Integer deptId,Student s) throws ResourceNotFoundException
    {
        Department d = drep.findById(deptId).orElseThrow(
                () -> new ResourceNotFoundException("Department "+deptId+" not found :("));

        Student stu = rep.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Student " + id+" not found :("));

        stu.setName(s.getName());
        stu.setId(s.getId());
        stu.setDept(d);
        final Student updatedStudent = rep.save(stu);
        return ResponseEntity.ok(updatedStudent);

    }

    public void deleteStudent(Integer id) throws ResourceNotFoundException
    {
        Student stu = rep.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Student " + id+"not found :("));
        rep.deleteById(id);
    }
}
