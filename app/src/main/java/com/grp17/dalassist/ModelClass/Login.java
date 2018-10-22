package com.grp17.dalassist.ModelClass;

import java.net.URL;



public class Login {
    String id;
    String name;
    String password;
    String courseInfo_name;
    String courseInfo_detail;
    boolean status;
    String uploadEvent;
    URL urlImage;
    String locid,termid;
    String time;
    String name_faculty;

    public URL getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(URL urlImage) {
        this.urlImage = urlImage;
    }

    public Login(String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;

    }

    public Login(String id, String name, String courseInfo_name, String courseInfo_detail, String name_faculty) {
        this.id = id;
        this.name = name;
        this.courseInfo_name = courseInfo_name;
        this.courseInfo_detail = courseInfo_detail;
        this.name_faculty = name_faculty;
    }

    public Login(String id, String name, boolean status, String time) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.time = time;
    }

    public Login(String id, String courseInfo_name, String courseInfo_detail, boolean status) {
        this.id = id;
        this.courseInfo_name = courseInfo_name;
        this.courseInfo_detail = courseInfo_detail;
        this.status = status;
    }

    public Login(String id, String name, String locid, String termid) {
        this.id = id;
        this.name = name;
        this.locid = locid;
        this.termid = termid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUploadEvent() {
        return uploadEvent;
    }

    public void setUploadEvent(String uploadEvent) {
        this.uploadEvent = uploadEvent;
    }

    public String getLocid() {
        return locid;
    }

    public void setLocid(String locid) {
        this.locid = locid;
    }

    public String getTermid() {
        return termid;
    }

    public void setTermid(String termid) {
        this.termid = termid;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Login(String id, String password) {
        this.id = id;
        this.password = password;

    }

    public Login() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCourseInfo_name() {
        return courseInfo_name;
    }

    public void setCourseInfo_name(String courseInfo_name) {
        this.courseInfo_name = courseInfo_name;
    }

    public String getCourseInfo_detail() {
        return courseInfo_detail;
    }

    public void setCourseInfo_detail(String courseInfo_detail) {
        this.courseInfo_detail = courseInfo_detail;
    }

    public String getName_faculty() {
        return name_faculty;
    }

    public void setName_faculty(String name_faculty) {
        this.name_faculty = name_faculty;
    }

    @Override
    public String toString() {
        return "Login{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", courseInfo_name='" + courseInfo_name + '\'' +
                ", courseInfo_detail='" + courseInfo_detail + '\'' +
                ", status=" + status +
                ", uploadEvent='" + uploadEvent + '\'' +
                '}';
    }
}


