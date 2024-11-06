package com.example.egoalv2.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoginResponse {
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("user_role")
    @Expose
    private String userRole;
    @SerializedName("user_type_id")
    @Expose
    private String userTypeId;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("user_concessionaire_id")
    @Expose
    private String userConcessionaireId;
    @SerializedName("user_concessionaire_name")
    @Expose
    private String userConcessionaireName;
    @SerializedName("property_id")
    @Expose
    private List<Object> propertyId;
    @SerializedName("token")
    @Expose
    private String token;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(String userTypeId) {
        this.userTypeId = userTypeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserConcessionaireId() {
        return userConcessionaireId;
    }

    public void setUserConcessionaireId(String userConcessionaireId) {
        this.userConcessionaireId = userConcessionaireId;
    }

    public String getUserConcessionaireName() {
        return userConcessionaireName;
    }

    public void setUserConcessionaireName(String userConcessionaireName) {
        this.userConcessionaireName = userConcessionaireName;
    }

    public List<Object> getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(List<Object> propertyId) {
        this.propertyId = propertyId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "userId='" + userId + '\'' +
                ", userRole='" + userRole + '\'' +
                ", userTypeId='" + userTypeId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", userName='" + userName + '\'' +
                ", userConcessionaireId='" + userConcessionaireId + '\'' +
                ", userConcessionaireName='" + userConcessionaireName + '\'' +
                ", propertyId=" + propertyId +
                ", token='" + token + '\'' +
                '}';
    }
}
