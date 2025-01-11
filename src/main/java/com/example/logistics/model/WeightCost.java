package com.example.logistics.model;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.*;

import java.util.*;

@Entity
public class WeightCost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int weight;
    private int cost;

    @ManyToOne
    @JoinColumn(name = "transfer_id")
    private Transfer transfer;

    private WeightCost(){}

    public WeightCost(int weight, int cost) {
        this.weight = weight;
        this.cost = cost;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }


    @Override
    public String toString() {
        return "\n {\n  " + "\"weight\": " + weight + ",\n  " + "\"cost\": " + cost + "\n }";
    }

}
