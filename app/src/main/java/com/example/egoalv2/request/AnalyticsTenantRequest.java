package com.example.egoalv2.request;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class AnalyticsTenantRequest {

    private String concessionaire_id;

    private String start_date;

    private String end_date;

    private String property_name;

    private String property_access;

    public AnalyticsTenantRequest(String concessionaireId, String startDate, String endDate, String propertyName, String propertyAccess) {
        this.concessionaire_id = concessionaireId;
        this.start_date = startDate;
        this.end_date = endDate;
        this.property_name = propertyName;
        this.property_access = propertyAccess;
    }



    @NonNull
    @Override
    public String toString() {
        return "AnalyticsTenantRequest{" +
                "concessionaire_id='" + concessionaire_id + '\'' +
                ", start_date='" + start_date + '\'' +
                ", end_date='" + end_date + '\'' +
                ", property_name='" + property_name + '\'' +
                ", property_access='" + property_access + '\'' +
                '}';
    }
}
