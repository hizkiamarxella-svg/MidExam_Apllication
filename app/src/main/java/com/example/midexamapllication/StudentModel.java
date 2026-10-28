package com.example.midexamapllication;

public class StudentModel {
    private int id;
    private String name;
    private String studentId;
    private int age;

    public StudentModel(int id, String name, String studentId, int age) {
        this.id = id;
        this.name = name;
        this.studentId = studentId;
        this.age = age;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getStudentId() { return studentId; }
    public int getAge() { return age; }

    public void setName(String name) { this.name = name; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    public void setAge(int age) { this.age = age; }
}