package com.nttdata.bc19.msmanagementsavingaccount.service.impl;

import com.nttdata.bc19.msmanagementsavingaccount.exception.ModelNotFoundException;
import com.nttdata.bc19.msmanagementsavingaccount.model.SavingAccountPerson;
import com.nttdata.bc19.msmanagementsavingaccount.model.responseWC.PasiveProduct;
import com.nttdata.bc19.msmanagementsavingaccount.model.responseWC.PersonClient;
import com.nttdata.bc19.msmanagementsavingaccount.repository.ISavingAccountPersonRepository;
import com.nttdata.bc19.msmanagementsavingaccount.request.SavingAccountPersonRequest;
import com.nttdata.bc19.msmanagementsavingaccount.service.IManagementSavingAccountPersonService;
import com.nttdata.bc19.msmanagementsavingaccount.util.PasiveProductType;
import com.nttdata.bc19.msmanagementsavingaccount.webclient.impl.ServiceWCImpl;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class ManagementSavingAccountPersonServiceImpl implements IManagementSavingAccountPersonService {

    @Autowired
    ISavingAccountPersonRepository savingAccountPersonRepository;

    @Autowired
    private ServiceWCImpl clientServiceWC;

    @Override
    public Mono<SavingAccountPerson> create(SavingAccountPersonRequest savingAccountPersonRequest) {
        return clientServiceWC.findPersonClientById(savingAccountPersonRequest.getIdPersonClient())
                .switchIfEmpty(Mono.error(new Exception()))
                .flatMap(personClientResponse ->
                        clientServiceWC.findPasiveProductById(savingAccountPersonRequest.getIdPasiveProduct())
                                .switchIfEmpty(Mono.error(new Exception()))
                                .flatMap(savingAccountResponse ->
                                        clientServiceWC.countCreditCardByIdPersonClient(savingAccountPersonRequest.getIdPersonClient())
                                                .switchIfEmpty(Mono.error(new Exception()))
                                                .flatMap(countCreditCardResponse ->
                                                        savingAccountPersonRepository.countByIdPersonClientAndIdPasiveProduct(savingAccountPersonRequest.getIdPersonClient(), savingAccountPersonRequest.getIdPasiveProduct())
                                                        .switchIfEmpty(Mono.error(new Exception()))
                                                        .flatMap(savingAccountCountResponse -> this.businessLogicSavingAccountPerson(personClientResponse, savingAccountResponse, savingAccountPersonRequest, savingAccountCountResponse, countCreditCardResponse))
                                                )
                                )
                );
    }

    @Override
    public Mono<SavingAccountPerson> update(SavingAccountPerson savingAccountPerson) {
        savingAccountPerson.setUpdatedAt(LocalDateTime.now());
        return savingAccountPersonRepository.save(savingAccountPerson);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return savingAccountPersonRepository.deleteById(id);
    }

    @Override
    public Mono<SavingAccountPerson> findById(String id) {
        return savingAccountPersonRepository.findById(id);
    }

    @Override
    public Flux<SavingAccountPerson> findAll() {
        return savingAccountPersonRepository.findAll();
    }

    @Override
    public Mono<SavingAccountPerson> findByAccountNumber(String accountNumber) {
        return savingAccountPersonRepository.findByAccountNumber(accountNumber);
    }

    @Override
    public Mono<Boolean> existsByAccountNumber(String accountNumber) {
        return savingAccountPersonRepository.existsByAccountNumber(accountNumber);
    }

    private Mono<SavingAccountPerson> businessLogicSavingAccountPerson(PersonClient personClient, PasiveProduct savingAccount, SavingAccountPersonRequest savingAccountPersonRequest, Long savingAccountCount, Long creditCardCount){
        SavingAccountPerson savingAccountPerson = new SavingAccountPerson();
        savingAccountPerson.setId(new ObjectId().toString());
        savingAccountPerson.setCreatedAt(LocalDateTime.now());
        savingAccountPerson.setAmount(savingAccountPersonRequest.getAmount());
        savingAccountPerson.setAccountNumber(savingAccountPersonRequest.getAccountNumber()); //Generate number account
        savingAccountPerson.setLastTrasactionDate(LocalDateTime.now());
        savingAccountPerson.setIdPersonClient(savingAccountPersonRequest.getIdPersonClient());
        savingAccountPerson.setIdPasiveProduct(savingAccountPersonRequest.getIdPasiveProduct());
        savingAccountPerson.setPersonClient(personClient);
        savingAccountPerson.setPasiveProduct(savingAccount);
        savingAccountPerson.setNumberMovements(0);
        savingAccountPerson.setVIP(false);

        if(savingAccount.getMinimumVIPAmount() < savingAccountPersonRequest.getAmount() && creditCardCount > 0){
            savingAccountPerson.setVIP(true);
        }

        boolean existsClientPersonPasiveProduct = savingAccountCount > 0 ? true : false;

        if(!savingAccount.getName().equals(PasiveProductType.AHORRO.name())){
            return Mono.error(new ModelNotFoundException("The account is not saving."));
        }
        if(!savingAccount.getAllowPersonClient()) {
            return Mono.error(new ModelNotFoundException("Type of account not allowed for person client"));
        }
        if(existsClientPersonPasiveProduct) {
            return Mono.error(new ModelNotFoundException("The customer already has an account"));
        }
        if(savingAccount.getMinimumOpeningAmount() > savingAccountPersonRequest.getAmount()) {
            return Mono.error(new ModelNotFoundException("The minimum amount for opening this account is greater than the amount deposited"));
        }
        else{
            return savingAccountPersonRepository.save(savingAccountPerson);
        }
    }
}
