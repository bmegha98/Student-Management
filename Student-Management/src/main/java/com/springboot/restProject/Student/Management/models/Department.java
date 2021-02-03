package com.springboot.restProject.Student.Management.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "department")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Department
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer dnumber;
    private String dname;
    private String dlocation;

    @OneToMany(mappedBy = "dept")

    @JsonIgnore
    private List<Student> studentList;
    public Department() {
    }

    public Department(Integer dnumber, String dname,String dloc) {
        this.dnumber = dnumber;
        this.dname = dname;
        this.dlocation = dloc;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    public Integer getDnumber() {
        return dnumber;
    }

    public void setDnumber(Integer dnumber) {
        this.dnumber = dnumber;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getDlocation() {
        return dlocation;
    }

    public void setDlocation(String dlocation) {
        this.dlocation = dlocation;
    }
}
