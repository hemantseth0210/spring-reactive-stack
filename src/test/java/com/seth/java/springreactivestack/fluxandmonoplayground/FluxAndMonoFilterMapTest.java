package com.seth.java.springreactivestack.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static reactor.core.scheduler.Schedulers.parallel;

public class FluxAndMonoFilterMapTest {

    @Test
    public void transformUsingFlatMap(){
        Flux<String> stringFlux = Flux.fromIterable(Arrays.asList("A", "B", "C", "D", "E", "F"))
                .flatMap(s -> {
                    return Flux.fromIterable(convertToList(s));
                })
                .log();

        StepVerifier.create(stringFlux)
                .expectNextCount(12)
                .verifyComplete();

    }

    @Test
    public void transformUsingFlatMap_UsingParallel(){
        Flux<String> stringFlux = Flux.fromIterable(Arrays.asList("A", "B", "C", "D", "E", "F"))
                .window(2)
                .flatMap(s -> s.map(this::convertToList).subscribeOn(parallel()))
                .flatMap(s -> Flux.fromIterable(s))
                .log();

        StepVerifier.create(stringFlux)
                .expectNextCount(12)
                .verifyComplete();

    }

    @Test
    public void transformUsingFlatMap_UsingParallel_maintain_Order(){
        Flux<String> stringFlux = Flux.fromIterable(Arrays.asList("A", "B", "C", "D", "E", "F"))
                .window(2)
                .flatMapSequential(s -> s.map(this::convertToList).subscribeOn(parallel()))
                .flatMap(s -> Flux.fromIterable(s))
                .log();

        StepVerifier.create(stringFlux)
                .expectNextCount(12)
                .verifyComplete();

    }


    private List<String> convertToList(String s) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Arrays.asList(s, "newValue");
    }
}
