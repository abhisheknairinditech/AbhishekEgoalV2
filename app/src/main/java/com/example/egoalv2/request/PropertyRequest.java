package com.example.egoalv2.request;

import com.google.gson.annotations.SerializedName;

public class PropertyRequest {
    @SerializedName("property_concessionaire_id")
    private String propertyConcessionaireId;
    @SerializedName("property_id")
    private String propertyId;

    public PropertyRequest(String propertyConcessionaireId) {
        this.propertyConcessionaireId = propertyConcessionaireId;
        this.propertyId = propertyId;
    }
}
