package com.test.Service;

import com.test.Dao.StudentDao;
import com.test.Entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class StudentService {

    @Autowired
    @Qualifier("mongoDB")
    private StudentDao studentDao;

    public Collection<Student> getAllStudents() {
        return studentDao.getAllStudents();
    }

    public Student getStudentById(int id) {
        return studentDao.getStudentById(id);
    }

    public boolean removeStudentById(int id) {
        return this.studentDao.removeStudentById(id);
    }

    public boolean updateStudent(Student student) {
        return this.studentDao.updateStudent(student);
    }

    public boolean insertStudent(Student student) {
        return this.studentDao.insertStudent(student);
    }
}