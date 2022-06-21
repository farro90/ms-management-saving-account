package com.nttdata.bc19.msmanagementsavingaccount.webclient;

import com.nttdata.bc19.msmanagementsavingaccount.model.responseWC.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IServiceWC {
    Mono<PersonClient> findPersonClientById(String id);

    Mono<BusinessClient> findBusinessClientById(String id);

    Mono<PasiveProduct> findPasiveProductById(String id);

    Mono<ActiveProduct> findActiveProductById(String id);

    Flux<CreditCardPerson> findCreditCardByIdPersonClient(String idPersonClient);
    Mono<Long> countCreditCardByIdPersonClient(String idPersonClient);
}
