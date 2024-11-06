package com.example.egoalv2.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.egoalv2.Config;
import com.example.egoalv2.R;
import com.example.egoalv2.model.TenantProperty;


import java.util.List;

public class TenantPropertyAdapter extends ArrayAdapter<TenantProperty> {
    private Context context;
    private List<TenantProperty> tenantPropertyList;

    public TenantPropertyAdapter(Context context, List<TenantProperty> tenantPropertyList){
        super(context, 0, tenantPropertyList);
        this.context = context;
        this.tenantPropertyList=tenantPropertyList;
        Log.d("TENANTPROPERTYADAPTER", tenantPropertyList.toString());
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @SuppressLint("SetTextI18n")
    private View initView(int position, View convertView, ViewGroup parent) {
        // Inflate the custom layout
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.tenant_dropdown_item, parent, false);
        }

        // Get the current tenant property object
        TenantProperty tenantProperty = getItem(position);

        // Get references to the views in the custom layout
        TextView tenantNameTextView = convertView.findViewById(R.id.tvTenantStoreName);
        TextView propertyNameTextView = convertView.findViewById(R.id.tvPropertyName);

        // Populate the views with the data
        if (tenantProperty != null) {
            tenantNameTextView.setText(tenantProperty.getTenant_store_name());
            propertyNameTextView.setText("(" + tenantProperty.getProperty_name() + ")");
        }

        return convertView;
    }
}
