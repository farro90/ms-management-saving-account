package com.nttdata.bc19.msmanagementsavingaccount.model.responseWC;

import lombok.Data;

@Data
public class PasiveProduct {
    private String id;
    private String name;
    private double transactionCommission;
    private double minimumOpeningAmount;
    private double minimumVIPAmount;
    private int numLimitMovements;
    private Boolean allowBusinessClient;
    private Boolean allowPersonClient;
    private Boolean mustHaveCreditCardVIP;
}
