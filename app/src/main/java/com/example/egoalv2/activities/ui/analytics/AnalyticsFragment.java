package com.example.egoalv2.activities.ui.analytics;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.egoalv2.R;
import com.example.egoalv2.activities.AnalyticsActivity;
import com.example.egoalv2.activities.DashboardActivity;
import com.example.egoalv2.activities.MainActivity;
import com.example.egoalv2.adapters.PropertyAdapter;
import com.example.egoalv2.adapters.TenantAdapter;
import com.example.egoalv2.databinding.FragmentAnalyticsBinding;
import com.example.egoalv2.databinding.FragmentDashboardBinding;
import com.example.egoalv2.interfaces.ApiClient;
import com.example.egoalv2.interfaces.ApiService;
import com.example.egoalv2.model.PropertyNameList;
import com.example.egoalv2.request.AnalyticsTenantRequest;
import com.example.egoalv2.request.PropertyRequest;
import com.example.egoalv2.response.AnalyticsTenantResponse;
import com.example.egoalv2.response.PropertyResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnalyticsFragment extends Fragment {

    private FragmentAnalyticsBinding binding;
    private AutoCompleteTextView propertyAutoComplete;
    private String sp_firsname, sp_concessionaireID, sp_sessionToken, sp_propertyaccess;
    private String selectedPropertyName = "";
    private String start_date, end_date;
    private EditText startDateEditText, endDateEditText;
    private AppCompatSpinner dateRangeSpinner;
    private ImageView menuIcon;
    private LinearLayout linear1;
    private SearchView searchViewTenant;
    private List<AnalyticsTenantResponse> tenantList = new ArrayList<>();
    private List<AnalyticsTenantResponse> filteredTenantList = new ArrayList<>();
    private RecyclerView recyclerView;
    private TenantAdapter tenantAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AnalyticsViewModel dashboardViewModel =
                new ViewModelProvider(this).get(AnalyticsViewModel.class);

        binding = FragmentAnalyticsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        propertyAutoComplete = binding.analyticsPropertyAutocomplete;
        dateRangeSpinner = binding.analyticsDateRangeSpinner;
        startDateEditText = binding.analyticsStartDate;
        endDateEditText = binding.analyticsEndDate;
        linear1 = binding.analyticsLinear;
        searchViewTenant = binding.searchViewTenant;
        recyclerView = binding.tenantRecyclerView; // Initialize RecyclerView with the correct ID

        menuIcon = binding.analyticsMenuIcon;
        dateRangeSpinner.setPopupBackgroundResource(android.R.color.white);

        SharedPreferences sharedPref = getActivity().getSharedPreferences("UserDetails", getActivity().MODE_PRIVATE);
        sp_firsname = sharedPref.getString("firstname", "User");
        sp_concessionaireID = sharedPref.getString("concessionaireId", "");
        sp_sessionToken = sharedPref.getString("sessiontoken", "");
        sp_propertyaccess = sharedPref.getString("propertyaccess", "");

        // Initialize RecyclerView only if it's not null
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            tenantAdapter = new TenantAdapter(filteredTenantList);
            recyclerView.setAdapter(tenantAdapter);
        } else {
            Log.e("AnalyticsActivity", "RecyclerView is null. Check the layout XML for the correct ID.");
        }

        menuIcon.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(getActivity(), v);
            popupMenu.getMenuInflater().inflate(R.menu.analytics_menu, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(item -> {
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
            });
            popupMenu.show();
        });

        Log.e("PropertyAccess", sp_propertyaccess);

        showPropertyNames(sp_sessionToken, sp_concessionaireID, sp_propertyaccess);

        startDateEditText.setOnClickListener(v -> showDatePickerDialog(startDateEditText));
        endDateEditText.setOnClickListener(v -> showDatePickerDialog(endDateEditText));

//        final TextView textView = binding.textDashboard;
//        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }
    private void setSearchViewTenant(){
        searchViewTenant.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterTenantList(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterTenantList(newText);
                return true;
            }

        });
    }

    private void showPropertyNames(String spSessionToken, String spConcessionaireID, String spPropertyID) {
        try {
            PropertyRequest propertyRequest = new PropertyRequest(spConcessionaireID);

//        Toast.makeText(DashboardActivity.this, spConcessionaireID+"\n"+spPropertyID, Toast.LENGTH_SHORT).show();
            ApiService apiService = ApiClient.getApiService();
            Call<List<PropertyResponse.Property>> call = apiService.getPropertyNames(spSessionToken, propertyRequest);

            call.enqueue(new Callback<List<PropertyResponse.Property>>() {
                @Override
                public void onResponse(Call<List<PropertyResponse.Property>> call, Response<List<PropertyResponse.Property>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<PropertyResponse.Property> properties = response.body();
                        ArrayList<String> propertyNames = new ArrayList<>();
                        ArrayList<String> propertyIDs = new ArrayList<>();
                        propertyNames.add("All property"); // Default item
                        propertyIDs.add("");
                        for (PropertyResponse.Property property : properties) {
                            propertyNames.add(property.getPropertyName());
                            propertyIDs.add(property.getPropertyId());
                        }
                        Log.d("PropertyNames", propertyNames.toString());
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
                    Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressLint("SetTextI18n")
    private void populatePropertySpinner(ArrayList<String> propertyNames, ArrayList<String> propertyIDs) {
        try {
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

            propertyAutoComplete.setOnFocusChangeListener((v, hasFocus) -> {
                if (hasFocus) {
                    propertyAutoComplete.showDropDown(); // Show the dropdown on focus
                }
            });

            propertyAutoComplete.setOnItemClickListener((parent, view, position, id) -> {
                propertyAutoComplete.showDropDown();
                PropertyNameList selectedProperty = (PropertyNameList) parent.getItemAtPosition(position);
                selectedPropertyName = selectedProperty.getPropertyName();
                if (selectedPropertyName.isEmpty()) {
                    selectedPropertyName = "All property";
                }
                showDateRange();
//                showTenants(sp_sessionToken, sp_concessionaireID, start_date, end_date, selectedPropertyName);
            });
            // Set "All property" as the initial value when no property is selected
            propertyAutoComplete.setText("All property");
            selectedPropertyName = "";
            showDateRange();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void updateDateRange(int position) {
        try {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

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
                    showTenants(sp_sessionToken, sp_concessionaireID, start_date, end_date, selectedPropertyName);
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
                    showTenants(sp_sessionToken, sp_concessionaireID, start_date, end_date, selectedPropertyName);
//                showTenants(sp_sessionToken, sp_concessionaireID, start_date, end_date, selectedPropertyID);
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
                    showTenants(sp_sessionToken, sp_concessionaireID, start_date, end_date, selectedPropertyName);
//                showTenants(sp_sessionToken, sp_concessionaireID, start_date, end_date, selectedPropertyID);
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
                    showTenants(sp_sessionToken, sp_concessionaireID, start_date, end_date, selectedPropertyName);
//                showTenants(sp_sessionToken, sp_concessionaireID, start_date, end_date, selectedPropertyID);
                    break;

                case 4: // Custom DateRange
                    startDateEditText.setVisibility(View.VISIBLE);
                    endDateEditText.setVisibility(View.VISIBLE);
                    linear1.setVisibility(View.VISIBLE);
                    start_date = startDateEditText.getText().toString();
                    end_date = endDateEditText.getText().toString();
                    showTenants(sp_sessionToken, sp_concessionaireID, start_date, end_date, selectedPropertyName);
//                showTenants(sp_sessionToken, sp_concessionaireID, start_date, end_date, selectedPropertyID);
                    break;

                default:
                    startDateEditText.setVisibility(View.GONE);
                    endDateEditText.setVisibility(View.GONE);
                    linear1.setVisibility(View.GONE);
                    break;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void showTenants(String spSessionToken, String spConcessionaireID, String startDate, String endDate, String selectedPropertyName) {
        try {
            if (selectedPropertyName == null || selectedPropertyName.isEmpty() || selectedPropertyName.equals("All property")) {
                selectedPropertyName = "";
            }
            AnalyticsTenantRequest tenantRequest = new AnalyticsTenantRequest(
                    spConcessionaireID,
                    startDate,
                    endDate,
                    selectedPropertyName,
                    ""
            );

            ApiService apiService = ApiClient.getApiService();
            Call<List<AnalyticsTenantResponse>> call = apiService.getdaywiseconsolidatedreport(spSessionToken, tenantRequest);

            call.enqueue(new Callback<List<AnalyticsTenantResponse>>() {
                @Override
                public void onResponse(@NonNull Call<List<AnalyticsTenantResponse>> call, @NonNull Response<List<AnalyticsTenantResponse>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<AnalyticsTenantResponse> tenantLists = response.body();
                        setupRecyclerView(tenantLists);
                        setSearchViewTenant();
                    } else {
                        Log.e("showTenantsError", "Failed to fetch tenants: " + response.toString());
                        Toast.makeText(getContext(), "Failed to fetch tenants", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<AnalyticsTenantResponse>> call, @NonNull Throwable t) {
                    Log.e("CIAL E-Goal Sales Capture Solution", t.getMessage() != null ? t.getMessage() : "Unknown error");
                    Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void setupRecyclerView(List<AnalyticsTenantResponse> tenantList1) {
        if (recyclerView == null) {
            recyclerView = binding.tenantRecyclerView; // Ensure RecyclerView is initialized before usage
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        tenantAdapter = new TenantAdapter(filteredTenantList);
        recyclerView.setAdapter(tenantAdapter);

        this.tenantList.clear();
        this.tenantList.addAll(tenantList1);

        filteredTenantList.clear();
        filteredTenantList.addAll(tenantList1);

        tenantAdapter.notifyDataSetChanged();
    }

    // Method to filter the tenant list based on the search query
    @SuppressLint("NotifyDataSetChanged")
    private void filterTenantList(String query) {
        filteredTenantList.clear();
        if (query.isEmpty()) {
            filteredTenantList.addAll(tenantList);
        } else {
            for (AnalyticsTenantResponse tenant : tenantList) {
                if (tenant.getTenant_store_name().toLowerCase().contains(query.toLowerCase())) {
                    filteredTenantList.add(tenant);
                }
            }
        }
        tenantAdapter.notifyDataSetChanged();
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
        showTenants(sp_sessionToken, sp_concessionaireID, start_date, end_date, selectedPropertyName);
//        fetchDashboardData(sp_sessionToken, sp_concessionaireID, start_date, end_date, selectedTenantID, selectedPropertyID, sp_propertyaccess);
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
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}