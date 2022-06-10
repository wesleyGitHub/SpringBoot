package com.example.demo.student;

import com.example.demo.exception.ApiRequestException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student getStudent(Long id) {
        Optional<Student> studentOptional = studentRepository.findStudentById(id);

        //if(!studentOptional.isPresent()) {
            //throw new IllegalStateException("can't find student.");
            //throw new ApiRequestException("can't find student.");
        //}
        return studentRepository.findStudentById(id).get();
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student) {
        Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());

        if(studentOptional.isPresent()) {
            throw new IllegalStateException("email taken!");
        }
        System.out.println(student);

        studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        boolean exists = studentRepository.existsById(studentId);
        if (!exists){
            throw new IllegalStateException("Student with id : " + studentId + " does not exists");
        }

        studentRepository.deleteById(studentId);
    }

    //@Transactional annotation 可以使entity進入managed狀態,只要使用setter即可更新欄位
    @Transactional
    public void updateStudent(Long studentId, String name, String birth, String email) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException(
                        "student with id : " + studentId  + " does not exists."
                        )
                );

        System.out.println("name : " + name);
        System.out.println("birth : " + birth);
        System.out.println("email : " + email);

        if (name != null && name.length() > 0 &&
            !Objects.equals(student.getName(), name)) {
            student.setName(name);
        }

        if (birth != null) {
            LocalDate localDateBirth = LocalDate.parse(birth);
            System.out.println("localDateBirth : " + localDateBirth);
            student.setBirth(localDateBirth);
        }

        if (email != null && email.length() > 0 &&
                !Objects.equals(student.getEmail(), email)) {
            Optional<Student> studentOptional = studentRepository.findStudentByEmail(email);

            if(studentOptional.isPresent()) {
                throw new IllegalStateException("email taken.");
            }
            student.setEmail(email);
        }
    }
}
