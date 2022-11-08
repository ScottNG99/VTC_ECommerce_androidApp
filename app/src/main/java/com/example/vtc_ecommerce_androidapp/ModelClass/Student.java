package com.example.vtc_ecommerce_androidapp.ModelClass;

public class Student {

    private  int userID;
    private String studentID, user_mobile, colleges, student_name, user_nick_name, user_address, student_course;

    public Student(int userID, String studentID, String user_mobile, String colleges, String student_name, String user_nick_name, String user_address, String student_course) {
        this.userID = userID;
        this.studentID = studentID;
        this.user_mobile = user_mobile;
        this.colleges = colleges;
        this.student_name = student_name;
        this.user_nick_name = user_nick_name;
        this.user_address = user_address;
        this.student_course = student_course;
    }

    public int getUserID() {
        return userID;
    }

    public String getStudentID() {
        return studentID;
    }

    public String getUser_mobile() {
        return user_mobile;
    }

    public String getColleges() {
        return colleges;
    }

    public String getStudent_name() {
        return student_name;
    }

    public String getUser_nick_name() {
        return user_nick_name;
    }

    public String getUser_address() {
        return user_address;
    }

    public String getStudent_course() {
        return student_course;
    }
}
