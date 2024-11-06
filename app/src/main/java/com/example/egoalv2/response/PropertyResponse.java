package com.example.egoalv2.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PropertyResponse {
    @SerializedName("properties")
    private List<Property> properties;

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    public static class Property {


        @SerializedName("property_name")
        private String propertyName;

        @SerializedName("_id")
        private String propertyId;

        public Property(String propertyName) {
            this.propertyName=propertyName;
        }

        public String getPropertyName() {
            return propertyName;
        }

        public void setPropertyName(String propertyName) {
            this.propertyName = propertyName;
        }

        public String getPropertyId() {
            return propertyId;
        }

        public void setPropertyId(String propertyId) {
            this.propertyId = propertyId;
        }
    }
}
