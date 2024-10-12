package com.dutact.web.auth.factors;

import com.dutact.web.core.repositories.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentAccountServiceImpl implements StudentAccountService {
    private final StudentRepository studentRepository;

    public StudentAccountServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Optional<Integer> getStudentId(String username) {
        return studentRepository.findByUsername(username).map(Account::getId);
    }
}
