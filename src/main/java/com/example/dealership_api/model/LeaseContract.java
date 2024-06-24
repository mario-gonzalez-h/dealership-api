package com.example.dealership_api.model;

public class LeaseContract {
    private int leaseContractID;
    private int contractID;
    private double expectedEndingValue;
    private double leaseFee;
    private double monthlyPayment;

    public LeaseContract(int leaseContractID, int contractID, double expectedEndingValue, double leaseFee, double monthlyPayment) {
        this.leaseContractID = leaseContractID;
        this.contractID = contractID;
        this.expectedEndingValue = expectedEndingValue;
        this.leaseFee = leaseFee;
        this.monthlyPayment = monthlyPayment;
    }

    // Getters and Setters
    public int getLeaseContractID() { return leaseContractID; }
    public void setLeaseContractID(int leaseContractID) { this.leaseContractID = leaseContractID; }
    public int getContractID() { return contractID; }
    public void setContractID(int contractID) { this.contractID = contractID; }
    public double getExpectedEndingValue() { return expectedEndingValue; }
    public void setExpectedEndingValue(double expectedEndingValue) { this.expectedEndingValue = expectedEndingValue; }
    public double getLeaseFee() { return leaseFee; }
    public void setLeaseFee(double leaseFee) { this.leaseFee = leaseFee; }
    public double getMonthlyPayment() { return monthlyPayment; }
    public void setMonthlyPayment(double monthlyPayment) { this.monthlyPayment = monthlyPayment; }

    @Override
    public String toString() {
        return "LeaseContract{" +
                "leaseContractID=" + leaseContractID +
                ", contractID=" + contractID +
                ", expectedEndingValue=" + expectedEndingValue +
                ", leaseFee=" + leaseFee +
                ", monthlyPayment=" + monthlyPayment +
                '}';
    }
}
