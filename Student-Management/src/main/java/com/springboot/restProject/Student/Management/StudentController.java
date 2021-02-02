package com.springboot.restProject.Student.Management;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class StudentController
{
    //To implement Singleton java class
    StudentService stu = StudentService.getInstance();

    @GetMapping("/students")
    public List<Student> show()
    {
        return stu.getAllStudents();
    }

    @GetMapping("/students/{id}")
    public Student showByID(@PathVariable String id)
    {
        return stu.getStudentByID(Integer.parseInt(id));
    }

    @PostMapping("/students")
    public Student create(@RequestBody Map<String,String> body)
    {
        int ID = Integer.parseInt(body.get("id"));
        String name = body.get("name");
        String course = body.get("course");
        return stu.addStudent(ID,name,course);
    }

    @PutMapping("/students/{id}")
    public Student update(@PathVariable String id,@RequestBody Map<String,String> body)
    {
        int ID = Integer.parseInt(id);
        String name = body.get("name");
        String course = body.get("course");
        return stu.updateStudent(ID,name,course);
    }

    @DeleteMapping("/students/{id}")
    public boolean delete(@PathVariable String id)
    {
        int ID = Integer.parseInt(id);
        return stu.deleteStudent(ID);
    }
}
