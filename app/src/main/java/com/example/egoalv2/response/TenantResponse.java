package com.example.egoalv2.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TenantResponse {
    private String _id;
    private String tenant_store_name;// This represents the tenant data
    private TenantProperty tenant_property_id;

    public TenantProperty getTenant_property_id() {
        return tenant_property_id;
    }

    public void setTenant_property_id(TenantProperty tenant_property_id) {
        this.tenant_property_id = tenant_property_id;
    }

    public String get_id() {
        return _id;
    }

    public String getTenant_store_name() {
        return tenant_store_name;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setTenant_store_name(String tenant_store_name) {
        this.tenant_store_name = tenant_store_name;
    }

    public static class Tenant {
        private double tenant_charge1;
        private double tenant_charge2;
        private double tenant_charge3;
        private double tenant_charge4;
        private double tenant_charge5;
        private String _id;
        private TenantConcessionaire tenant_concessionaire_id;
        private TenantCountry tenant_country_id;
        private TenantProperty tenant_property_id;
        private TenantCategory tenant_category_id;
        private TenantSubcategory tenant_subcategory_id;
        private String tenant_store_name;
        private String tenant_brand_name;
        private String tenant_email_id;
        private String tenant_password;
        private String tenant_unit_code;
        private String tenant_floor;
        private String tenant_group_name;
        private String tenant_lease_startdate;
        private String tenant_lease_enddate;
        private String tenant_created_date;
        private TenantCreatedBy tenant_created_by;
        private String tenant_login_id;
        private double tenant_carpet_area;
        private double tenant_chargable_area;
        private double tenant_revenue_share;
        private TenantRevenueShareMethod tenant_revenue_share_method_id;
        private boolean tenant_manual_flag;
        private String tenant_code;
        private String tenant_store_type;
        private String tenant_franchisee;
        private String tenant_current_status;
        private String tenant_location;
        private boolean tenant_disable;
        private String tenant_status;
        private int string;
        private TenantDates tenant_dates;
        private String tenant_live_date;
        private String tenant_api_key;
        private double tenant_base_rent;
        private String tenant_modified_date;
        private String updatedAt;
        private String tenant_concessionaire_code;
        private String tenant_mall_code;

        // Getters and setters for all fields
        // ...

        public double getTenant_charge1() {
            return tenant_charge1;
        }

        public double getTenant_charge2() {
            return tenant_charge2;
        }

        public double getTenant_charge3() {
            return tenant_charge3;
        }

        public double getTenant_charge4() {
            return tenant_charge4;
        }

        public double getTenant_charge5() {
            return tenant_charge5;
        }

        public String get_id() {
            return _id;
        }

        public TenantConcessionaire getTenant_concessionaire_id() {
            return tenant_concessionaire_id;
        }

        public TenantCountry getTenant_country_id() {
            return tenant_country_id;
        }

        public TenantProperty getTenant_property_id() {
            return tenant_property_id;
        }

        public TenantCategory getTenant_category_id() {
            return tenant_category_id;
        }

        public TenantSubcategory getTenant_subcategory_id() {
            return tenant_subcategory_id;
        }

        public String getTenant_store_name() {
            return tenant_store_name;
        }

        public String getTenant_brand_name() {
            return tenant_brand_name;
        }

        public String getTenant_email_id() {
            return tenant_email_id;
        }

        public String getTenant_password() {
            return tenant_password;
        }

        public String getTenant_unit_code() {
            return tenant_unit_code;
        }

        public String getTenant_floor() {
            return tenant_floor;
        }

        public String getTenant_group_name() {
            return tenant_group_name;
        }

        public String getTenant_lease_startdate() {
            return tenant_lease_startdate;
        }

        public String getTenant_lease_enddate() {
            return tenant_lease_enddate;
        }

        public String getTenant_created_date() {
            return tenant_created_date;
        }

        public TenantCreatedBy getTenant_created_by() {
            return tenant_created_by;
        }

        public String getTenant_login_id() {
            return tenant_login_id;
        }

        public double getTenant_carpet_area() {
            return tenant_carpet_area;
        }

        public double getTenant_chargable_area() {
            return tenant_chargable_area;
        }

        public double getTenant_revenue_share() {
            return tenant_revenue_share;
        }

        public TenantRevenueShareMethod getTenant_revenue_share_method_id() {
            return tenant_revenue_share_method_id;
        }

        public boolean isTenant_manual_flag() {
            return tenant_manual_flag;
        }

        public String getTenant_code() {
            return tenant_code;
        }

        public String getTenant_store_type() {
            return tenant_store_type;
        }

        public String getTenant_franchisee() {
            return tenant_franchisee;
        }

        public String getTenant_current_status() {
            return tenant_current_status;
        }

        public String getTenant_location() {
            return tenant_location;
        }

        public boolean isTenant_disable() {
            return tenant_disable;
        }

        public String getTenant_status() {
            return tenant_status;
        }

        public int getString() {
            return string;
        }

        public TenantDates getTenant_dates() {
            return tenant_dates;
        }

        public String getTenant_live_date() {
            return tenant_live_date;
        }

        public String getTenant_api_key() {
            return tenant_api_key;
        }

        public double getTenant_base_rent() {
            return tenant_base_rent;
        }

        public String getTenant_modified_date() {
            return tenant_modified_date;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public String getTenant_concessionaire_code() {
            return tenant_concessionaire_code;
        }

        public String getTenant_mall_code() {
            return tenant_mall_code;
        }
    }

    public static class TenantConcessionaire {
        private String _id;
        private String concessionaire_name;
        private String concessionaire_code;

        // Getters and setters
        // ...

        public String get_id() {
            return _id;
        }

        public String getConcessionaire_name() {
            return concessionaire_name;
        }

        public String getConcessionaire_code() {
            return concessionaire_code;
        }
    }

    public static class TenantCountry {
        private String _id;
        private String country_name;

        // Getters and setters
        // ...

        public String get_id() {
            return _id;
        }

        public String getCountry_name() {
            return country_name;
        }
    }

    public static class TenantProperty {
        private String _id;
        private String property_name;
        private String property_mall_code;

        public String get_id() {
            return _id;
        }

        public String getProperty_name() {
            return property_name;
        }

        public String getProperty_mall_code() {
            return property_mall_code;
        }

    }

    public static class TenantCategory {
        private String _id;
        private String category_name;

        // Getters and setters
        // ...

        public String get_id() {
            return _id;
        }

        public String getCategory_name() {
            return category_name;
        }
    }

    public static class TenantSubcategory {
        private String _id;
        private String subcategory_name;

        // Getters and setters
        // ...

        public String get_id() {
            return _id;
        }

        public String getSubcategory_name() {
            return subcategory_name;
        }
    }

    public static class TenantCreatedBy {
        private String _id;
        private String userFirstName;

        // Getters and setters
        // ...

        public String get_id() {
            return _id;
        }

        public String getUserFirstName() {
            return userFirstName;
        }
    }

    public static class TenantRevenueShareMethod {
        private String _id;
        private String revenue_method_name;

        // Getters and setters
        // ...
    }

    public static class TenantDates {
        private String _id;
        private String first_transaction_date;
        private String tenant_id;

        // Getters and setters
        // ...
    }

// Getters and setters for Tenant class fields
// ...

}
