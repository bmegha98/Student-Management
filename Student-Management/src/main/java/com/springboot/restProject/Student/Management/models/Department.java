package com.springboot.restProject.Student.Management.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "department")
//@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
@Data @AllArgsConstructor @NoArgsConstructor
public class Department
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer dnumber;
    private String dname;
    private String dlocation;

    @OneToMany(mappedBy = "dept")

    @JsonIgnore         //Ignore the property
    private List<Student> studentList;

}
