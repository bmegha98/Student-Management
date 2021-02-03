package com.springboot.restProject.Student.Management.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

}
