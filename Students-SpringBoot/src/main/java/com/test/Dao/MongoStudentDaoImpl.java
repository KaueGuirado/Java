package com.test.Dao;

import com.mongodb.*;
import com.test.Entity.Student;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.net.UnknownHostException;
import java.util.*;

import static oracle.jrockit.jfr.events.Bits.intValue;

@Repository
@Qualifier("mongoDB")
public class MongoStudentDaoImpl implements StudentDao {
    MongoClient mongoClient;
    DB db;
    DBCollection studentCollection;

    public MongoStudentDaoImpl() {

        try {
            mongoClient = new MongoClient("172.21.82.26", 27017);
            db = mongoClient.getDB("test");
            studentCollection = db.getCollection("student");
            System.out.println("connect to database successfully");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Collection<Student> getAllStudents() {
        System.out.println("Searching Students list...");
        List<Student> students = new ArrayList<Student>();
        DBCursor cursor = studentCollection.find();
        Map<String, Object> myMap = new HashMap<String, Object>();
        while (cursor.hasNext()) {
            myMap = cursor.next().toMap();
            Student student = new Student();
            student.setId(intValue(myMap.get("_id")));
            student.setName(myMap.get("name").toString());
            student.setCourse(myMap.get("course").toString());
            students.add(student);
        }

        return students;
    }

    @Override
    public Student getStudentById(int id) {
        System.out.println("Searching Student by id " + id + " ...");
        DBObject query = BasicDBObjectBuilder.start().add("_id", id).get();
        DBObject result = studentCollection.findOne(query);
        if (result != null) {
            Map<String, Object> myMap = result.toMap();
            Student student = new Student();
            student.setId(intValue(myMap.get("_id")));
            student.setName(myMap.get("name").toString());
            student.setCourse(myMap.get("course").toString());
            return student;
        }
        return null;
    }

    @Override
    public boolean insertStudent(Student student) {
        System.out.println("Inserting new Student " + student.toString() + " ...");
        DBObject query = BasicDBObjectBuilder.start().add("_id", student.getId()).get();
        DBObject result = studentCollection.findOne(query);
        if (result == null) {
            DBObject doc = createDBObject(student);
            studentCollection.insert(doc);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateStudent(Student student) {
        System.out.println("Updating Student id " + student.getId() + " ...");

        DBObject query = BasicDBObjectBuilder.start().add("_id", student.getId()).get();
        DBObject result = studentCollection.findOne(query);
        if (result != null) {
            DBObject doc = createDBObject(student);
            studentCollection.update(query, doc);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeStudentById(int id) {
        System.out.println("Deleting Student id " + id + " ...");
        DBObject query = BasicDBObjectBuilder.start().add("_id", id).get();
        DBObject result = studentCollection.findOne(query);
        if (result != null) {
            studentCollection.remove(query);
            return true;
        }
        return false;
    }

    private static DBObject createDBObject(Student student) {
        BasicDBObjectBuilder docBuilder = BasicDBObjectBuilder.start();
        docBuilder.append("_id", student.getId());
        docBuilder.append("name", student.getName());
        docBuilder.append("course", student.getCourse());
        return docBuilder.get();
    }
}
