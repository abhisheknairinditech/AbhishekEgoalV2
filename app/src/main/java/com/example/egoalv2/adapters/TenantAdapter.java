package com.example.egoalv2.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.egoalv2.R;
import com.example.egoalv2.response.AnalyticsTenantResponse;
import com.example.egoalv2.response.TenantResponse;

import java.util.List;

public class TenantAdapter extends RecyclerView.Adapter<TenantAdapter.TenantViewHolder> {

    private List<AnalyticsTenantResponse> tenantList;

    public TenantAdapter(List<AnalyticsTenantResponse> tenantList) {
        this.tenantList = tenantList;
    }

    @NonNull
    @Override
    public TenantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tenant_card_item, parent, false);
        return new TenantViewHolder(view);
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public void onBindViewHolder(@NonNull TenantViewHolder holder, int position) {
        AnalyticsTenantResponse tenant = tenantList.get(position);
        holder.tenantStoreName.setText(tenant.getTenant_store_name());
        holder.tenantCode.setText("Tenant Code: "+tenant.getTenant_code());
        holder.propertyMallCode.setText("Property Name: "+tenant.getProperty_name());
        holder.totalNetSales.setText("Total Net Sale: "+String.format("%.2f", tenant.getTotal_net_sales()));
        holder.totalTransactionCount.setText("Total Transaction Count: "+String.valueOf(tenant.getTotal_transaction_count()));
        holder.tenantGroupName.setText("Group Name: "+tenant.getTenant_group_name());
        holder.floor.setText("Floor: "+tenant.getFloor());
    }

    @Override
    public int getItemCount() {
        return tenantList.size();
    }

    static class TenantViewHolder extends RecyclerView.ViewHolder {
        TextView tenantStoreName, tenantCode, propertyMallCode, totalNetSales, totalTransactionCount, tenantGroupName, floor;

        public TenantViewHolder(@NonNull View itemView) {
            super(itemView);
            tenantStoreName = itemView.findViewById(R.id.tenant_store_name);
            tenantCode = itemView.findViewById(R.id.tenant_code);
            propertyMallCode = itemView.findViewById(R.id.property_mall_code);
            totalNetSales = itemView.findViewById(R.id.totalNetSales);
            totalTransactionCount = itemView.findViewById(R.id.totalTransactionCount);
            tenantGroupName = itemView.findViewById(R.id.tenantGroupName);
            floor = itemView.findViewById(R.id.floor);
        }
    }
}
