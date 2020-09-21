package com.arun.springsecuritycorecontinued.service;

import com.arun.springsecuritycorecontinued.domain.StudentDomain;
import com.arun.springsecuritycorecontinued.mapper.StudentMapper;
import com.arun.springsecuritycorecontinued.model.Student;
import com.arun.springsecuritycorecontinued.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author arun on 9/19/20
 */

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, StudentMapper studentMapper) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }

    @Override
    public List<Student> getStudent(String name) {
        List<StudentDomain> studentDomains = studentRepository.findByName(name);

        List<Student> students = new ArrayList<>();
        studentDomains.forEach(studentDomain -> {
            Student student = studentMapper.studentDomainToStudent(studentDomain);
            students.add(student);
        });
        return students;
    }

    @Override
    public Student updateStudent(Student student) {
        StudentDomain studentDomain = studentMapper.studentToStudentDomain(student);
        StudentDomain save = studentRepository.save(studentDomain);
        return studentMapper.studentDomainToStudent(save);
    }

    @Override
    public void deleteStudent(Student student) {
        StudentDomain studentDomain = studentMapper.studentToStudentDomain(student);
        studentRepository.delete(studentDomain);
    }

    @Override
    public Student createStudent(Student student) {
        StudentDomain studentDomain = studentMapper.studentToStudentDomain(student);
        StudentDomain save = studentRepository.save(studentDomain);
        return studentMapper.studentDomainToStudent(save);
    }

    @Override
    public List<Student> getAllStudent() {
        Iterable<StudentDomain> studentDomains = studentRepository.findAll();

        List<Student> students = new ArrayList<>();
        studentDomains.forEach(studentDomain -> {
            Student student = studentMapper.studentDomainToStudent(studentDomain);
            students.add(student);
        });
        return students;
    }
}
