package com.arun.springsecuritycorecontinued.loader;

import com.arun.springsecuritycorecontinued.domain.StudentDomain;
import com.arun.springsecuritycorecontinued.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

/**
 * @author arun on 9/19/20
 */

@Configuration
public class StudentLoader implements CommandLineRunner {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentLoader(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        for (int i = 0; i < 10; i++) {
            StudentDomain studentDomain = new StudentDomain()
                    .setAge(10)
                    .setName("Arun" + (i + 1))
                    .setStandard("1");
            studentRepository.save(studentDomain);
        }
    }
}
