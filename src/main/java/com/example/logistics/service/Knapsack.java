package com.example.logistics.service;

import com.example.logistics.model.SelectedTransfers;
import com.example.logistics.model.Transfer;
import com.example.logistics.model.WeightCost;
import com.example.logistics.repository.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class Knapsack {
    @Autowired
    private TransferRepository transferR;

    public SelectedTransfers knapsackAlgorithm(Transfer data, int maxWeight) {
        List<WeightCost> costs = data.getAvailableTransfers();
        int n = costs.size();
        int[][] dp = new int[n + 1][maxWeight + 1];
        boolean[][] selected = new boolean[n + 1][maxWeight + 1];

        for (int i = 1; i <= n; i++) {
            for (int w = 1; w <= maxWeight; w++) {
                if (costs.get(i - 1).getWeight() <= w) {
                    // If we can include the current item, we check the maximum value
                    int include = costs.get(i - 1).getCost() + dp[i - 1][w - costs.get(i - 1).getWeight()];
                    int exclude = dp[i - 1][w];

                    if (include > exclude) {
                        dp[i][w] = include;
                        selected[i][w] = true;
                    } else {
                        dp[i][w] = exclude;
                        selected[i][w] = false;
                    }
                } else {
                    dp[i][w] = dp[i - 1][w];
                    selected[i][w] = false;
                }
            }
        }

        int w = maxWeight;
        int totalCost = 0;
        int totalWeight = 0;
        List<WeightCost> selectedItemsList = new ArrayList<>();
        for (int i = n; i > 0; i--) {
            if (selected[i][w]) {
                selectedItemsList.add(costs.get(i - 1));
                w -= costs.get(i - 1).getWeight();
                totalCost += costs.get(i - 1).getCost();
                totalWeight += costs.get(i - 1).getWeight();
            }
        }


        SelectedTransfers selectedTransfers = new SelectedTransfers(selectedItemsList, totalCost, totalWeight);
        return selectedTransfers;
    }
}
