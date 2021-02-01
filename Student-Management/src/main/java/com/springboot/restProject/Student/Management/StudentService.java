package com.springboot.restProject.Student.Management;

import java.util.ArrayList;
import java.util.List;

public class StudentService
{
    private List<Student> studentList;
    private static StudentService instance = null;
    public static StudentService getInstance()
    {
        if(instance == null)
            instance = new StudentService();
        return instance;
    }

    public StudentService()
    {
        studentList = new ArrayList<>();
        studentList.add(new Student(1,"Joey","Mechanical"));
        studentList.add(new Student(2,"Monica","Electrical"));
        studentList.add(new Student(3,"Ross","paleontology"));
        studentList.add(new Student(4,"Chandler","Something with numbers"));
        studentList.add(new Student(5,"Rachel","Computer Science"));
    }

    public List<Student> getAllStudents()
    {
        //return all students
        return studentList;
    }

    public Student getStudentByID(int id)
    {
        //return a particular student
        for(Student s : studentList)
        {
            if(s.getId() == id)
                return s;
        }
        return null;
    }

    public Student addStudent(int id,String name,String course)
    {
        //Add a new student
        Student s = new Student(id,name,course);
        studentList.add(s);
        return s;
    }

    public Student updateStudent(int id,String name,String course)
    {
        //Update details of a student
        for(Student s : studentList)
        {
            if(s.getId() == id)
            {
                int ind = studentList.indexOf(s);
                s.setName(name);
                s.setCourse(course);
                studentList.set(ind,s);
                return s;
            }
        }
        return null;
    }

    public  boolean deleteStudent(int id)
    {
        //Delete a student
        for(Student s : studentList)
        {
            if(s.getId() == id)
            {
                studentList.remove(s);
                return true;
            }
        }
        return false;
    }
}
