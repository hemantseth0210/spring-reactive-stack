package com.seth.java.springreactivestack.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

public class FluxAndMonoTest {

    @Test
    public void fluxTest(){
        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
                //.concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                .concatWith(Flux.just("After Error"))
                .log();
        stringFlux
                .subscribe(System.out::println, (e) -> System.err.println(e), () -> System.out.println("Completed"));
    }

    @Test
    public void fluxTest_Exception(){
    Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
                                        .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                                        .log();
        stringFlux
                .subscribe(System.out::println, (e) -> System.err.println(e));
    }
}
