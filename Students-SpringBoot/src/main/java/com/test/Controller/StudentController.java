package com.test.Controller;

import com.test.Entity.Student;
import com.test.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getAllStudents() {
        Collection<Student> students = studentService.getAllStudents();
        if (!students.isEmpty()) {
            return ResponseEntity.ok(students);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity getStudentById(@PathVariable("id") int id) {
        Student student = studentService.getStudentById(id);
        if (student != null) {
            return ResponseEntity.ok(student);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity insertStudentById(@RequestBody Student student) {
        if (studentService.insertStudent(student)) {
            return ResponseEntity.ok("Student has been inserted!");
        }
        return ResponseEntity.badRequest().body("Student already exists!");
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateStudentById(@RequestBody Student student) {
        if (studentService.updateStudent(student)) {
            return ResponseEntity.ok("Student has been updated!");
        }
        return ResponseEntity.badRequest().body("Student not found!");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteStudentById(@PathVariable("id") int id) {
        if (studentService.removeStudentById(id)) {
            return ResponseEntity.ok("Student has been deleted!");
        }
        return ResponseEntity.badRequest().body("Student not found!");
    }

}