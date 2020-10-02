package com.example.digitalcontracts.Models;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Login_api {

    @SerializedName("message")
    @Expose
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}