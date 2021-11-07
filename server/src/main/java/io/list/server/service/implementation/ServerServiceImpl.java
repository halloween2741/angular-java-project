package io.list.server.service.implementation;

import java.util.Collection;

import javax.transaction.Transactional;

import io.list.server.model.Student;
import io.list.server.repo.StudentRepo;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static java.lang.Boolean.TRUE;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class ServerServiceImpl {

    private final StudentRepo studentRepo;

    public Student createStudent(Student student) {
        log.info("Saving new student");
        return studentRepo.save(student);
    }

    public Collection<Student> students(int limit) {
        log.info("Fetching all students");
        return studentRepo.findAll(PageRequest.of(0, limit)).toList();
    }

    public Student updateStudent(Student student) {
        log.info("Updating student");
        return studentRepo.save(student);
    }

    public Boolean deleteStudent(Long id) {
        log.info("deleting student by ID: {}", id);
        studentRepo.deleteById(id);
        return TRUE;
    }
}
