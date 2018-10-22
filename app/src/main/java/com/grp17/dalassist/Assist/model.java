package com.grp17.dalassist.Assist;



public class model {

    String pic;
    String picName;
    String userName;
    String user_id;
    String medium;
    String description;

    public model() {
    }

    public model(String pic, String picName, String userName, String user_id, String medium, String description) {
        this.pic = pic;
        this.picName = picName;
        this.userName = userName;
        this.user_id = user_id;
        this.medium = medium;
        this.description = description;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
