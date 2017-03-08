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
}
