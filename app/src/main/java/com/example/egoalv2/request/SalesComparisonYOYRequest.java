package com.example.egoalv2.request;

import com.google.gson.annotations.SerializedName;

public class SalesComparisonYOYRequest {
    @SerializedName("concessionaire_id")
    private String concessionaireId;
    @SerializedName("property_id")
    private String property_id;

    public SalesComparisonYOYRequest(String concessionaireId, String property_id) {
        this.concessionaireId = concessionaireId;
        this.property_id = property_id;
    }

    @Override
    public String toString() {
        return "SalesComparisonYOYRequest{" +
                "concessionaireId='" + concessionaireId + '\'' +
                ", property_id='" + property_id + '\'' +
                '}';
    }
}
