package com.example.dealership_api.controller;

import com.example.dealership_api.dao.SalesDao;
import com.example.dealership_api.model.SalesContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/api/dealership/sales_contracts")
public class SalesContractsController {

    private final SalesDao salesDao;

    @Autowired
    public SalesContractsController(SalesDao salesDao) {
        this.salesDao = salesDao;
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalesContract> getSalesContractById(@PathVariable int id) {
        SalesContract contract = salesDao.getSalesContractById(id);
        return new ResponseEntity<>(contract, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> addSalesContract(@RequestBody SalesContract salesContract) {
        salesDao.addSalesContract(salesContract);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SalesContract>> getSalesInformationWithDateRange(
            @RequestParam int dealerID,
            @RequestParam Date startDate,
            @RequestParam Date endDate) {
        List<SalesContract> contracts = salesDao.getSalesInformationWithDateRange(dealerID, startDate, endDate);
        return new ResponseEntity<>(contracts, HttpStatus.OK);
    }
}
