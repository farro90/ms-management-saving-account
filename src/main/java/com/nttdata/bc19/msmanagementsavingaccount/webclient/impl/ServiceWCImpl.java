package com.nttdata.bc19.msmanagementsavingaccount.webclient.impl;

import com.nttdata.bc19.msmanagementsavingaccount.exception.ModelNotFoundException;
import com.nttdata.bc19.msmanagementsavingaccount.model.responseWC.*;
import com.nttdata.bc19.msmanagementsavingaccount.webclient.IServiceWC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ServiceWCImpl implements IServiceWC {

    //private static final String URL_GATEWAY = "${my.property.URL_GATEWAY}";

    private static final String URL_GATEWAY = "http://localhost:8080";

    @Autowired
    private WebClient.Builder webClient;

    @Override
    public Mono<PersonClient> findPersonClientById(String id) {
        return webClient.baseUrl(URL_GATEWAY).build().get().uri("/api/client/person/".concat(id))
                .retrieve()
                .bodyToMono(PersonClient.class)
                .onErrorResume(error -> {
                    WebClientResponseException response = (WebClientResponseException) error;
                    if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                        return Mono.error(new ModelNotFoundException("CLIENT NOT FOUND"));
                    }
                    return Mono.error(error);
                });
    }

    @Override
    public Mono<BusinessClient> findBusinessClientById(String id) {
        return webClient.baseUrl(URL_GATEWAY).build().get().uri("/api/client/business/".concat(id))
                .retrieve()
                .bodyToMono(BusinessClient.class)
                .onErrorResume(error -> {
                    WebClientResponseException response = (WebClientResponseException) error;
                    if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                        return Mono.error(new ModelNotFoundException("BUSINESS NOT FOUND"));
                    }
                    return Mono.error(error);
                });
    }

    @Override
    public Mono<ActiveProduct> findActiveProductById(String id) {
        return webClient.baseUrl(URL_GATEWAY).build().get().uri("/api/product/active/".concat(id))
                .retrieve()
                .bodyToMono(ActiveProduct.class)
                .onErrorResume(error -> {
                    WebClientResponseException response = (WebClientResponseException) error;
                    if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                        return Mono.error(new ModelNotFoundException("PRODUCT NOT FOUND"));
                    }
                    return Mono.error(error);
                });
    }

    @Override
    public Flux<CreditCardPerson> findCreditCardByIdPersonClient(String idPersonClient) {
        return webClient.baseUrl(URL_GATEWAY).build().get().uri("/api/credit-card/person/findByIdPersonClient/".concat(idPersonClient))
                .retrieve()
                .bodyToFlux(CreditCardPerson.class)
                .onErrorResume(error -> {
                    WebClientResponseException response = (WebClientResponseException) error;
                    if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                        return Flux.error(new ModelNotFoundException("PERSON CLIENT NOT FOUND"));
                    }
                    return Flux.error(error);
                });
    }

    @Override
    public Mono<Long> countCreditCardByIdPersonClient(String idPersonClient) {
        return webClient.baseUrl(URL_GATEWAY).build().get().uri("/api/credit-card/person/countByIdPersonClient/".concat(idPersonClient))
                .retrieve()
                .bodyToMono(Long.class)
                .onErrorResume(error -> {
                    WebClientResponseException response = (WebClientResponseException) error;
                    if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                        return Mono.error(new ModelNotFoundException("PERSON CLIENT NOT FOUND"));
                    }
                    return Mono.error(error);
                });
    }

    @Override
    public Mono<PasiveProduct> findPasiveProductById(String id) {
        return webClient.baseUrl(URL_GATEWAY).build().get().uri("/api/product/pasive/".concat(id))
                .retrieve()
                .bodyToMono(PasiveProduct.class)
                .onErrorResume(error -> {
                    WebClientResponseException response = (WebClientResponseException) error;
                    if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                        return Mono.error(new ModelNotFoundException("PRODUCT NOT FOUND"));
                    }
                    return Mono.error(error);
                });
    }
}
