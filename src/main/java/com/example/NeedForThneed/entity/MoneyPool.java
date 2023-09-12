package com.example.NeedForThneed.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name = "money_pool")
public class MoneyPool extends DistributedEntity {

    @Transient
    @NotNull(message = "Total Value must not be null")
    @Min(value = 0, message = "Total Value must not be less than 0")
    @Column(name = "total_value")
    private Integer totalValue;

    @OneToMany
    @JoinColumn(name = "money_contributions")
    private List<MoneyContribution> moneyContributions;

    public MoneyPool() {
    }

    public Integer getTotalValue() {
        return totalValue;
    }

    public void addToTotal(Integer amount) {
        this.totalValue += amount;
    }

    public void removeFromTotal(Integer amount) {
        if (this.totalValue - amount >= 0) {
            this.totalValue -= amount;
        } else {
            throw new RuntimeException("Can't set total amount to less than 0");
        }
    }

    public void resetTotalValue() {
        this.totalValue = 0;
    }

    public void updatePool() {
        this.totalValue = this.moneyContributions.stream()
                .map(MoneyContribution::getAmount)
                .mapToInt(Integer::intValue)
                .sum();
    }

    public List<MoneyContribution> getMoneyContributions() {
        return moneyContributions;
    }

    public void setMoneyContributions(List<MoneyContribution> moneyContributions) {
        this.moneyContributions = moneyContributions;
    }
}
