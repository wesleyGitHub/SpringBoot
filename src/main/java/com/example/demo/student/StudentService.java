package com.example.demo.student;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Service
public class StudentService {
    public List<Student> getStudent() {
        return List.of(new Student(
                1L,
                "Marin",
                LocalDate.of(2000, Month.JANUARY, 5),
                21,
                "Marin@gmail.com"
        ));
    }
}
