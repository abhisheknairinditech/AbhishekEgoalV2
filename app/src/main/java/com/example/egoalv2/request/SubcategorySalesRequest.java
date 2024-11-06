package com.example.egoalv2.request;

public class SubcategorySalesRequest {
    private String concessionaire_id;
    private String category_name;
    private String property_id;

    public SubcategorySalesRequest(String concessionaire_id, String category_name, String property_id) {
        this.concessionaire_id = concessionaire_id;
        this.category_name = category_name;
        this.property_id = property_id;
    }

    @Override
    public String toString() {
        return "SubcategorySalesRequest{" +
                "concessionaire_id='" + concessionaire_id + '\'' +
                ", category_name='" + category_name + '\'' +
                ", property_id='" + property_id + '\'' +
                '}';
    }
}
