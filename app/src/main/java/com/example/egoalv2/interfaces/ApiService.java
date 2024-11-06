package com.example.egoalv2.interfaces;

import com.example.egoalv2.Config;
import com.example.egoalv2.request.AnalyticsTenantRequest;
import com.example.egoalv2.request.DashboardRequest;
import com.example.egoalv2.request.LoginRequest;
import com.example.egoalv2.request.SalesComparisonRequest;
import com.example.egoalv2.request.SalesComparisonYOYRequest;
import com.example.egoalv2.request.SubcategorySalesRequest;
import com.example.egoalv2.request.TenantRequest;
import com.example.egoalv2.request.TopFiveTenantsRequest;
import com.example.egoalv2.request.TopPerformerRequest;
import com.example.egoalv2.response.AnalyticsTenantResponse;
import com.example.egoalv2.response.DashboardResponse;
import com.example.egoalv2.request.PropertyRequest;
import com.example.egoalv2.response.LoginResponse;
import com.example.egoalv2.response.PropertyResponse;
import com.example.egoalv2.response.SalesComparisonResponse;
import com.example.egoalv2.response.SalesComparisonYOYResponse;
import com.example.egoalv2.response.SubcategorySalesResponse;
import com.example.egoalv2.response.TenantResponse;
import com.example.egoalv2.response.TopFiveTenantsRespons;
import com.example.egoalv2.response.TopPerformerResponse;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiService {
    // API to login
    @POST(Config.LOGIN_API)
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST(Config.GET_DETAILS)
    Call<DashboardResponse> getDashboardData(@Header("Authorization") String token, @Body DashboardRequest dashboardRequest);

    @POST(Config.GET_PROPERTY_BY_CONCESSIONAIRE)
    Call<List<PropertyResponse.Property>> getPropertyNames(@Header("Authorization") String token,@Body PropertyRequest request);

    // API to get tenants
    @POST(Config.GET_TENANTS_BY_CONCESSIONAIRE)
    Call<List<TenantResponse.Tenant>> getTenants(@Header("Authorization")String token, @Body TenantRequest tenantRequest);

    @POST(Config.GET_SALES_COMPARISON_MOM_TABLE)
    Call<SalesComparisonResponse> getSalesComparisonData(@Header("Authorization")String token, @Body SalesComparisonRequest request);

    @POST(Config.GET_SUBCATEGORY_SALES)
    Call<SubcategorySalesResponse> getSubcategorySales(@Header("Authorization")String token, @Body SubcategorySalesRequest request);

    @POST(Config.GET_TOP_5_Tenant)
    Call<TopFiveTenantsRespons> getTopFiveTenants(@Header("Authorization")String token, @Body TopFiveTenantsRequest request);

    @POST(Config.SALES_COMPARISON_YOY_TABLE)
    Call<SalesComparisonYOYResponse> getSalesComparisonYOY(@Header("Authorization")String token, @Body SalesComparisonYOYRequest request);

    @POST(Config.GET_DAY_WISE_CONSOLIDATE_REPORT)
    Call<List<AnalyticsTenantResponse>> getdaywiseconsolidatedreport(@Header("Authorization")String token, @Body AnalyticsTenantRequest request);

    @POST(Config.TOP_PERFOMER)
    Call<TopPerformerResponse> getTopPerfomer(@Header("Authorization")String token, @Body TopPerformerRequest request);
}
