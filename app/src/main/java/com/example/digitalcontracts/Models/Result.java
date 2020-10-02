package com.example.digitalcontracts.Models;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("application_id")
    @Expose
    private String applicationId;
    @SerializedName("father_husband_name")
    @Expose
    private String fatherHusbandName;
    @SerializedName("applicant_status")
    @Expose
    private String applicantStatus;

    public String getApplicantStatus() {
        return applicantStatus;
    }

    public void setApplicantStatus(String applicantStatus) {
        this.applicantStatus = applicantStatus;
    }

    @SerializedName("DOS")
    @Expose
    private String DOS;

    public String getDOS() {
        return DOS;
    }

    public void setDOS(String DOS) {
        this.DOS = DOS;
    }

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("profile_image")
    @Expose
    private String profileImage;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getFatherHusbandName() {
        return fatherHusbandName;
    }

    public void setFatherHusbandName(String fatherHusbandName) {
        this.fatherHusbandName = fatherHusbandName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

}