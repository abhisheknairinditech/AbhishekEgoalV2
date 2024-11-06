package com.example.egoalv2;

import android.annotation.SuppressLint;

import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

public class MillionsValueFormatter extends ValueFormatter {
    @SuppressLint("DefaultLocale")
    @Override
    public String getFormattedValue(float value) {
        // Convert value to millions and append "M"
        if (value >= 1000000) {
            return String.format("%.1fM", value / 1000000); // Displays 1 decimal place
        } else {
            return String.format("%.1f", value); // Displays as is if not in millions
        }
    }
}