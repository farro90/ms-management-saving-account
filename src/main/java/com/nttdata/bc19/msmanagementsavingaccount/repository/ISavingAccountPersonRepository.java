package com.nttdata.bc19.msmanagementsavingaccount.repository;

import com.nttdata.bc19.msmanagementsavingaccount.model.SavingAccountPerson;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ISavingAccountPersonRepository extends ReactiveMongoRepository<SavingAccountPerson, String> {
    Flux<SavingAccountPerson> findByIdPersonClient(String id);

    Mono<Long> countByIdPersonClient(String id);

    Flux<SavingAccountPerson> findByIdPersonClientAndIdPasiveProduct(String idPersonClient, String idPasiveProduct);

    Mono<Long> countByIdPersonClientAndIdPasiveProduct(String idPersonClient, String idPasiveProduct);

    Mono<SavingAccountPerson> findByAccountNumber(String accountNumber);

    Mono<Boolean> existsByAccountNumber(String accountNumber);
}
