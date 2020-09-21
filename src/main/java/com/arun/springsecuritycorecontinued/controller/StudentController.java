package com.arun.springsecuritycorecontinued.controller;

import com.arun.springsecuritycorecontinued.model.Student;
import com.arun.springsecuritycorecontinued.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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


    @PreAuthorize("hasAuthority('student.read')")
    @GetMapping("/v1/student")
    public ResponseEntity<List<Student>> getStudent(@RequestParam String name) {
        List<Student> student = studentService.getStudent(name);
        return ResponseEntity.ok(student);
    }


    @PostMapping("/v1/student")
    @PreAuthorize("hasAuthority('student.create')")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student saved = studentService.createStudent(student);
        return ResponseEntity.ok(saved);
    }

    @PreAuthorize("hasAuthority('student.delete')")
    @DeleteMapping("/v1/student")
    public ResponseEntity<HttpStatus> deleteStudent(@RequestBody Student student) {
        studentService.deleteStudent(student);
        return new ResponseEntity<>(HttpStatus.GONE);
    }

    @PreAuthorize("hasAuthority('student.update')")
    @PutMapping("/v1/student")
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
        Student updatedStudent = studentService.updateStudent(student);
        return ResponseEntity.ok(updatedStudent);
    }

}
