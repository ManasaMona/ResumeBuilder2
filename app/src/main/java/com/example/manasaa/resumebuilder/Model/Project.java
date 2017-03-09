package com.example.manasaa.resumebuilder.Model;

/**
 * Created by manasa on 05-03-2017.
 */

public class Project {
    public int project_id;
    public String project_name,project_role,project_summary;
    public Project(){
    }
    public Project(String name,String role,String summary){
        project_name=name;
        project_role=role;
        project_summary = summary;
    }
    public Project(int id,String name,String role,String summary){
        project_id = id;
        project_name=name;
        project_role=role;
        project_summary = summary;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getProject_role() {
        return project_role;
    }

    public void setProject_role(String project_role) {
        this.project_role = project_role;
    }

    public String getProject_summary() {
        return project_summary;
    }

    public void setProject_summary(String project_summary) {
        this.project_summary = project_summary;
    }


}
