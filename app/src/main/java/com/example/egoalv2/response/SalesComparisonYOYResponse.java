package com.example.egoalv2.response;

public class SalesComparisonYOYResponse {
    private String previousYearSale;
    private String currentYearSale;
    private String yoyCurrentYear;
    private String ytdPreviousYear;
    private String ytdCurrentYear;
    private String growthYTD;
    private String mtdPreviousYear;
    private String mtdCurrentYear;
    private String growthMTD;

    public SalesComparisonYOYResponse(String previousYearSale, String currentYearSale, String yoyCurrentYear, String ytdPreviousYear, String ytdCurrentYear, String growthYTD, String mtdPreviousYear, String mtdCurrentYear, String growthMTD) {
        this.previousYearSale = previousYearSale;
        this.currentYearSale = currentYearSale;
        this.yoyCurrentYear = yoyCurrentYear;
        this.ytdPreviousYear = ytdPreviousYear;
        this.ytdCurrentYear = ytdCurrentYear;
        this.growthYTD = growthYTD;
        this.mtdPreviousYear = mtdPreviousYear;
        this.mtdCurrentYear = mtdCurrentYear;
        this.growthMTD = growthMTD;
    }

    public String getPreviousYearSale() {
        return previousYearSale;
    }

    public String getCurrentYearSale() {
        return currentYearSale;
    }

    public String getYoyCurrentYear() {
        return yoyCurrentYear;
    }

    public String getYtdPreviousYear() {
        return ytdPreviousYear;
    }

    public String getYtdCurrentYear() {
        return ytdCurrentYear;
    }

    public String getGrowthYTD() {
        return growthYTD;
    }

    public String getMtdPreviousYear() {
        return mtdPreviousYear;
    }

    public String getMtdCurrentYear() {
        return mtdCurrentYear;
    }

    public String getGrowthMTD() {
        return growthMTD;
    }

    @Override
    public String toString() {
        return "SalesComparisonYOYResponse{" +
                "previousYearSale='" + previousYearSale + '\'' +
                ", currentYearSale='" + currentYearSale + '\'' +
                ", yoyCurrentYear='" + yoyCurrentYear + '\'' +
                ", ytdPreviousYear='" + ytdPreviousYear + '\'' +
                ", ytdCurrentYear='" + ytdCurrentYear + '\'' +
                ", growthYTD='" + growthYTD + '\'' +
                ", mtdPreviousYear='" + mtdPreviousYear + '\'' +
                ", mtdCurrentYear='" + mtdCurrentYear + '\'' +
                ", growthMTD='" + growthMTD + '\'' +
                '}';
    }
}
