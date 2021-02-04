package com.springboot.restProject.Student.Management.services;

import com.springboot.restProject.Student.Management.exceptions.ResourceNotFoundException;
import com.springboot.restProject.Student.Management.models.entities.Department;
import com.springboot.restProject.Student.Management.models.entities.Student;
import com.springboot.restProject.Student.Management.models.requests.RequestClass;
import com.springboot.restProject.Student.Management.repositories.DepartmentRepository;
import com.springboot.restProject.Student.Management.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public List<Student> showByDept(Integer departmentId) throws ResourceNotFoundException {
        Optional<Department> d = drep.findById(departmentId);
        if(d.isPresent())
            return rep.findAllByDept(d.get());
        else
            throw  new ResourceNotFoundException("Not found");
    }

    public ResponseEntity<Student> showByID(Integer id)throws ResourceNotFoundException
    {
         Student s= rep.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("Student " + id+" not found :("));
         return ResponseEntity.ok().body(s);
    }

    public ResponseEntity<Student> createStudent(RequestClass s) throws ResourceNotFoundException {
        Department d = drep.findById(s.getDnum()).orElseThrow(
                () -> new ResourceNotFoundException("Department " + s.getDnum()+"not found :("));

        Student newStudent = new Student(s.getId(),s.getName(),d);
        rep.save(newStudent);
        return ResponseEntity.ok(newStudent);
    }

    public ResponseEntity<Student> updateStudent(Integer id,RequestClass s) throws ResourceNotFoundException
    {
        Department d = drep.findById(s.getDnum()).orElseThrow(
                () -> new ResourceNotFoundException("Department not found :("));

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
