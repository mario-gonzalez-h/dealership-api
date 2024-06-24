package com.example.dealership_api.dao;

import com.example.dealership_api.model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class VehicleDao {

    private final DataSource dataSource;

    @Autowired
    public VehicleDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Vehicle> findVehiclesByDealershipID(int dealershipID) {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = "CALL FindVehiclesByDealershipID(?)";
        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, dealershipID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Vehicle vehicle = new Vehicle(rs.getInt("Vin"), rs.getInt("Year"), rs.getString("Make"),
                        rs.getString("Model"), rs.getString("VehicleType"), rs.getString("Color"),
                        rs.getInt("Odometer"), rs.getDouble("Price"), rs.getInt("DealershipID"));
                vehicles.add(vehicle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    public Vehicle findVehicleByVIN(int vin) {
        String sql = "CALL FindVehicleByVIN(?)";
        Vehicle vehicle = null;
        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, vin);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                vehicle = new Vehicle(rs.getInt("Vin"), rs.getInt("Year"), rs.getString("Make"),
                        rs.getString("Model"), rs.getString("VehicleType"), rs.getString("Color"),
                        rs.getInt("Odometer"), rs.getDouble("Price"), rs.getInt("DealershipID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicle;
    }

    public List<Vehicle> searchVehicles(Double minPrice, Double maxPrice, String make, String model, Integer minYear, Integer maxYear, String color, Integer minMiles, Integer maxMiles, String type) {
        List<Vehicle> vehicles = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM vehicles WHERE 1=1");

        if (minPrice != null) sql.append(" AND Price >= ").append(minPrice);
        if (maxPrice != null) sql.append(" AND Price <= ").append(maxPrice);
        if (make != null) sql.append(" AND Make = '").append(make).append("'");
        if (model != null) sql.append(" AND Model = '").append(model).append("'");
        if (minYear != null) sql.append(" AND Year >= ").append(minYear);
        if (maxYear != null) sql.append(" AND Year <= ").append(maxYear);
        if (color != null) sql.append(" AND Color = '").append(color).append("'");
        if (minMiles != null) sql.append(" AND Odometer >= ").append(minMiles);
        if (maxMiles != null) sql.append(" AND Odometer <= ").append(maxMiles);
        if (type != null) sql.append(" AND VehicleType = '").append(type).append("'");

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql.toString())) {
            while (rs.next()) {
                Vehicle vehicle = new Vehicle(rs.getInt("Vin"), rs.getInt("Year"), rs.getString("Make"),
                        rs.getString("Model"), rs.getString("VehicleType"), rs.getString("Color"),
                        rs.getInt("Odometer"), rs.getDouble("Price"), rs.getInt("DealershipID"));
                vehicles.add(vehicle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    public void addVehicle(Vehicle vehicle) {
        String sql = "INSERT INTO vehicles (Vin, Year, Make, Model, VehicleType, Color, Odometer, Price, DealershipID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, vehicle.getVin());
            pstmt.setInt(2, vehicle.getYear());
            pstmt.setString(3, vehicle.getMake());
            pstmt.setString(4, vehicle.getModel());
            pstmt.setString(5, vehicle.getVehicleType());
            pstmt.setString(6, vehicle.getColor());
            pstmt.setInt(7, vehicle.getOdometer());
            pstmt.setDouble(8, vehicle.getPrice());
            pstmt.setInt(9, vehicle.getDealershipId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateVehicle(Vehicle vehicle) {
        String sql = "UPDATE vehicles SET Year = ?, Make = ?, Model = ?, VehicleType = ?, Color = ?, Odometer = ?, Price = ?, DealershipID = ? WHERE Vin = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, vehicle.getYear());
            pstmt.setString(2, vehicle.getMake());
            pstmt.setString(3, vehicle.getModel());
            pstmt.setString(4, vehicle.getVehicleType());
            pstmt.setString(5, vehicle.getColor());
            pstmt.setInt(6, vehicle.getOdometer());
            pstmt.setDouble(7, vehicle.getPrice());
            pstmt.setInt(8, vehicle.getDealershipId());
            pstmt.setInt(9, vehicle.getVin());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteVehicle(int vin) {
        String sql = "DELETE FROM vehicles WHERE Vin = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, vin);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Vehicle> getAllVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = "CALL GetAllVehicles()";
        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Vehicle vehicle = new Vehicle(rs.getInt("Vin"), rs.getInt("Year"), rs.getString("Make"),
                        rs.getString("Model"), rs.getString("VehicleType"), rs.getString("Color"),
                        rs.getInt("Odometer"), rs.getDouble("Price"), rs.getInt("DealershipID"));
                vehicles.add(vehicle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }
}
