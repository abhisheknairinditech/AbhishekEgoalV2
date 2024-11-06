package com.example.egoalv2;

public class Config {

    // Base URL for the API
//    public static final String BASE_URL = "http://13.235.215.57:8081/api/";
    public static final String BASE_URL = "https://app.egoalprime.com:4443/api/";

    // Auth APIs
    public static final String LOGIN_API = BASE_URL + "auth/login";

    // Property APIs
    public static final String GET_PROPERTY_BY_CONCESSIONAIRE = BASE_URL + "property/getpropertybyconcessionaire";

    // Tenant APIs
    public static final String GET_TENANTS_BY_CONCESSIONAIRE = BASE_URL + "tenant/getenantsbyconcessionaire";

    public static final String GET_DETAILS = BASE_URL + "androidapp/getdetails";

    public static final String GET_SALES_COMPARISON_MOM_TABLE = BASE_URL + "dashboard/salescomparisonmomtable";

    public static final String GET_SUBCATEGORY_SALES = BASE_URL + "dashboard/subcategorysales";

    public static final String GET_TOP_5_Tenant = BASE_URL + "dashboard/topfivetenants";

    public static final String SALES_COMPARISON_YOY_TABLE = BASE_URL + "dashboard/salescomparisonyoytable";

    public static final String GET_DAY_WISE_CONSOLIDATE_REPORT = BASE_URL + "reports/getdaywiseconsolidatedreport";

    public static final String TOP_PERFOMER = BASE_URL + "dashboard/topperformer";
}
