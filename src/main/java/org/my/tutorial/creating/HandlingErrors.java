package org.my.tutorial.creating;

import jdk.swing.interop.SwingInterOpUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicInteger;

public class HandlingErrors {

    public static void main(String[] args) {

        // . I want to create an error sequence: error (Flux|Mono)
        /*Flux.error(new RuntimeException("Boom"))
                .subscribe(System.out::println, System.err::println);*/

        // o ... to replace the completion of successful Flux: .concat(Flux.error(e))
        /*var errorFlux = Flux.concat(Flux.just("A", "B", "C", "D", "E"), Flux.range(1, 5), Flux.error(new RuntimeException("Boom")));

        errorFlux.subscribe(System.out::println, System.err::println, () -> System.out.println("DONE"));*/

        // o ... to replace the emission of successful Mono: .then(Mono.error(e))
        /*Flux.just("Z", "Y", "X")
                //.doOnNext(val -> System.out.println("onNext Received "+val))
                .then(Mono.error(new RuntimeException("Boom")))
                .subscribe(System.out::println, System.err::println, () -> System.out.println("Done"));*/

        // o ... if too much time elapses between onNexts: timeout (Flux|Mono)
        /*Flux.just("A", "B", "C", "D")
                .map(l -> {
                    if (l.equals("C")) {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    return l;
                })
                .timeout(Duration.ofMillis(1000))
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .subscribe(System.out::println, System.err::println, () -> System.out.println("Done"));*/

        // o ... lazily: error(Supplier<Throwable>) (Flux|Mono)
        /*AtomicInteger lazyCount = new AtomicInteger();
        AtomicInteger eagerCount = new AtomicInteger();
        var errFlux1 = Flux.error(() -> new RuntimeException("Lazy Boom " + lazyCount.incrementAndGet()))
                .doOnEach(signal -> System.out.println("Signal "+signal));
        var errFlux2 = Flux.error(new RuntimeException("Eager Boom " + eagerCount.incrementAndGet()))
                .doOnEach(signal -> System.out.println("Signal "+signal));

        errFlux1.subscribe(val -> System.out.println("Subscriber 1 Received "+val),
                err -> System.out.println("Subscriber 1 Failed with error "+err),
                () -> System.out.println("Done"));
        errFlux2.subscribe(val -> System.out.println("Subscriber 1 Received "+val),
                err -> System.out.println("Subscriber 1 Failed with error "+err),
                () -> System.out.println("Done"));

        errFlux1.subscribe(val -> System.out.println("Subscriber 2 Received "+val),
                err -> System.out.println("Subscriber 2 Failed with error "+err),
                () -> System.out.println("Done"));
        errFlux2.subscribe(val -> System.out.println("Subscriber 2 Received "+val),
                err -> System.out.println("Subscriber 2 Failed with error "+err),
                () -> System.out.println("Done"));*/

        // . I want the try/catch equivalent of:
        // o ... throwing: error (Flux|Mono)
        /*Mono.error(new RuntimeException("Boom"))
                .subscribe(val -> System.out.println("Subscriber Received "+val),
                        err -> System.out.println("Subscriber Failed with error "+err),
                        () -> System.out.println("Done"));*/
        // o ... catching an exception
        // o ... and falling back to a default value: onErrorReturn (Flux|Mono)
        /*Mono.error(new RuntimeException("Boom RuntimeException!..."))
                .onErrorReturn("That's not true")
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .subscribe(val -> System.out.println("Subscriber Received "+val),
                        err -> System.out.println("Subscriber Failed with error "+err),
                        () -> System.out.println("Done"));*/

        // o ... and swallowing the error (ie complete): onErrorComplete (Flux|Mono)
        /*Mono.error(new RuntimeException("Boom RuntimeException|..."))
                .onErrorComplete()
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .subscribe(val -> System.out.println("Subscriber Received "+val),
                        err -> System.out.println("Subscriber Failed with error "+err),
                        () -> System.out.println("Done"));*/

        // o ... and falling back to another Flux or Mono: onErrorResume (Flux|Mono)
        /*Mono.error(new RuntimeException("Boom ..."))
                .onErrorResume(err -> Mono.just("Apple"))
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .subscribe(val -> System.out.println("Subscriber Received "+val),
                        err -> System.out.println("Subscriber Failed with error "+err),
                        () -> System.out.println("Done"));*/

        // o ... and wrapping and re-throwing: .onErrorMap(t -> new RuntimeException(t)) (Flux|Mono)

        /*Flux.error(new RuntimeException("Boom RuntimeException!..."))
                .onErrorMap(IllegalStateException::new)
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .subscribe(val -> System.out.println("Subscriber Received "+val),
                        err -> System.out.println("Subscriber Failed with error "+err),
                        () -> System.out.println("Done"));*/

        // o ... the finally block: doFinally (Flux|Mono)
        /*Flux.error(new RuntimeException("Boom ... "))
                .doFinally(signal -> System.out.println("Signal "+signal))
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .subscribe(val -> System.out.println("Subscriber Received "+val),
                        err -> System.out.println("Subscriber Failed with error "+err),
                        () -> System.out.println("Done"));*/

        // the using pattern from Java 7: using (Flux|Mono) factory method

        /*Mono.using(() -> "Resource Supplier ABC",
                resource -> {
                    System.out.println("Using the resource to perform a specific task " +resource);
                    return Mono.just(resource);
                },
                (resource) -> System.out.println("Cleaning up resource " +resource))
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .subscribe(val -> System.out.println("Subscriber Received "+val),
                        err -> System.out.println("Subscriber Failed with error "+err),
                        () -> System.out.println("Done"));*/

        // Supplier - Initialize resource,
        // Function - To perform tasks using resource initialized by supplier,
        // Consumer - cleanup resource initialized by supplier

        // . I want to recover from errors...


    }
}
