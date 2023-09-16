package com.narayana.jpa.model;

import javax.persistence.*;

@Entity
@Table(name = "account")
public class Account {
    @Id()
    //@GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "account_number")
    private Integer accountNumber;

    @Column(name = "account_holder_name")
    private String accountHolderName;

    @Column(name = "amount")
    private Double amount;

    @Version
    private Integer version; // allowed java.sql.Timestamp

    public Integer getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Integer accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
