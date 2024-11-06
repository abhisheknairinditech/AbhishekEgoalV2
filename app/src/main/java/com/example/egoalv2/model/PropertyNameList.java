package com.example.egoalv2.model;

public class PropertyNameList {
    private String propertyName;
    private String propertyId;

    public PropertyNameList(String propertyName, String propertyId) {
        this.propertyName = propertyName;
        this.propertyId = propertyId;
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

    @Override
    public String toString() {
        return "PropertyNameList{" +
                "propertyName='" + propertyName + '\'' +
                ", propertyId='" + propertyId + '\'' +
                '}';
    }
}
