package com.springboot.restProject.Student.Management.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity(name = "student")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Student
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "dno",nullable = false)           //Indicates that this is owner entity
    private Department dept;


    public Student() {}

    public Student(Integer id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Department getDept() {
        return dept;
    }

    public void setDept(Department dept) {
        this.dept = dept;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name +
                '}';
    }
}
