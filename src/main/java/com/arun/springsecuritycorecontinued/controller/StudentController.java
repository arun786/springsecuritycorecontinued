package com.arun.springsecuritycorecontinued.controller;

import com.arun.springsecuritycorecontinued.model.Student;
import com.arun.springsecuritycorecontinued.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author arun on 9/19/20
 */

@RestController
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @GetMapping("/v1/student")
    public ResponseEntity<List<Student>> getStudent(@RequestParam String name) {
        List<Student> student = studentService.getStudent(name);
        return ResponseEntity.ok(student);
    }
}
