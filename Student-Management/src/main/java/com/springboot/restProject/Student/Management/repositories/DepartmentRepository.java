package com.springboot.restProject.Student.Management.repositories;

import com.springboot.restProject.Student.Management.models.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department,Integer>
{
}
