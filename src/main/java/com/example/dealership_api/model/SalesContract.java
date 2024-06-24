package com.example.dealership_api.model;

public class SalesContract {
    private int salesContractID;
    private int contractID;
    private double totalPrice;
    private double salesTaxAmount;
    private double recordingFee;
    private double processingFee;
    private boolean financed;
    private String financingDetails;

    public SalesContract(int salesContractID, int contractID, double totalPrice, double salesTaxAmount, double recordingFee, double processingFee, boolean financed, String financingDetails) {
        this.salesContractID = salesContractID;
        this.contractID = contractID;
        this.totalPrice = totalPrice;
        this.salesTaxAmount = salesTaxAmount;
        this.recordingFee = recordingFee;
        this.processingFee = processingFee;
        this.financed = financed;
        this.financingDetails = financingDetails;
    }

    // Getters and Setters
    public int getSalesContractID() { return salesContractID; }
    public void setSalesContractID(int salesContractID) { this.salesContractID = salesContractID; }
    public int getContractID() { return contractID; }
    public void setContractID(int contractID) { this.contractID = contractID; }
    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
    public double getSalesTaxAmount() { return salesTaxAmount; }
    public void setSalesTaxAmount(double salesTaxAmount) { this.salesTaxAmount = salesTaxAmount; }
    public double getRecordingFee() { return recordingFee; }
    public void setRecordingFee(double recordingFee) { this.recordingFee = recordingFee; }
    public double getProcessingFee() { return processingFee; }
    public void setProcessingFee(double processingFee) { this.processingFee = processingFee; }
    public boolean isFinanced() { return financed; }
    public void setFinanced(boolean financed) { this.financed = financed; }
    public String getFinancingDetails() { return financingDetails; }
    public void setFinancingDetails(String financingDetails) { this.financingDetails = financingDetails; }

    @Override
    public String toString() {
        return "SalesContract{" +
                "salesContractID=" + salesContractID +
                ", contractID=" + contractID +
                ", totalPrice=" + totalPrice +
                ", salesTaxAmount=" + salesTaxAmount +
                ", recordingFee=" + recordingFee +
                ", processingFee=" + processingFee +
                ", financed=" + financed +
                ", financingDetails='" + financingDetails + '\'' +
                '}';
    }
}
