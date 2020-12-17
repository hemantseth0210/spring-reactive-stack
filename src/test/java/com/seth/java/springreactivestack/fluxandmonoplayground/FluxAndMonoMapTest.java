package com.seth.java.springreactivestack.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

public class FluxAndMonoMapTest {

    List<String> names = Arrays.asList("Adam", "Anna", "Jack", "Cindy");

    @Test
    public void transformUsingMap(){
        Flux<String> stringFlux = Flux.fromIterable(names)
                .map(String::toUpperCase)
                .log();
        StepVerifier.create(stringFlux)
                .expectNext("ADAM", "ANNA", "JACK", "CINDY")
                .verifyComplete();
    }

    @Test
    public void transformUsingMap_length(){
        Flux<Integer> integerFlux = Flux.fromIterable(names)
                .map(String::length)
                .log();
        StepVerifier.create(integerFlux)
                .expectNext(4,4,4,5)
                .verifyComplete();
    }

    @Test
    public void transformUsingMap_length_repeat(){
        Flux<Integer> integerFlux = Flux.fromIterable(names)
                .map(String::length)
                .repeat(1)
                .log();
        StepVerifier.create(integerFlux)
                .expectNext(4,4,4,5)
                .expectNext(4,4,4,5)
                .verifyComplete();
    }

    @Test
    public void transformUsingMap_map_filter(){
        Flux<String> integerFlux = Flux.fromIterable(names)
                .filter(s -> s.length()>4)
                .map(String::toUpperCase)
                .log();
        StepVerifier.create(integerFlux)
                .expectNext("CINDY")
                .verifyComplete();
    }
}
