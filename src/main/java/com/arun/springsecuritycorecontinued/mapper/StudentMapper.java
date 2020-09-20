package com.arun.springsecuritycorecontinued.mapper;

import com.arun.springsecuritycorecontinued.domain.StudentDomain;
import com.arun.springsecuritycorecontinued.model.Student;
import org.mapstruct.Mapper;

/**
 * @author arun on 9/19/20
 */

@Mapper
public interface StudentMapper {

    Student studentDomainToStudent(StudentDomain studentDomain);

    StudentDomain studentToStudentDomain(Student student);
}
