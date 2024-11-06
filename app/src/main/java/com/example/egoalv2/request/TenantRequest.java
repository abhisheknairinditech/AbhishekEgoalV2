package com.example.egoalv2.request;

public class TenantRequest {
    private String tenant_concessionaire_id;
    private String property_id;

    public TenantRequest(String tenant_concessionaire_id, String property_id) {
        this.tenant_concessionaire_id = tenant_concessionaire_id;
        this.property_id = property_id;
    }

    @Override
    public String toString() {
        return "TenantRequest{" +
                "tenant_concessionaire_id='" + tenant_concessionaire_id + '\'' +
                ", property_id='" + property_id + '\'' +
                '}';
    }
}
