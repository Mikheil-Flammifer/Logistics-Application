package com.example.logistics.model;

import java.util.*;
import jakarta.persistence.*;

@Entity
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int maxWeight;

    @OneToMany(mappedBy = "transfer")
    private List<WeightCost> availableTransfers;

   public Transfer(List<WeightCost> availableTransfers, int maxWeight){
       this.availableTransfers = availableTransfers;
       this.maxWeight = maxWeight;
   }


    public int getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(int maxWeight) {
        this.maxWeight = maxWeight;
    }

    public List<WeightCost> getAvailableTransfers() {
        return availableTransfers;
    }

    public void setAvailableTransfers(List<WeightCost> availableTransfers) {
        this.availableTransfers = availableTransfers;
    }
}

