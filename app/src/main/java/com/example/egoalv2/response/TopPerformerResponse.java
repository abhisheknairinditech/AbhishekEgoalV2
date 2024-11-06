package com.example.egoalv2.response;

public class TopPerformerResponse {
    private TopPerformer topPerformerYesterday;
    private TopPerformer topPerformerMonth;
    private TopPerformer topPerformerYear;

    // Getters and setters for each field


    public TopPerformer getTopPerformerYesterday() {
        return topPerformerYesterday;
    }

    public void setTopPerformerYesterday(TopPerformer topPerformerYesterday) {
        this.topPerformerYesterday = topPerformerYesterday;
    }

    public TopPerformer getTopPerformerMonth() {
        return topPerformerMonth;
    }

    public void setTopPerformerMonth(TopPerformer topPerformerMonth) {
        this.topPerformerMonth = topPerformerMonth;
    }

    public TopPerformer getTopPerformerYear() {
        return topPerformerYear;
    }

    public void setTopPerformerYear(TopPerformer topPerformerYear) {
        this.topPerformerYear = topPerformerYear;
    }

    public static class TopPerformer {
        private String _id;
        private String totalNetSales;
        private String tenant_name;

        // Getters and setters for each field

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getTotalNetSales() {
            return totalNetSales;
        }

        public void setTotalNetSales(String totalNetSales) {
            this.totalNetSales = totalNetSales;
        }

        public String getTenant_name() {
            return tenant_name;
        }

        public void setTenant_name(String tenant_name) {
            this.tenant_name = tenant_name;
        }
    }
}
