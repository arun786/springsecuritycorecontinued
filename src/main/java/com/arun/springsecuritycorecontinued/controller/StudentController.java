package com.arun.springsecuritycorecontinued.controller;

import com.arun.springsecuritycorecontinued.annotation.StudentCreateOnly;
import com.arun.springsecuritycorecontinued.annotation.StudentDeleteOnly;
import com.arun.springsecuritycorecontinued.annotation.StudentReadOnly;
import com.arun.springsecuritycorecontinued.annotation.StudentUpdateOnly;
import com.arun.springsecuritycorecontinued.model.Student;
import com.arun.springsecuritycorecontinued.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


    @StudentReadOnly
    @GetMapping("/v1/student")
    public ResponseEntity<List<Student>> getStudent(@RequestParam String name) {
        List<Student> student = studentService.getStudent(name);
        return ResponseEntity.ok(student);
    }


    @StudentCreateOnly
    @PostMapping("/v1/student")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student saved = studentService.createStudent(student);
        return ResponseEntity.ok(saved);
    }

    @StudentDeleteOnly
    @DeleteMapping("/v1/student")
    public ResponseEntity<HttpStatus> deleteStudent(@RequestBody Student student) {
        studentService.deleteStudent(student);
        return new ResponseEntity<>(HttpStatus.GONE);
    }

    @StudentUpdateOnly
    @PutMapping("/v1/student")
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
        Student updatedStudent = studentService.updateStudent(student);
        return ResponseEntity.ok(updatedStudent);
    }


    @StudentReadOnly
    @GetMapping("/v2/student")
    public ResponseEntity<List<Student>> getAllStudent() {
        List<Student> student = studentService.getAllStudent();
        return ResponseEntity.ok(student);
    }

}
