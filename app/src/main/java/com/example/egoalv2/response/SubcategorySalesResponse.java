package com.example.egoalv2.response;

import java.util.List;

public class SubcategorySalesResponse {
    private List<SubcategorySales> subcategorywiseSales;

    public List<SubcategorySales> getSubcategorywiseSales() {
        return subcategorywiseSales;
    }

    public static class SubcategorySales {
        private String subcategory_name;
        private double net_sales;

        public String getSubcategory_name() {
            return subcategory_name;
        }

        public double getNet_sales() {
            return net_sales;
        }
    }
}
