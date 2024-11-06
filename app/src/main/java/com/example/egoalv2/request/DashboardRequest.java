package com.example.egoalv2.request;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

@SuppressLint("SimpleDateFormat")
public class DashboardRequest {
    @SerializedName("concessionaire_id")
    private String concessionaireId;
    @SerializedName("start_date")
    private String startDate;
    @SerializedName("end_date")
    private String endDate;
    @SerializedName("property_id")
    private String propertyId;
    @SerializedName("tenant_id")
    private String tenantId;
    @SerializedName("property_access")
    private String propertyAccess;

    // ISO 8601 format with milliseconds and UTC time zone
//    private static final SimpleDateFormat isoFormat;
//
//    static {
//        isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
//        isoFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
//    }

    private String changeDateFormat(String oldDate) throws ParseException {
        String newDate;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC")); // Set UTC time zone if needed
        newDate = String.valueOf(dateFormat.parse(oldDate));
        return newDate.toString();

    }
    public String getConcessionaireId() {
        return concessionaireId;
    }

    public void setConcessionaireId(String concessionaireId) {
        this.concessionaireId = concessionaireId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getPropertyAccess() {
        return propertyAccess;
    }

    public void setPropertyAccess(String propertyAccess) {
        this.propertyAccess = propertyAccess;
    }

    public DashboardRequest(String concessionaireId, String startDate, String endDate, String propertyId, String tenantId, String propertyAccess) {
        this.concessionaireId = concessionaireId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.propertyId = propertyId;
        this.tenantId = tenantId;
        this.propertyAccess = propertyAccess;
    }

    @NonNull
    @Override
    public String toString() {
        return "DashboardRequest{" +
                "concessionaireId='" + concessionaireId + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", propertyId='" + propertyId + '\'' +
                ", tenantId='" + tenantId + '\'' +
                ", propertyAccess='" + propertyAccess + '\'' +
                '}';
    }
}

