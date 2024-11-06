package com.example.egoalv2.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import android.widget.Filter;

import androidx.annotation.NonNull;

import com.example.egoalv2.R;
import com.example.egoalv2.model.PropertyNameList;
import com.example.egoalv2.response.PropertyResponse;

import java.util.ArrayList;
import java.util.List;

public class PropertyAdapter extends ArrayAdapter<PropertyNameList> {
    private Context context;
    private final List<PropertyNameList> originalPropertyList;
    private final List<PropertyNameList> filteredPropertyList;

    public PropertyAdapter(Context context, List<PropertyNameList> propertyList) {
        super(context, 0, propertyList);
        this.context = context;
        this.originalPropertyList = new ArrayList<>(propertyList); // Keep the original list
        this.filteredPropertyList = propertyList; // This will be filtered
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.property_dropdown_item, parent, false);
        }

        TextView propertyNameTextView = convertView.findViewById(R.id.property_name);
        PropertyNameList property = getItem(position);

        if (property != null) {
            propertyNameTextView.setText(property.getPropertyName());
        }

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.property_dropdown_item, parent, false);
        }

        PropertyNameList property = getItem(position);
        TextView propertyNameTextView = convertView.findViewById(R.id.property_name);

        if (property != null) {
            propertyNameTextView.setText(property.getPropertyName());
        }

        return convertView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                List<PropertyNameList> suggestions = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {
                    suggestions.addAll(originalPropertyList); // Show all if no input
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (PropertyNameList property : originalPropertyList) {
                        if (property.getPropertyName().toLowerCase().contains(filterPattern)) {
                            suggestions.add(property); // Add matching property
                        }
                    }
                }

                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredPropertyList.clear();
                filteredPropertyList.addAll((List) results.values);
                notifyDataSetChanged();
            }

            @Override
            public CharSequence convertResultToString(Object resultValue) {
                return ((PropertyNameList) resultValue).getPropertyName();
            }
        };
    }
}
