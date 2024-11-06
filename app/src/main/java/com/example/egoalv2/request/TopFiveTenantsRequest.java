package com.example.egoalv2.request;

import com.google.gson.annotations.SerializedName;

public class TopFiveTenantsRequest {
    @SerializedName("concessionaire_id")
    private String concessionaireId;
    @SerializedName("propertyMallCode")
    private String propertyMallCode;
    @SerializedName("month")
    private String month;
    @SerializedName("property_id")
    private String property_id;
    @SerializedName("year")
    private String year;

    public TopFiveTenantsRequest(String concessionaireId, String propertyMallCode, String month, String property_id, String year) {
        this.concessionaireId = concessionaireId;
        this.propertyMallCode = propertyMallCode;
        this.month = month;
        this.property_id = property_id;
        this.year = year;
    }
}

