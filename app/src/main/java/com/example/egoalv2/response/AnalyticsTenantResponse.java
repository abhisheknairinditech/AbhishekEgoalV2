package com.example.egoalv2.response;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class AnalyticsTenantResponse {
    @SerializedName("total_net_sales")
    private double totalNetSales;

    @SerializedName("total_transaction_count")
    private int totalTransactionCount;

    @SerializedName("tenant_group_name")
    private String tenantGroupName;

    @SerializedName("tenant_store_name")
    private String tenantStoreName;

    @SerializedName("tenant_code")
    private String tenantCode;

    @SerializedName("floor")
    private String floor;

    @SerializedName("property_name")
    private String propertyName;

    public double getTotal_net_sales() {
        return totalNetSales;
    }

    public int getTotal_transaction_count() {
        return totalTransactionCount;
    }

    public String getTenant_group_name() {
        return tenantGroupName;
    }

    public String getTenant_store_name() {
        return tenantStoreName;
    }

    public String getTenant_code() {
        return tenantCode;
    }

    public String getFloor() {
        return floor;
    }

    public String getProperty_name() {
        return propertyName;
    }

    @NonNull
    @Override
    public String toString() {
        return "AnalyticsTenantResponse{" +
                "total_net_sales=" + totalNetSales +
                ", total_transaction_count=" + totalTransactionCount +
                ", tenant_group_name='" + tenantGroupName + '\'' +
                ", tenant_store_name='" + tenantStoreName+ '\'' +
                ", tenant_code='" + tenantCode + '\'' +
                ", floor='" + floor + '\'' +
                ", property_name='" + propertyName + '\'' +
                '}';
    }
}
