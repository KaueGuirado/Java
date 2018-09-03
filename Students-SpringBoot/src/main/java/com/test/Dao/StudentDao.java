package com.test.Dao;

import com.test.Entity.Student;

import java.util.Collection;

public interface StudentDao {
    Collection<Student> getAllStudents();

    Student getStudentById(int id);

    boolean removeStudentById(int id);

    boolean updateStudent(Student student);

    boolean insertStudent(Student student);
}
