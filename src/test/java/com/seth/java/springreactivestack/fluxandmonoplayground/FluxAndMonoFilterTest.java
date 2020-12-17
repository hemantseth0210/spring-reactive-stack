package com.seth.java.springreactivestack.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

public class FluxAndMonoFilterTest {

    List<String> names = Arrays.asList("Adam", "Anna", "Jack", "Cindy");

    @Test
    public void filterTest(){
        Flux<String> stringFlux = Flux.fromIterable(names)
                .filter(s -> s.startsWith("A"))
                .log();

        StepVerifier.create(stringFlux)
                .expectNext("Adam", "Anna")
                .verifyComplete();
    }
}
