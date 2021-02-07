package com.springboot.restProject.Student.Management.repositories;

import com.springboot.restProject.Student.Management.models.entities.Department;
import com.springboot.restProject.Student.Management.models.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student,Integer>
{
    List<Student> findAllByDept(Department d);
}
