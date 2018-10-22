package com.grp17.dalassist.Assist;


public class commentmodel {
    String comment;
    String user_id;

    public commentmodel() {
    }

    public commentmodel(String comment, String user_id) {
        this.comment = comment;
        this.user_id = user_id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
