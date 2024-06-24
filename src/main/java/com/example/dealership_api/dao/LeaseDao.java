package com.example.dealership_api.dao;

import com.example.dealership_api.model.LeaseContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class LeaseDao {

    private final DataSource dataSource;

    @Autowired
    public LeaseDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<LeaseContract> getLeaseInformationWithDateRange(int dealerID, Date startDate, Date endDate) {
        List<LeaseContract> contracts = new ArrayList<>();
        String sql = "CALL GetLeaseInformationWithDateRange(?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, dealerID);
            stmt.setDate(2, startDate);
            stmt.setDate(3, endDate);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                LeaseContract contract = new LeaseContract(rs.getInt("LeaseContractID"), rs.getInt("ContractID"),
                        rs.getDouble("ExpectedEndingValue"), rs.getDouble("LeaseFee"),
                        rs.getDouble("MonthlyPayment"));
                contracts.add(contract);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contracts;
    }

    public LeaseContract getLeaseContractById(int id) {
        String sql = "SELECT * FROM lease_contracts WHERE LeaseContractID = ?";
        LeaseContract contract = null;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                contract = new LeaseContract(rs.getInt("LeaseContractID"), rs.getInt("ContractID"),
                        rs.getDouble("ExpectedEndingValue"), rs.getDouble("LeaseFee"),
                        rs.getDouble("MonthlyPayment"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contract;
    }

    public void addLeaseContract(LeaseContract contract) {
        String sql = "INSERT INTO lease_contracts (ContractID, ExpectedEndingValue, LeaseFee, MonthlyPayment) VALUES (?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, contract.getContractID());
            pstmt.setDouble(2, contract.getExpectedEndingValue());
            pstmt.setDouble(3, contract.getLeaseFee());
            pstmt.setDouble(4, contract.getMonthlyPayment());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
