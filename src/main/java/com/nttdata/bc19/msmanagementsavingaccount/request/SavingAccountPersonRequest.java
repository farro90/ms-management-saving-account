package com.nttdata.bc19.msmanagementsavingaccount.request;

import lombok.Data;

@Data
public class SavingAccountPersonRequest {
    private double amount;
    private String accountNumber;
    private String idPersonClient;
    private String idPasiveProduct;
}
