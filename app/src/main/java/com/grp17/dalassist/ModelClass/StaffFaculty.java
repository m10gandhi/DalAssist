package com.grp17.dalassist.ModelClass;

public class StaffFaculty {
    String id;
    String time;
    String contactNo;
    String emailId;
    String faculty_name;
    String student_name;

    public StaffFaculty(String id, String time, String contactNo, String emailId) {
        this.id = id;
        this.time = time;
        this.contactNo = contactNo;
        this.emailId = emailId;
    }
    public StaffFaculty(String id, String time, String contactNo) {
        this.id = id;
        this.time = time;
        this.contactNo = contactNo;
    }
 /*public StaffFaculty(String id, String time, String faculty_name,String student_name) {
        this.id = id;
        this.time = time;
        this.faculty_name = faculty_name;
        this.student_name = student_name;
    }
*/
    public StaffFaculty(String id, String time) {
        this.id = id;
        this.time = time;
    }

    public StaffFaculty() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getFaculty_name() {
        return faculty_name;
    }

    public void setFaculty_name(String faculty_name) {
        this.faculty_name = faculty_name;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    @Override
    public String toString() {
        return "StaffFaculty{" +
                "id='" + id + '\'' +
                ", time='" + time + '\'' +
                ", contactNo='" + contactNo + '\'' +
                ", emailId='" + emailId + '\'' +
                '}';
    }
}
