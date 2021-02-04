package com.springboot.restProject.Student.Management.repositories;

import com.springboot.restProject.Student.Management.models.entities.Department;
import com.springboot.restProject.Student.Management.models.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student,Integer>
{
    List<Student> findAllByDept(Department d);
}
