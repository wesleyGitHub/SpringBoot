package com.example.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner (StudentRepository repository) {
        return  args -> {
            Student marin = new Student(
                    "Marin",
                    LocalDate.of(2000, Month.JANUARY, 5),
                    "Marin@gmail.com"
            );

            Student alex = new Student(
                    "Alex",
                    LocalDate.of(2004, Month.JANUARY, 5),
                    "Alex@gmail.com"
            );

            repository.deleteAll();
            repository.saveAll(List.of(marin, alex));
        };
    }
}
