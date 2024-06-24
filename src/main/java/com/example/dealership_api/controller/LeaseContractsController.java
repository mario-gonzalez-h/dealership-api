package com.example.dealership_api.controllers;

import com.example.dealership_api.dao.LeaseDao;
import com.example.dealership_api.model.LeaseContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/api/dealership/lease_contracts")
public class LeaseContractsController {

    private final LeaseDao leaseDao;

    @Autowired
    public LeaseContractsController(LeaseDao leaseDao) {
        this.leaseDao = leaseDao;
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeaseContract> getLeaseContractById(@PathVariable int id) {
        LeaseContract contract = leaseDao.getLeaseContractById(id);
        return new ResponseEntity<>(contract, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> addLeaseContract(@RequestBody LeaseContract leaseContract) {
        leaseDao.addLeaseContract(leaseContract);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<LeaseContract>> getLeaseInformationWithDateRange(
            @RequestParam int dealerID,
            @RequestParam Date startDate,
            @RequestParam Date endDate) {
        List<LeaseContract> contracts = leaseDao.getLeaseInformationWithDateRange(dealerID, startDate, endDate);
        return new ResponseEntity<>(contracts, HttpStatus.OK);
    }
}
