package com.nttdata.bc19.msmanagementsavingaccount.service;

import com.nttdata.bc19.msmanagementsavingaccount.model.SavingAccountPerson;
import com.nttdata.bc19.msmanagementsavingaccount.request.SavingAccountPersonRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IManagementSavingAccountPersonService {

    Mono<SavingAccountPerson> create(SavingAccountPersonRequest savingAccountPersonRequest);
    Mono<SavingAccountPerson> update(SavingAccountPerson savingAccountPerson);
    Mono<Void>deleteById(String id);
    Mono<SavingAccountPerson> findById(String id);
    Flux<SavingAccountPerson> findAll();

    Mono<SavingAccountPerson> findByAccountNumber(String accountNumber);
    Mono<Boolean> existsByAccountNumber(String accountNumber);
}
