package com.example.digitalcontracts.Models;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Status_percent {

    @SerializedName("result")
    @Expose
    private percent result;

    public percent getResult() {
        return result;
    }

    public void setResult(percent result) {
        this.result = result;
    }

}