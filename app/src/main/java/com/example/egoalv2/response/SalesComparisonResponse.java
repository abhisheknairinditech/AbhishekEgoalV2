package com.example.egoalv2.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SalesComparisonResponse {
    @SerializedName("currentYearSales")
    private List<SalesData> currentYearSales;

    @SerializedName("previousYearSales")
    private List<SalesData> previousYearSales;

    public List<SalesData> getCurrentYearSales() {
        return currentYearSales;
    }

    public List<SalesData> getPreviousYearSales() {
        return previousYearSales;
    }
    @Override
    public String toString() {
        return "SalesComparisonResponse{" +
                "currentYearSales=" + currentYearSales +
                ", previousYearSales=" + previousYearSales +
                '}';
    }
    public class SalesData {
        @SerializedName("month")
        private String month;

        @SerializedName("totalSales")
        private double totalSales;

        @SerializedName("percentageChange")
        private String percentageChange;

        public String getMonth() {
            return month;
        }

        public double getTotalSales() {
            return totalSales;
        }

        public String getPercentageChange() {
            return percentageChange;
        }
        @Override
        public String toString() {
            return "SalesData{" +
                    "month='" + month + '\'' +
                    ", totalSales=" + totalSales +
                    ", percentageChange='" + percentageChange + '\'' +
                    '}';
        }
    }


}
