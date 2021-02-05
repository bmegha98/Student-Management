package com.springboot.restProject.Student.Management.models.entities;

import com.springboot.restProject.Student.Management.models.entities.Department;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "student")
//@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
@Data @AllArgsConstructor @NoArgsConstructor
public class Student
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "dno",nullable = false)           //Indicates that this is owner entity
    private Department dept;

    public Student(Integer id, String name) {
        this.id = id;
        this.name = name;
        this.dept = new Department()
    }
}
