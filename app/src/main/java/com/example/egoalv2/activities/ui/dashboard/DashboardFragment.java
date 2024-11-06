package com.example.egoalv2.activities.ui.dashboard;

import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.egoalv2.activities.MainActivity;
import com.example.egoalv2.databinding.FragmentDashboardBinding;

import android.view.MenuItem;

import androidx.appcompat.widget.PopupMenu;

import androidx.core.widget.NestedScrollView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.egoalv2.MillionsValueFormatter;
import com.example.egoalv2.interfaces.ApiClient;
import com.example.egoalv2.R;
import com.example.egoalv2.adapters.PropertyAdapter;
import com.example.egoalv2.adapters.TenantPropertyAdapter;
import com.example.egoalv2.interfaces.ApiService;
import com.example.egoalv2.model.PropertyNameList;
import com.example.egoalv2.model.TenantProperty;
import com.example.egoalv2.request.DashboardRequest;
import com.example.egoalv2.request.PropertyRequest;
import com.example.egoalv2.request.SalesComparisonRequest;
import com.example.egoalv2.request.SubcategorySalesRequest;
import com.example.egoalv2.request.TenantRequest;
import com.example.egoalv2.request.TopFiveTenantsRequest;
import com.example.egoalv2.request.TopPerformerRequest;
import com.example.egoalv2.response.DashboardResponse;
import com.example.egoalv2.response.PropertyResponse;
import com.example.egoalv2.response.SalesComparisonResponse;
import com.example.egoalv2.response.SubcategorySalesResponse;
import com.example.egoalv2.response.TenantResponse;
import com.example.egoalv2.response.TopFiveTenantsRespons;
import com.example.egoalv2.adapters.TopPerformerSliderAdapter;
import com.example.egoalv2.response.TopPerformerResponse;
import com.example.egoalv2.response.TopPerformerResponse.TopPerformer;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.library.foysaltech.smarteist.autoimageslider.SliderView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;


public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private TextView usernameTextView, netSalesTextView, tenantTextView, storeTextView, kiosksTextView;
    private AutoCompleteTextView propertyAutoComplete, tenantAutoComplete;
    private AppCompatSpinner dateRangeSpinner;
    private String sp_firsname, sp_concessionaireID, sp_sessionToken, sp_propertyaccess;
    private String selectedProperty, selectedTenant, seletedDateRange, selectedPropertyID = "", selectedTenantID = "";

    private LinearLayout linear1;
    private String start_date, end_date, current_year, previous_year;
    private EditText startDateEditText, endDateEditText;
    private List<PropertyResponse.Property> properties;
    private ProgressDialog progressDialog;
    private Spinner categorySpinner;
    private ImageView menuIcon;
    private SliderView sliderView;
    private List<SpannableString> performerList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel homeViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        progressDialog = new ProgressDialog(getActivity());

        linear1 = binding.linear;
        categorySpinner = binding.categorySpinner;
        menuIcon = binding.menuIcon;
        usernameTextView = binding.username;
        netSalesTextView = binding.dashboardNetsales;
        tenantTextView = binding.dashboardTenant;
        propertyAutoComplete = binding.propertyAutocomplete;
        tenantAutoComplete = binding.tenantAutocomplete;
        dateRangeSpinner = binding.dateRangeSpinner;
        startDateEditText = binding.startDate;
        endDateEditText = binding.endDate;
        storeTextView = binding.dashboardStores;
        kiosksTextView = binding.dashboardKiosks;
        sliderView= binding.topPerformerViewPager;


        dateRangeSpinner.setPopupBackgroundResource(android.R.color.white);

//        Log.d("STARTDATEENDDATE", "Start Date: " + start_date + "End Date: " + end_date);
        SharedPreferences sharedPref = getActivity().getSharedPreferences("UserDetails", getActivity().MODE_PRIVATE);
        sp_firsname = sharedPref.getString("firstname", "User");
        sp_concessionaireID = sharedPref.getString("concessionaireId", "");
        sp_sessionToken = sharedPref.getString("sessiontoken", "");
        sp_propertyaccess = sharedPref.getString("propertyaccess", "");
        usernameTextView.setText(sp_firsname);

        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a popup menu
                PopupMenu popupMenu = new PopupMenu(getActivity(), v);
                popupMenu.getMenuInflater().inflate(R.menu.dashboard_menu, popupMenu.getMenu());

                // Handle menu item clicks
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();

                        if (id == R.id.logout) {
                            // Implement logout logic here
                            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserDetails", getActivity().MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.clear(); // Clear all saved data in SharedPreferences
                            editor.apply(); // Apply changes

                            // Redirect to login activity after logout
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            Toast.makeText(getActivity(), "Logged Out", Toast.LENGTH_SHORT).show();
                            return true;
                        }


                        return false;
                    }
                });
                popupMenu.show();
            }
        });


        startDateEditText.setOnClickListener(v -> showDatePickerDialog(startDateEditText));
        endDateEditText.setOnClickListener(v -> showDatePickerDialog(endDateEditText));
//        Toast.makeText(this, sp_propertyID, Toast.LENGTH_SHORT).show();
//        Log.e("PropertyAccess", sp_propertyaccess);


        showDateRange();
        fetchTopPerformers(sp_sessionToken, sp_concessionaireID);
        showPropertyNames(sp_sessionToken, sp_concessionaireID, sp_propertyaccess);
        showTenantsName(sp_sessionToken, sp_concessionaireID, selectedPropertyID);

        return root;
    }


    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.dashboard_menu, menu);
//        return true;
//    }
    private void fetchTopPerformers(String spSessionToken, String spConcessionaireID) {
        TopPerformerRequest topPerformerRequest = new TopPerformerRequest(sp_concessionaireID);
        ApiService apiService = ApiClient.getApiService();
        Call<TopPerformerResponse> call = apiService.getTopPerfomer(spSessionToken, topPerformerRequest);

        call.enqueue(new Callback<TopPerformerResponse>() {
            @Override
            public void onResponse(Call<TopPerformerResponse> call, Response<TopPerformerResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    TopPerformerResponse responseData = response.body();
                    TopPerformerResponse.TopPerformer topPerformerYear = responseData.getTopPerformerYear();
                    TopPerformerResponse.TopPerformer topPerformerMonth = responseData.getTopPerformerMonth();
                    TopPerformer topPerformerYesterday = responseData.getTopPerformerYesterday();

//                    String yearText = "Top performer of the year is " +
//                            topPerformerYear.getTenant_name() +
//                            " with the sale of " + topPerformerYear.getTotalNetSales() + " Millions.";
//
//                    String monthText = "Top performer of the month is " +
//                            topPerformerMonth.getTenant_name() +
//                            " with the sale of " + topPerformerMonth.getTotalNetSales() + " Millions.";
//
//                    String yesterdayText = "Top performer of the day is " +
//                            topPerformerYesterday.getTenant_name() +
//                            " with the sale of " + topPerformerYesterday.getTotalNetSales() + " Millions.";
                    SpannableString yearText = getFormattedText(
                            "Top performer of the year is ",
                            topPerformerYear.getTenant_name(),
                            topPerformerYear.getTotalNetSales(),
                            ".");

                    SpannableString monthText = getFormattedText(
                            "Top performer of the month is ",
                            topPerformerMonth.getTenant_name(),
                            topPerformerMonth.getTotalNetSales(),
                            ".");

                    SpannableString yesterdayText = getFormattedText(
                            "Top performer of the day is ",
                            topPerformerYesterday.getTenant_name(),
                            topPerformerYesterday.getTotalNetSales(),
                            ".");
                    performerList.add(yearText);
                    performerList.add(monthText);
                    performerList.add(yesterdayText);

                    setupsliderView();
                } else {
                    Toast.makeText(getContext(), "Failed to get data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TopPerformerResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Failed to fetch top performers", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private SpannableString getFormattedText(String prefix, String name, String sale, String suffix) {
        String fullText = prefix + name + " with the sale of " + sale + " Millions" + suffix;
        SpannableString spannableString = new SpannableString(fullText);

        // Make tenant name bold, italic, and change color
        int nameStart = prefix.length();
        int nameEnd = nameStart + name.length();
        spannableString.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), nameStart, nameEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), R.color.colorAccent)), nameStart, nameEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Make totalNetSales bold, italic, and change color
        int saleStart = nameEnd + " with the sale of ".length();
        int saleEnd = saleStart + sale.length();
        spannableString.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), saleStart, saleEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), R.color.colorAccent)), saleStart, saleEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }
    private void setupsliderView() {
        TopPerformerSliderAdapter sliderViewAdapter = new TopPerformerSliderAdapter(performerList);
        sliderView.setSliderAdapter(sliderViewAdapter);
        sliderView.startAutoCycle();
    }

    private void showPropertyNames(String spSessionToken, String spConcessionaireID, String spPropertyID) {
        PropertyRequest propertyRequest = new PropertyRequest(spConcessionaireID);

//        Toast.makeText(DashboardActivity.this, spConcessionaireID+"\n"+spPropertyID, Toast.LENGTH_SHORT).show();
        ApiService apiService = ApiClient.getApiService();
        Call<List<PropertyResponse.Property>> call = apiService.getPropertyNames(spSessionToken, propertyRequest);

        call.enqueue(new Callback<List<PropertyResponse.Property>>() {
            @Override
            public void onResponse(Call<List<PropertyResponse.Property>> call, Response<List<PropertyResponse.Property>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    properties = response.body();
                    ArrayList<String> propertyNames = new ArrayList<>();
                    ArrayList<String> propertyIDs = new ArrayList<>();
                    propertyNames.add("All property"); // Default item
                    propertyIDs.add("");
                    for (PropertyResponse.Property property : properties) {
                        propertyNames.add(property.getPropertyName());
                        propertyIDs.add(property.getPropertyId());
                    }
//                    Log.d("PropertyNames", propertyNames.toString());
                    populatePropertySpinner(propertyNames, propertyIDs);
                } else {
                    Toast.makeText(getContext(), "Error fetching data", Toast.LENGTH_SHORT).show();
//                    Log.e("ERRORFETCHINGDATA", response.toString());
                }
            }

            @Override
            public void onFailure(Call<List<PropertyResponse.Property>> call, Throwable t) {
//                Log.e("ERRORONFAILURE", Objects.requireNonNull(t.getMessage()));
//                Toast.makeText(DashboardActivity.this, "Hi", Toast.LENGTH_SHORT).show();
                Toast.makeText(getContext(), "Property Name Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populatePropertySpinner(ArrayList<String> propertyNames, ArrayList<String> propertyIDs) {
//        Log.d("PropertyId", propertyNames.toString());
        ArrayList<PropertyNameList> propertyList = new ArrayList<>();
        for (int i = 0; i < propertyNames.size(); i++) {
            propertyList.add(new PropertyNameList(propertyNames.get(i).toString(), propertyIDs.get(i).toString()));
//            Log.d("PropertyNAME&Id", propertyNames.get(i).toString() + "  " + propertyIDs.get(i).toString());
        }
// Create the custom adapter
        PropertyAdapter propertyAdapter = new PropertyAdapter(getContext(), propertyList);
        propertyAutoComplete.setAdapter(propertyAdapter);
        propertyAutoComplete.setThreshold(1); // Display dropdown after 1 character input

        propertyAutoComplete.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    propertyAutoComplete.showDropDown(); // Show the dropdown on focus
                }
            }
        });

        propertyAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                propertyAutoComplete.showDropDown();
                PropertyNameList selectedProperty = (PropertyNameList) parent.getItemAtPosition(position);

                selectedPropertyID = selectedProperty.getPropertyId();
                showTenantsName(sp_sessionToken, sp_concessionaireID, selectedPropertyID);
                fetchDashboardData(sp_sessionToken, sp_concessionaireID, start_date, end_date, "", selectedPropertyID, sp_propertyaccess);
            }
        });
    }

    private void showTenantsName(String spSessionToken, String spConcessionaireID, String spPropertyID) {
        if (selectedPropertyID == null || selectedPropertyID.equals("All property")) {
            selectedPropertyID = ""; // Pass empty string to fetch all tenants
        }
        TenantRequest tenantRequest = new TenantRequest(spConcessionaireID, selectedPropertyID);
Log.d("TenantRequest", tenantRequest.toString());
        ApiService apiService = ApiClient.getApiService();
        Call<List<TenantResponse.Tenant>> call = apiService.getTenants(spSessionToken, tenantRequest);
//        Log.d("DASHBOARDCALL", call.toString());
//        Log.e("TenantNames",spConcessionaireID+"  "+selectedPropertyID);
        call.enqueue(new Callback<List<TenantResponse.Tenant>>() {
            @Override
            public void onResponse(@NonNull Call<List<TenantResponse.Tenant>> call, @NonNull Response<List<TenantResponse.Tenant>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<TenantResponse.Tenant> tenantList = response.body();

                    ArrayList<String> tenantNames = new ArrayList<>();
                    ArrayList<String> tenantIds = new ArrayList<>();
                    ArrayList<String> tenantPropertyNames = new ArrayList<>();

                    // Add "All Tenant" as the first item
                    tenantNames.add("All Tenant");
                    tenantIds.add("");
                    tenantPropertyNames.add("");

                    for (TenantResponse.Tenant tenant : tenantList) {
                        // Add tenant names, tenant IDs, and property IDs to the lists
                        tenantNames.add(tenant.getTenant_store_name());
                        tenantIds.add(tenant.get_id());
                        tenantPropertyNames.add(tenant.getTenant_property_id().getProperty_name());

                    }
                    populatetenantSpinner(tenantNames, tenantPropertyNames, tenantIds);
                } else {
                    Toast.makeText(getContext(), "Failed to fetch tenants", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<TenantResponse.Tenant>> call, Throwable t) {
                Log.e("ERRORSHOWTENANT", Objects.requireNonNull(t.getMessage()));
                Toast.makeText(getContext(), "Tenants Name Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populatetenantSpinner(ArrayList<String> tenantNames, ArrayList<String> tenantPropertyNames, ArrayList<String> tenantIds) {
        // Clear any previous data in the tenantAutoComplete
        tenantAutoComplete.setText(""); // Clear the previous selection
        tenantAutoComplete.clearListSelection(); // Clear previous selection state

        // Create a list of TenantProperty objects
        ArrayList<TenantProperty> tenantPropertyList = new ArrayList<>();
        for (int i = 0; i < tenantNames.size(); i++) {
            tenantPropertyList.add(new TenantProperty(tenantNames.get(i), tenantPropertyNames.get(i), tenantIds.get(i)));
//            Log.d("TENANTNAME&Id", tenantNames.get(i) + "  " + tenantPropertyNames.get(i) + " " + tenantIds.get(i));
        }
        //Log.d("tenantPropertyList", tenantPropertyList.toString());

        TenantPropertyAdapter tenantAdapter = new TenantPropertyAdapter(getContext(), tenantPropertyList);
        tenantAutoComplete.setAdapter(tenantAdapter);
        tenantAutoComplete.setThreshold(1);

        tenantAutoComplete.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    tenantAutoComplete.showDropDown(); // Show the dropdown on focus
                }
            }
        });

        tenantAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tenantAutoComplete.showDropDown();
                TenantProperty selectedTenantProperty = (TenantProperty) parent.getItemAtPosition(position);
                selectedTenant = selectedTenantProperty.getTenant_store_name();
                selectedTenantID = selectedTenantProperty.getTenant_id(); // Access tenant ID from the separate list

                // Do something with the selected tenant
//                Toast.makeText(getApplicationContext(), "Selected Tenant: " + selectedTenant + ", Property: " + selectedTenantProperty.getProperty_name(), Toast.LENGTH_SHORT).show();

//                Log.d("SELECTEDTENANTID", "Session Token: " + sp_sessionToken + "\nConcessionare ID: " + sp_concessionaireID + "\nStart Date: " + start_date +
//                        "\nEnd Date: " + end_date + "\nSelected Tenant TD " + selectedTenantID + "\nSelected Property ID " + selectedPropertyID + "\nProperty Access" +
//                        " " + sp_propertyaccess);
                fetchDashboardData(sp_sessionToken, sp_concessionaireID, start_date, end_date, selectedTenantID, selectedPropertyID, sp_propertyaccess);
            }
        });
    }

    private void fetchDashboardData(String spSessionToken, String spConcessionaireID, String start_date, String end_date, String selectedTenantID, String selectedPropertyID, String sp_propertyaccess) {

        String start_date1 = startDateEditText.getText().toString();
        String end_date1 = endDateEditText.getText().toString();
        if (start_date1.isEmpty() && end_date1.isEmpty()) {
            start_date1 = start_date;
            end_date1 = end_date;
        }
        DashboardRequest dashboardRequest = new DashboardRequest(spConcessionaireID, start_date1, end_date1, selectedPropertyID, selectedTenantID, sp_propertyaccess);
//        Log.d("DASHBOARDERROR", dashboardRequest.toString());
        ApiService apiService = ApiClient.getApiService();
        Call<DashboardResponse> call = apiService.getDashboardData(spSessionToken, dashboardRequest);

        call.enqueue(new Callback<DashboardResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<DashboardResponse> call, @NonNull Response<DashboardResponse> response) {
                if (response.isSuccessful() && response.body() != null) {

                    DashboardResponse dashboardResponse = response.body();
//                    Log.d("DASHBOARDRESPONSE", "DASHBOARD REQUEST: " + dashboardRequest + "\nDASHBOARD RESPONSE: " + dashboardResponse);
                    netSalesTextView.setText(dashboardResponse.getTotalSales());
                    tenantTextView.setText(dashboardResponse.getTotalTenants());
                    storeTextView.setText(dashboardResponse.getTotalStores());
                    kiosksTextView.setText(dashboardResponse.getTotalKiosks());

                    getSalesComparisonData(sp_sessionToken, sp_concessionaireID, selectedPropertyID);
                } else {
                    Log.e("DASHBOARDERROR", dashboardRequest.toString());
                    Toast.makeText(getContext(), "Failed to fetch Dashboard Details", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DashboardResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Dashboard Error" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void showDateRange() {
        // Populate the spinner with options
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_spinner_item,
                new String[]{"Yesterday", "This Week", "This Month", "This Year", "Custom DateRange"}
        ) {
            @NonNull
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setTextColor(Color.BLACK);
                return textView;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getDropDownView(position, convertView, parent);
                textView.setTextColor(Color.BLACK);
                return textView;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dateRangeSpinner.setAdapter(adapter);

        dateRangeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateDateRange(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    private void updateDateRange(int position) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());

        switch (position) {
            case 0: // Yesterday
                calendar.add(Calendar.DAY_OF_YEAR, -1);
                String yesterday = sdf.format(calendar.getTime());
                start_date = yesterday;
                end_date = yesterday;
                startDateEditText.setText(yesterday);
                endDateEditText.setText(yesterday);
                startDateEditText.setVisibility(View.GONE);
                endDateEditText.setVisibility(View.GONE);
                linear1.setVisibility(View.GONE);
                fetchDashboardData(sp_sessionToken, sp_concessionaireID, start_date, end_date, selectedTenantID, selectedPropertyID, sp_propertyaccess);
                break;

            case 1: // This Week
                calendar.add(Calendar.DAY_OF_YEAR, -1);
                String endWeek = sdf.format(calendar.getTime());
                calendar.add(Calendar.DAY_OF_YEAR, -6);
                String startWeek = sdf.format(calendar.getTime());
                start_date = startWeek;
                end_date = endWeek;
                startDateEditText.setText(startWeek);
                endDateEditText.setText(endWeek);
                startDateEditText.setVisibility(View.GONE);
                endDateEditText.setVisibility(View.GONE);
                linear1.setVisibility(View.GONE);
                fetchDashboardData(sp_sessionToken, sp_concessionaireID, start_date, end_date, selectedTenantID, selectedPropertyID, sp_propertyaccess);
                break;

            case 2: // This Month
                calendar.add(Calendar.DAY_OF_YEAR, -1);
                String endMonth = sdf.format(calendar.getTime());
                calendar.add(Calendar.DAY_OF_YEAR, -29);
                String startMonth = sdf.format(calendar.getTime());
                start_date = startMonth;
                end_date = endMonth;
                startDateEditText.setText(startMonth);
                endDateEditText.setText(endMonth);
                startDateEditText.setVisibility(View.GONE);
                endDateEditText.setVisibility(View.GONE);
                linear1.setVisibility(View.GONE);
                fetchDashboardData(sp_sessionToken, sp_concessionaireID, start_date, end_date, selectedTenantID, selectedPropertyID, sp_propertyaccess);
                break;

            case 3: // This Year
                calendar.add(Calendar.DAY_OF_YEAR, -1);
                String endYear = sdf.format(calendar.getTime());
                calendar.add(Calendar.DAY_OF_YEAR, -364);
                String startYear = sdf.format(calendar.getTime());
                start_date = startYear;
                end_date = endYear;
                startDateEditText.setText(startYear);
                endDateEditText.setText(endYear);
                startDateEditText.setVisibility(View.GONE);
                endDateEditText.setVisibility(View.GONE);
                linear1.setVisibility(View.GONE);
                fetchDashboardData(sp_sessionToken, sp_concessionaireID, start_date, end_date, selectedTenantID, selectedPropertyID, sp_propertyaccess);
                break;

            case 4: // Custom DateRange
                startDateEditText.setVisibility(View.VISIBLE);
                endDateEditText.setVisibility(View.VISIBLE);
                linear1.setVisibility(View.VISIBLE);
                start_date = startDateEditText.getText().toString();
                end_date = endDateEditText.getText().toString();
                fetchDashboardData(sp_sessionToken, sp_concessionaireID, start_date, end_date, selectedTenantID, selectedPropertyID, sp_propertyaccess);
                break;

            default:
                startDateEditText.setVisibility(View.GONE);
                endDateEditText.setVisibility(View.GONE);
                linear1.setVisibility(View.GONE);
                break;
        }
    }

    private void showDatePickerDialog(EditText dateField) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), (view, year1, monthOfYear, dayOfMonth) -> {
            String selectedDate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year1;
            dateField.setText(selectedDate);
        }, year, month, day);

        datePickerDialog.show();
        fetchDashboardData(sp_sessionToken, sp_concessionaireID, start_date, end_date, selectedTenantID, selectedPropertyID, sp_propertyaccess);
    }

    private void getSalesComparisonData(String spSessionToken, String spConcessionaireID, String spPropertyID) {

        SalesComparisonRequest salesComparisonRequest = new SalesComparisonRequest(spConcessionaireID, spPropertyID);
//        Log.d("SALESCOMPARISONREQUEST", salesComparisonRequest.toString());
        ApiService apiService = ApiClient.getApiService();
        Call<SalesComparisonResponse> call = apiService.getSalesComparisonData(spSessionToken, salesComparisonRequest);

        call.enqueue(new Callback<SalesComparisonResponse>() {
            @Override
            public void onResponse(Call<SalesComparisonResponse> call, Response<SalesComparisonResponse> response) {
                if (response.isSuccessful()) {
                    // Update the table layout with the response data
                    SalesComparisonResponse salesComparison = response.body();

                    displaySalesData(salesComparison);
                } else {
                    Toast.makeText(getContext(), "Error fetching data", Toast.LENGTH_SHORT).show();
                    Log.e("ERRORFETCHINGDATA", response.toString());
                }
            }

            @Override
            public void onFailure(Call<SalesComparisonResponse> call, Throwable t) {
                Log.e("ERRORONFAILURE", t.getMessage());
                Toast.makeText(getContext(), "Sales Comparison Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void displaySalesData(SalesComparisonResponse salesComparison) {
        TableLayout tableLayout = binding.salesTable;
        TableRow previousYearRow = binding.previousYearRow;
        TableRow currentYearRow = binding.currentYearRow;

        previousYearRow.removeAllViews();
        currentYearRow.removeAllViews();

        List<SalesComparisonResponse.SalesData> currentYearSales = salesComparison.getCurrentYearSales();
        List<SalesComparisonResponse.SalesData> previousYearSales = salesComparison.getPreviousYearSales();
        List<String> months = Arrays.asList("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy", Locale.getDefault());

        // Get current and previous years
        String current_year = sdf.format(calendar.getTime());
        calendar.add(Calendar.YEAR, -1);
        String previous_year = sdf.format(calendar.getTime());

        int cellWidthInPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, getResources().getDisplayMetrics());

        // Add month headers
        TableRow headerRow = (TableRow) tableLayout.getChildAt(0); // Assuming first row is the header
        if (headerRow.getChildCount() == 1) { // Only add month headers if they are not added already
            for (String month : months) {
                TextView monthView = new TextView(getContext());
                monthView.setText(month);
                monthView.setPadding(5, 5, 5, 5);
                monthView.setTextColor(Color.BLACK);
                TableRow.LayoutParams params = new TableRow.LayoutParams(cellWidthInPx, TableRow.LayoutParams.WRAP_CONTENT);
                monthView.setLayoutParams(params);
                headerRow.addView(monthView);
            }
        }

        // Add previous year row
        TextView prevYearText = new TextView(getContext());
        prevYearText.setText(previous_year);
        prevYearText.setPadding(8, 8, 8, 8);
        prevYearText.setTextColor(Color.BLACK);
        TableRow.LayoutParams paramsPrevYear = new TableRow.LayoutParams(cellWidthInPx, TableRow.LayoutParams.WRAP_CONTENT);
        prevYearText.setLayoutParams(paramsPrevYear);
        previousYearRow.addView(prevYearText);

        // Populate previous year sales data
        for (SalesComparisonResponse.SalesData prevYearData : previousYearSales) {
            LinearLayout cellLayout = new LinearLayout(getContext());
            cellLayout.setOrientation(LinearLayout.VERTICAL);
            cellLayout.setPadding(8, 8, 8, 8);

            // Total Sales
            TextView totalSalesText = new TextView(getContext());
            totalSalesText.setText(String.valueOf(prevYearData.getTotalSales()));
            totalSalesText.setTextColor(Color.BLACK);
            totalSalesText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            cellLayout.addView(totalSalesText);

            // Percentage Change
            TextView percentageText = new TextView(getContext());
            percentageText.setText(prevYearData.getPercentageChange() + "%");
            double percentage = Double.parseDouble(prevYearData.getPercentageChange());
            percentageText.setTextColor(percentage > 0 ? Color.GREEN : percentage < 0 ? Color.RED : Color.GRAY);
            percentageText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);  // Smaller text for percentage
            percentageText.setGravity(Gravity.CENTER);  // Align percentage to the end
            if (percentage == 0) percentageText.setText("-");
            cellLayout.addView(percentageText);

            TableRow.LayoutParams params = new TableRow.LayoutParams(cellWidthInPx, TableRow.LayoutParams.WRAP_CONTENT);
            cellLayout.setLayoutParams(params);
            previousYearRow.addView(cellLayout);
        }

        // Add "-" for missing months in Previous Year data
        for (int i = previousYearSales.size(); i < 12; i++) {
            TextView missingText = new TextView(getContext());
            missingText.setText("-");
            missingText.setTextColor(Color.BLACK);
            missingText.setGravity(Gravity.CENTER);
            TableRow.LayoutParams missingParams = new TableRow.LayoutParams(cellWidthInPx, TableRow.LayoutParams.WRAP_CONTENT);
            missingText.setLayoutParams(missingParams);
            previousYearRow.addView(missingText);
        }

        // Add current year row
        TextView currYearText = new TextView(getContext());
        currYearText.setText(current_year);
        currYearText.setPadding(8, 8, 8, 8);
        currYearText.setTextColor(Color.BLACK);
        TableRow.LayoutParams paramsCurrYear = new TableRow.LayoutParams(cellWidthInPx, TableRow.LayoutParams.WRAP_CONTENT);
        currYearText.setLayoutParams(paramsCurrYear);
        currentYearRow.addView(currYearText);

        // Populate current year sales data
        for (SalesComparisonResponse.SalesData currYearData : currentYearSales) {
            LinearLayout cellLayout = new LinearLayout(getContext());
            cellLayout.setOrientation(LinearLayout.VERTICAL);
            cellLayout.setPadding(8, 8, 8, 8);

            // Total Sales
            TextView totalSalesText = new TextView(getContext());
            totalSalesText.setText(String.valueOf(currYearData.getTotalSales()));
            totalSalesText.setTextColor(Color.BLACK);
            totalSalesText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            cellLayout.addView(totalSalesText);

            // Percentage Change
            TextView percentageText = new TextView(getContext());
            percentageText.setText(currYearData.getPercentageChange() + "%");
            double percentage = Double.parseDouble(currYearData.getPercentageChange());
            percentageText.setTextColor(percentage > 0 ? Color.GREEN : percentage < 0 ? Color.RED : Color.GRAY);
            percentageText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);  // Smaller text for percentage
            percentageText.setGravity(Gravity.CENTER);  // Align percentage to the end
            if (percentage == 0) percentageText.setText("-");
            cellLayout.addView(percentageText);

            TableRow.LayoutParams params = new TableRow.LayoutParams(cellWidthInPx, TableRow.LayoutParams.WRAP_CONTENT);
            cellLayout.setLayoutParams(params);
            currentYearRow.addView(cellLayout);
        }

        // Add "-" for missing months in Current Year data
        for (int i = currentYearSales.size(); i < 12; i++) {
            TextView missingText = new TextView(getContext());
            missingText.setText("-");
            missingText.setTextColor(Color.BLACK);
            missingText.setGravity(Gravity.CENTER);
            TableRow.LayoutParams missingParams = new TableRow.LayoutParams(cellWidthInPx, TableRow.LayoutParams.WRAP_CONTENT);
            missingText.setLayoutParams(missingParams);
            currentYearRow.addView(missingText);
        }

        selectSubCategory();
//        getSubCategorySales(sp_sessionToken, sp_concessionaireID, category_name);
    }

    private void selectSubCategory() {

        // Check if dark mode is active
//        int nightModeFlags = getContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
//        boolean isDarkMode = nightModeFlags == Configuration.UI_MODE_NIGHT_YES;

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getContext(),
                R.layout.custom_spinner_item, // Use the custom layout for spinner item
                new String[]{"F&B", "Retail", "All"}
        ) {
            @NonNull
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                return textView; // Text color will adapt based on theme
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getDropDownView(position, convertView, parent);
                return textView; // Text color will adapt based on theme
            }
        };

        adapter.setDropDownViewResource(R.layout.custom_spinner_item); // Use the custom layout for dropdown items
        categorySpinner.setAdapter(adapter);

        // Set dropdown indicator based on dark mode
//        if (isDarkMode) {
//            categorySpinner.setBackgroundResource(R.drawable.dropdown_indicator); // Set the indicator drawable
//        }

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateCategories(parent, position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });


    }

    private void updateCategories(AdapterView<?> parent, int position) {
//        Log.d("SelectedSubCategory", "Default " + position);
        String selectedCategory = (String) parent.getItemAtPosition(position);
        String category_name;
        switch (selectedCategory) {
            case "F&B":
                category_name = "F&B";
//                Log.d("SelectedSubCategory", category_name);
                break;
            case "Retail":
                category_name = "Retail";
//                Log.d("SelectedSubCategory", category_name);
                break;
            case "All":
                category_name = "";
//                Log.d("SelectedSubCategory", category_name);
                break;
            default:
                category_name = "F&B";  // Default to "F&B"
//                Log.d("SelectedSubCategory", "Default Switch " + category_name);
                break;
        }
        getSubCategorySales(sp_sessionToken, sp_concessionaireID, category_name);
    }

    private void getSubCategorySales(String spSessionToken, String spConcessionaireID, String category_name) {
        SubcategorySalesRequest subcategorySalesRequest = new SubcategorySalesRequest(spConcessionaireID, category_name, selectedPropertyID);
//        Log.d("SubCategorySalesRequest", subcategorySalesRequest.toString());
        ApiService apiService = ApiClient.getApiService();
        Call<SubcategorySalesResponse> call = apiService.getSubcategorySales(spSessionToken, subcategorySalesRequest);
        call.enqueue(new Callback<SubcategorySalesResponse>() {
            @Override
            public void onResponse(Call<SubcategorySalesResponse> call, Response<SubcategorySalesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {

                    List<SubcategorySalesResponse.SubcategorySales> salesData = response.body().getSubcategorywiseSales();
                    populatePieChart(salesData);
                } else {
                    Toast.makeText(getContext(), "Error fetching data", Toast.LENGTH_SHORT).show();
                    Log.e("ERRORFETCHINGDATA", response.toString());
                }
            }

            @Override
            public void onFailure(Call<SubcategorySalesResponse> call, Throwable t) {
                Log.e("ERRORONFAILURE", t.getMessage());
                Toast.makeText(getContext(), "Sub Category Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void populatePieChart(List<SubcategorySalesResponse.SubcategorySales> salesData) {
        if (salesData == null || salesData.isEmpty()) {
            Toast.makeText(getContext(), "No sales data available", Toast.LENGTH_SHORT).show();
            return;  // Exit the method to avoid further processing
        }

        PieChart pieChart = binding.tenantsPieChart;
        pieChart.setRotationEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(12f);

        List<PieEntry> pieEntries = new ArrayList<>();
        List<String> legendLabels = new ArrayList<>();

        // Loop through the sales data and add entries to the pie chart
        for (SubcategorySalesResponse.SubcategorySales sales : salesData) {
            // Add both the net sales and the subcategory name for the legends
            pieEntries.add(new PieEntry((float) sales.getNet_sales(), sales.getSubcategory_name()));
//            legendLabels.add(sales.getSubcategory_name() + ": ₹ " + sales.getNet_sales() + "M");
        }

        // Create a data set from the pie entries
        PieDataSet pieDataSet = new PieDataSet(pieEntries, "* Values in Millions");

        List<Integer> colors = Arrays.asList(
                Color.parseColor("#FF5733"),  // Red-Orange
                Color.parseColor("#33FF57"),  // Green
                Color.parseColor("#5733FF"),  // Blue
                Color.parseColor("#FF33A1"),  // Pink
                Color.parseColor("#33FFF9"),  // Cyan
                Color.parseColor("#FFC733"),  // Yellow
                Color.parseColor("#FF6F33"),  // Dark Orange
                Color.parseColor("#B833FF"),  // Purple
                Color.parseColor("#33FFB8"),  // Mint
                Color.parseColor("#FF3333"),  // Red
                Color.parseColor("#1ABC9C"),  // Turquoise
                Color.parseColor("#2ECC71"),  // Emerald
                Color.parseColor("#3498DB"),  // Sky Blue
                Color.parseColor("#9B59B6"),  // Amethyst
                Color.parseColor("#34495E"),  // Dark Blue-Grey
                Color.parseColor("#F1C40F"),  // Sunflower Yellow
                Color.parseColor("#E67E22"),  // Carrot Orange
                Color.parseColor("#E74C3C"),  // Alizarin Red
                Color.parseColor("#95A5A6"),  // Concrete Grey
                Color.parseColor("#ECF0F1"),  // Clouds White
                Color.parseColor("#16A085"),  // Green Sea
                Color.parseColor("#27AE60"),  // Nephritis Green
                Color.parseColor("#2980B9"),  // Belize Hole Blue
                Color.parseColor("#8E44AD"),  // Wisteria Purple
                Color.parseColor("#2C3E50"),  // Midnight Blue
                Color.parseColor("#F39C12"),  // Orange
                Color.parseColor("#D35400"),  // Pumpkin Orange
                Color.parseColor("#C0392B"),  // Pomegranate Red
                Color.parseColor("#7F8C8D"),  // Asbestos Grey
                Color.parseColor("#BDC3C7"),  // Silver
                Color.parseColor("#8E44AD"),  // Deep Violet
                Color.parseColor("#1F618D"),  // Ocean Blue
                Color.parseColor("#C70039"),  // Crimson
                Color.parseColor("#A569BD"),  // Soft Purple
                Color.parseColor("#2980B9"),  // Strong Blue
                Color.parseColor("#F4D03F"),  // Bright Yellow
                Color.parseColor("#48C9B0"),  // Aqua Green
                Color.parseColor("#5DADE2"),  // Cool Blue
                Color.parseColor("#AAB7B8"),  // Warm Grey
                Color.parseColor("#DFFF00"),  // Chartreuse
                Color.parseColor("#FF9FF3")   // Light Pink
        );
        pieDataSet.setColors(colors);

        // Dynamic padding based on the number of entries
        int numberOfEntries = pieDataSet.getEntryCount();
        if (numberOfEntries > 10) {
            pieChart.setHoleRadius(45f);
            pieChart.setTransparentCircleRadius(50f);
        } else {
            pieChart.setHoleRadius(55f); // Adjust for fewer entries
            pieChart.setTransparentCircleRadius(60f);
        }

        // Adding padding around PieChart
        pieChart.setExtraOffsets(10, 10, 10, 10); // Top, Right, Bottom, Left

        // Format percentages
        PieData pieData = new PieData(pieDataSet);
        pieData.setValueFormatter(new PercentFormatter(pieChart));
        pieData.setValueTextSize(12f);
        pieData.setValueTextColor(Color.BLACK);

        // Remove labels from the pie chart slices (only display percentage)
        pieChart.setDrawEntryLabels(false);

        // Scrollable Legends Implementation
        NestedScrollView legendScrollView = binding.legendScrollView;
        LinearLayout legendLayout = binding.legendLayout;
        legendScrollView.setVisibility(View.VISIBLE); // Show if too many categories

        // Clear previous entries in the legend layout
        legendLayout.removeAllViews();
        pieChart.getLegend().setEnabled(false);
        // Create legend items
        for (int i = 0; i < salesData.size(); i++) {
            // Create a horizontal LinearLayout for each legend entry
            LinearLayout legendItemLayout = new LinearLayout(getContext());
            legendItemLayout.setOrientation(LinearLayout.HORIZONTAL);
            legendItemLayout.setGravity(Gravity.CENTER_VERTICAL);

            // Create a color indicator
            View colorIndicator = new View(getContext());
            colorIndicator.setLayoutParams(new LinearLayout.LayoutParams(40, 40)); // Size of the color indicator
            colorIndicator.setBackgroundColor(pieDataSet.getColor(i)); // Set the color from the PieDataSet

            // Create the TextView for legend entry
            TextView legendItem = new TextView(getContext());
            legendItem.setText(salesData.get(i).getSubcategory_name() + ":  " + salesData.get(i).getNet_sales() + "M"); // Display subcategory name and sales
            legendItem.setTextColor(Color.BLACK);
            legendItem.setPadding(10, 0, 0, 0); // Padding between color indicator and text

            // Add the color indicator and TextView to the legendItemLayout
            legendItemLayout.addView(colorIndicator);
            legendItemLayout.addView(legendItem);

            // Add the complete legend item layout to the legendLayout
            legendLayout.addView(legendItemLayout);
        }
        // Clickable legends to toggle visibility
        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                PieEntry pieEntry = (PieEntry) e;
                String label = pieEntry.getLabel();
                float value = pieEntry.getValue();

                // Show the selected entry in a Toast or log it
                Toast.makeText(getContext(), "Selected: " + label + " Value: ₹" + value + "M", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {
            }
        });

        // Set data to the pie chart and refresh
        pieChart.setData(pieData);
        pieChart.invalidate();  // Refresh the chart

        fetchTopFiveTenantsData();
    }

    private void fetchTopFiveTenantsData() {
        ApiService apiService = ApiClient.getApiService();
        TopFiveTenantsRequest topFiveTenantsRequest = new TopFiveTenantsRequest(sp_concessionaireID, "", "", selectedPropertyID, "");

        Call<TopFiveTenantsRespons> call = apiService.getTopFiveTenants(sp_sessionToken, topFiveTenantsRequest);

        call.enqueue(new Callback<TopFiveTenantsRespons>() {
            @Override
            public void onResponse(Call<TopFiveTenantsRespons> call, Response<TopFiveTenantsRespons> response) {
                if (response.isSuccessful() && response.body() != null) {
                    TopFiveTenantsRespons topFiveTenantsResponse = response.body();
                    List<TopFiveTenantsRespons.TenantData> tenantData = topFiveTenantsResponse.getTopFiveTenants();

                    // Populate bar chart data
                    populateBarChart(tenantData);
                } else {
                    Toast.makeText(getContext(), "Failed to fetch top 5 tenants data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TopFiveTenantsRespons> call, Throwable t) {
                Toast.makeText(getContext(), "Top 5 Tenants Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populateBarChart(List<TopFiveTenantsRespons.TenantData> tenantData) {

        BarChart top5TenantsBarChart = binding.top5TenantsBarChart;

        ArrayList<BarEntry> entries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();

        int i = 0;
        for (TopFiveTenantsRespons.TenantData data : tenantData) {
            // Convert net_sales to millions
            float netSalesInMillions = (float) (data.getNet_sales() / 1000000.0);
            entries.add(new BarEntry(i, netSalesInMillions));
            // Format the label to put each word on a new line
            String tenantName = data.getTenant_store_name();
            String[] words = tenantName.split(" "); // Split the tenant name by spaces
            StringBuilder formattedName = new StringBuilder();

            for (String word : words) {
                formattedName.append(word).append("\n"); // Append each word followed by a newline
            }

            // Remove the last newline character to avoid an extra blank line
            if (formattedName.length() > 0) {
                formattedName.setLength(formattedName.length() - 1);
            }

            labels.add(formattedName.toString()); // Add the formatted name to the labels
            i++;
        }

        BarDataSet barDataSet = new BarDataSet(entries, "Top 5 Tenants");
        barDataSet.setColor(Color.parseColor("#58E2F5")); // Set bar color

        // Enable values to be drawn on top of the bars
        barDataSet.setDrawValues(true);
        barDataSet.setValueTextSize(12f);  // Set value text size
        barDataSet.setValueTextColor(Color.BLACK);  // Set value text color

        // Set custom value formatter to display millions (M)
        barDataSet.setValueFormatter(new MillionsValueFormatter());

        BarData barData = new BarData(barDataSet);

        top5TenantsBarChart.setData(barData);
        top5TenantsBarChart.setFitBars(true);

        // Set the description and its position to the top-right corner
        Description description = new Description();
        description.setText("* Values in millions");
        description.setPosition(top5TenantsBarChart.getWidth() - 10, 20);
        top5TenantsBarChart.setDescription(description);

        top5TenantsBarChart.getXAxis().setLabelCount(5);
        top5TenantsBarChart.animateXY(2000, 2000);

        // Refresh chart after setting the data
        top5TenantsBarChart.invalidate();

        // Configure XAxis (for the horizontal chart)
        XAxis xAxis = top5TenantsBarChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setLabelRotationAngle(0f);  // Rotate labels by 90 degrees to make them vertical
        xAxis.setDrawLabels(true);
        xAxis.setXOffset(5);
        xAxis.setDrawAxisLine(true);
//        xAxis.setLabelCount(labels.size(), true);

        // Set custom labels for X axis
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));

        // Additional settings
        top5TenantsBarChart.setVisibleXRangeMaximum(5);
        top5TenantsBarChart.setPinchZoom(false);
        top5TenantsBarChart.setScaleEnabled(false);
        top5TenantsBarChart.getAxisRight().setEnabled(false);
        // Set custom value formatter to display millions (M)
//        barDataSet.setValueFormatter(new MillionsValueFormatter());
//        getSalesComparisonYOY(sp_sessionToken, sp_concessionaireID, selectedPropertyID);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}