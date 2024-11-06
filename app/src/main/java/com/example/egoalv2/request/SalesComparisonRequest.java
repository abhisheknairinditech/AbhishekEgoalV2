package com.example.egoalv2.request;

public class SalesComparisonRequest {
    private String concessionaire_id;
    private String property_id;

    public SalesComparisonRequest(String concessionaire_id, String property_id) {
        this.concessionaire_id = concessionaire_id;
        this.property_id = property_id;
    }

    @Override
    public String toString() {
        return "SalesComparisonRequest{" +
                "concessionaire_id='" + concessionaire_id + '\'' +
                ", property_id='" + property_id + '\'' +
                '}';
    }
}
