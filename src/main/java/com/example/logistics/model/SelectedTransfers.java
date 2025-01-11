package com.example.logistics.model;

import jakarta.persistence.Entity;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class SelectedTransfers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int totalCost;
    private int totalWeight;

    @OneToMany
    private List<WeightCost> weightCostList;

    public SelectedTransfers(List<WeightCost> weightCostList, int totalCost, int totalWeight) {
        this.weightCostList = weightCostList;
        this.totalCost = totalCost;
        this.totalWeight = totalWeight;
    }

    @Override
    public String toString() {
        StringBuilder formattedString = new StringBuilder("{\n");

        formattedString.append("\"selectedTransfers\": ");
        formattedString.append(weightCostList.toString().replace("]", "\n]"));
        formattedString.append(",\n \"totalCost\": ").append(totalCost);
        formattedString.append(",\n \"totalWeight\": ").append(totalWeight);
        formattedString.append("\n}");

        return formattedString.toString();
    }

    public List<WeightCost> getWeightCostList() {
        return weightCostList;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public int getTotalWeight() {
        return totalWeight;
    }
}
