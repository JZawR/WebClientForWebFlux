package com.example.webclientforwebflux;

import model.Car;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.Objects;

public class WebClientForWebFluxApplication {

    public static void main(String[] args) {

        WebClient client = WebClient.create("http://localhost:8081");

        Flux<Car> carFlux = client
                .get()
                .uri("all")
                .exchangeToFlux(clientResponse -> clientResponse.bodyToFlux(Car.class))
                .cache();
        carFlux.subscribe(System.out::println);

        Car lastElement = client
                .get()
                .uri(Objects.requireNonNull(carFlux.blockLast()).getId())
                .exchangeToMono(clientResponse -> clientResponse.bodyToMono(Car.class))
                .block();
        System.out.println("\nПоследний элемент: " + lastElement);
    }

}
