package com.example.digitalcontracts.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Dashboard_api {
    @SerializedName("message")
    @Expose
    public String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @SerializedName("user_data")
    @Expose
    private List<user_data> userData = null;
    @SerializedName("result")
    @Expose
    private List<Result> result = null;

    public List<user_data> getUserData() {
        return userData;
    }

    public void setUserData(List<user_data> userData) {
        this.userData = userData;
    }


    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

}