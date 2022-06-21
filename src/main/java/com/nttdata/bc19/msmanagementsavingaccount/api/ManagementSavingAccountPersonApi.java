package com.nttdata.bc19.msmanagementsavingaccount.api;

import com.nttdata.bc19.msmanagementsavingaccount.model.SavingAccountPerson;
import com.nttdata.bc19.msmanagementsavingaccount.request.SavingAccountPersonRequest;
import com.nttdata.bc19.msmanagementsavingaccount.service.IManagementSavingAccountPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/saving-account")
public class ManagementSavingAccountPersonApi {

    @Autowired
    private IManagementSavingAccountPersonService managementSavingAccountPersonService;

    @PostMapping()
    public Mono<SavingAccountPerson> create(@RequestBody SavingAccountPersonRequest savingAccountPersonRequest){
        return managementSavingAccountPersonService.create(savingAccountPersonRequest);
    }

    @PutMapping()
    public Mono<SavingAccountPerson> update(@RequestBody SavingAccountPerson savingAccountPerson){
        return managementSavingAccountPersonService.update(savingAccountPerson);
    }

    @GetMapping()
    public Flux<SavingAccountPerson> findAll(){
        return managementSavingAccountPersonService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<SavingAccountPerson> findById(@PathVariable String id){ return managementSavingAccountPersonService.findById(id); }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteSA(@PathVariable String id){
        return managementSavingAccountPersonService.deleteById(id);
    }

    @GetMapping("/findByNumberAccount/{accountNumber}")
    public Mono<SavingAccountPerson> findByNumberAccount(@PathVariable String accountNumber){ return managementSavingAccountPersonService.findByAccountNumber(accountNumber); }
}
