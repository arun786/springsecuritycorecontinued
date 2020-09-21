package com.arun.springsecuritycorecontinued.service;

import com.arun.springsecuritycorecontinued.model.Student;

import java.util.List;

/**
 * @author arun on 9/19/20
 */

public interface StudentService {

    List<Student> getStudent(String name);

    Student updateStudent(Student student);

    void deleteStudent(Student student);

    Student createStudent(Student student);
}
