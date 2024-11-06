package com.example.egoalv2.response;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class DashboardResponse {
    @SerializedName("totalSales")
    private String totalSales;
    @SerializedName("totalTenants")
    private String totalTenants;
    @SerializedName("totalStores")
    private String totalStores;
    @SerializedName("totalKiosks")
    private String totalKiosks;

    // Add other fields as needed

    public String getTotalSales() {
        return totalSales;
    }

    public String getTotalTenants() {
        return totalTenants;
    }

    public String getTotalStores() {
        return totalStores;
    }

    public String getTotalKiosks() {
        return totalKiosks;
    }

    @NonNull
    @Override
    public String toString() {
        return "DashboardResponse{" +
                "totalSales='" + totalSales + '\'' +
                ", totalTenants='" + totalTenants + '\'' +
                ", totalStores='" + totalStores + '\'' +
                ", totalKiosks='" + totalKiosks + '\'' +
                '}';
    }
}
