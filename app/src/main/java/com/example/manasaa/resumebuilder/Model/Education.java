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


    public int getEducation_id() {
        return education_id;
    }

    public void setEducation_id(int education_id) {
        this.education_id = education_id;
    }

    public String getInstitute_name() {
        return institute_name;
    }

    public void setInstitute_name(String institute_name) {
        this.institute_name = institute_name;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }


}
