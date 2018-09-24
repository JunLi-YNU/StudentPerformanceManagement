package com.example.junli.studentperformancemanagement.bean;



public class Student {

    private int _id;
    private String name;

    @Override
    public String toString() {
        return   _id+name;
    }

    public Student(int _id, String name) {
        this.name = name;
        this._id = _id;
    }

    public int get_id() {

        return _id;
    }

    public Student set_id(int _id) {
        this._id = _id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Student setName(String name) {
        this.name = name;
        return this;
    }
}
