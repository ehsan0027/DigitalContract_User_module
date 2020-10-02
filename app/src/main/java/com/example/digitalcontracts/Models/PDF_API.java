package com.example.digitalcontracts.Models;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PDF_API {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("arr")
    @Expose
    private List<PDF> result = null;
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<PDF> getResult() {
        return result;
    }

    public void setResult(List<PDF> result) {
        this.result = result;
    }

}