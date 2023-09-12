package com.example.NeedForThneed.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name = "money_contribution")
public class MoneyContribution extends DistributedEntity {

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "moneycontribution_user",
            joinColumns = @JoinColumn(name = "money_contribution_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> user;

    @NotNull(message = "Amount must not be null")
    @Min(value = 0, message = "Amount must not be less than 0")
    @Column(name = "amount")
    private Integer amount;

    public MoneyContribution() {
    }

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
