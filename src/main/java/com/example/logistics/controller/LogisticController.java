package com.example.logistics.controller;

import com.example.logistics.model.SelectedTransfers;
import com.example.logistics.model.Transfer;
import com.example.logistics.model.WeightCost;
import com.example.logistics.service.Knapsack;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;



import java.util.List;

@RestController
@RequestMapping("/api/logistics")
public class LogisticController {

    private final Knapsack knapsackService;

    public LogisticController(Knapsack knapsackService) {
        this.knapsackService = knapsackService;
    }

    @PostMapping("/calculate-transfers")
    public SelectedTransfers calculateTransfers(@RequestBody Transfer transferData) {
        System.out.println("Received transfer data: " + transferData);
        return knapsackService.knapsackAlgorithm(transferData, transferData.getMaxWeight());
    }

    @PostMapping("/transfers")
    public ResponseEntity<String> addTransfer(@RequestBody Transfer transfer) {
        return ResponseEntity.status(HttpStatus.CREATED).body("Transfer created successfully");
    }

}
