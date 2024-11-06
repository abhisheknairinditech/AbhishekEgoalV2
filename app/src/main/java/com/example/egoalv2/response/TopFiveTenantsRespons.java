package com.example.egoalv2.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TopFiveTenantsRespons {
    @SerializedName("topFiveTenants")
    private List<TenantData> topFiveTenants;

    public List<TenantData> getTopFiveTenants() {
        return topFiveTenants;
    }

    public void setTopFiveTenants(List<TenantData> topFiveTenants) {
        this.topFiveTenants = topFiveTenants;
    }

    public static class TenantData {

        @SerializedName("tenant_store_name")
        private String tenant_store_name;

        @SerializedName("net_sales")
        private double net_sales;

        public String getTenant_store_name() {
            return tenant_store_name;
        }

        public void setTenant_store_name(String tenant_store_name) {
            this.tenant_store_name = tenant_store_name;
        }

        public double getNet_sales() {
            return net_sales;
        }

        public void setNet_sales(double net_sales) {
            this.net_sales = net_sales;
        }
    }
}
