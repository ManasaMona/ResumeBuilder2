package com.example.manasaa.resumebuilder.Model;

/**
 * Created by manasa on 05-03-2017.
 */

public class Education {
    public int education_id;
    public String institute_name,course_name,year;
    public  Education(){

    }
    public Education(String name,String course,String year){
        institute_name = name;
        course_name = course;
        this.year = year;
    }
    public Education(int id,String name,String course,String year){
        education_id = id;
        institute_name = name;
        course_name = course;
        this.year = year;
    }
}
