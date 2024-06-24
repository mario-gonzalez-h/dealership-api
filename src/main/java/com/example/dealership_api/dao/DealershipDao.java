package com.example.dealership_api.dao;

import com.example.dealership_api.model.Dealership;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class DealershipDao {

    private final DataSource dataSource;

    @Autowired
    public DealershipDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Dealership> getAllDealerships() {
        List<Dealership> dealerships = new ArrayList<>();
        String sql = "CALL GetAllDealerships()";
        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Dealership dealership = new Dealership(rs.getInt("DealershipID"), rs.getString("Name"),
                        rs.getString("Address"), rs.getString("Phone"));
                dealerships.add(dealership);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dealerships;
    }

    public Dealership findDealershipByVehicleVIN(int vin) {
        String sql = "CALL FindDealershipByVehicleVIN(?)";
        Dealership dealership = null;
        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, vin);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                dealership = new Dealership(rs.getInt("DealershipID"), rs.getString("Name"),
                        rs.getString("Address"), rs.getString("Phone"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dealership;
    }

    public List<Dealership> findDealershipsWithCarType(String make, String model, String color) {
        List<Dealership> dealerships = new ArrayList<>();
        String sql = "CALL FindDealershipsWithCarType(?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setString(1, make);
            stmt.setString(2, model);
            stmt.setString(3, color);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Dealership dealership = new Dealership(rs.getInt("DealershipID"), rs.getString("Name"),
                        rs.getString("Address"), rs.getString("Phone"));
                dealerships.add(dealership);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dealerships;
    }
}
