package com.example.egoalv2.model;

public class TenantProperty {
    private final String tenant_store_name;
    private final String property_name;
    private String tenant_id;


    public TenantProperty(String tenant_store_name, String property_name, String tenant_id) {
        this.tenant_store_name = tenant_store_name;
        this.property_name = property_name;
        this.tenant_id=tenant_id;
    }

    public String getTenant_id() {
        return tenant_id;
    }

    public String getTenant_store_name() {
        return tenant_store_name;
    }

    public String getProperty_name() {
        return property_name;
    }
    @Override
    public String toString() {
        // This method will define how the object is displayed in the AutoCompleteTextView
        return tenant_store_name + "\n (" + property_name + ")";
    }
}
