package com.example.dealership_api.dao;

import com.example.dealership_api.model.SalesContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class SalesDao {

    private final DataSource dataSource;

    @Autowired
    public SalesDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<SalesContract> getSalesInformationWithDateRange(int dealerID, Date startDate, Date endDate) {
        List<SalesContract> contracts = new ArrayList<>();
        String sql = "CALL GetSalesInformationWithDateRange(?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, dealerID);
            stmt.setDate(2, startDate);
            stmt.setDate(3, endDate);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                SalesContract contract = new SalesContract(rs.getInt("SalesContractID"), rs.getInt("ContractID"),
                        rs.getDouble("TotalPrice"), rs.getDouble("SalesTaxAmount"),
                        rs.getDouble("RecordingFee"), rs.getDouble("ProcessingFee"),
                        rs.getBoolean("Financed"), rs.getString("FinancingDetails"));
                contracts.add(contract);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contracts;
    }

    public SalesContract getSalesContractById(int id) {
        String sql = "SELECT * FROM sales_contracts WHERE SalesContractID = ?";
        SalesContract contract = null;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                contract = new SalesContract(rs.getInt("SalesContractID"), rs.getInt("ContractID"),
                        rs.getDouble("TotalPrice"), rs.getDouble("SalesTaxAmount"),
                        rs.getDouble("RecordingFee"), rs.getDouble("ProcessingFee"),
                        rs.getBoolean("Financed"), rs.getString("FinancingDetails"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contract;
    }

    public void addSalesContract(SalesContract contract) {
        String sql = "INSERT INTO sales_contracts (ContractID, TotalPrice, SalesTaxAmount, RecordingFee, ProcessingFee, Financed, FinancingDetails) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, contract.getContractID());
            pstmt.setDouble(2, contract.getTotalPrice());
            pstmt.setDouble(3, contract.getSalesTaxAmount());
            pstmt.setDouble(4, contract.getRecordingFee());
            pstmt.setDouble(5, contract.getProcessingFee());
            pstmt.setBoolean(6, contract.isFinanced());
            pstmt.setString(7, contract.getFinancingDetails());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
