package com.example.digitalcontracts.Models;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PDF {

    @SerializedName("name")
    @Expose
    private String name;

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    @SerializedName("doc")
    @Expose
    private String doc;
    @SerializedName("doc1")
    @Expose
    private String doc1;
    @SerializedName("doc2")
    @Expose
    private String doc2;
    @SerializedName("doc3")
    @Expose
    private String doc3;
    @SerializedName("doc4")
    @Expose
    private String doc4;
    @SerializedName("doc5")
    @Expose
    private String doc5;
    @SerializedName("doc6")
    @Expose
    private String doc6;
    @SerializedName("doc7")
    @Expose
    private String doc7;
    @SerializedName("doc8")
    @Expose
    private String doc8;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDoc1() {
        return doc1;
    }

    public void setDoc1(String doc1) {
        this.doc1 = doc1;
    }

    public String getDoc2() {
        return doc2;
    }

    public void setDoc2(String doc2) {
        this.doc2 = doc2;
    }

    public String getDoc3() {
        return doc3;
    }

    public void setDoc3(String doc3) {
        this.doc3 = doc3;
    }

    public String getDoc4() {
        return doc4;
    }

    public void setDoc4(String doc4) {
        this.doc4 = doc4;
    }

    public String getDoc5() {
        return doc5;
    }

    public void setDoc5(String doc5) {
        this.doc5 = doc5;
    }

    public String getDoc6() {
        return doc6;
    }

    public void setDoc6(String doc6) {
        this.doc6 = doc6;
    }

    public String getDoc7() {
        return doc7;
    }

    public void setDoc7(String doc7) {
        this.doc7 = doc7;
    }

    public String getDoc8() {
        return doc8;
    }

    public void setDoc8(String doc8) {
        this.doc8 = doc8;
    }

}