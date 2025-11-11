package org.my.tutorial.creating;

import jdk.swing.interop.SwingInterOpUtils;
import reactor.core.publisher.BufferOverflowStrategy;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

import static java.time.LocalTime.now;

public class HandlingErrors {

    public static void main(String[] args) throws InterruptedException {

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
        // o ... by falling back
        // o ... to a value: onErrorReturn (Flux|Mono)
        /*Flux.range(1, 5)
                .map(i -> {
                    if (i==4) throw new RuntimeException("Boom!...");
                    return i;
                })
                .onErrorReturn(0)
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .subscribe(val -> System.out.println("Subscriber Received "+val),
                        err -> System.out.println("Subscriber Failed with error "+err),
                        () -> System.out.println("Done"));*/
        // o ... to a completion ("swallowing" the error): onErrorComplete (Flux|Mono)
        /*Flux.range(1, 5)
                .map(i -> {
                    if (i==4) throw new RuntimeException("Boom!...");
                    return i;
                })
                .onErrorComplete()
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .subscribe(val -> System.out.println("Subscriber Received "+val),
                        err -> System.out.println("Subscriber Failed with error "+err),
                        () -> System.out.println("Done"));*/
        // o ... to a Publisher or Mono, possibly different ones depending on the error: Flux#onErrorResume and Mono#onErrorResume
        /*Flux.range(1, 5)
                .map(i -> {
                    if (i==4) throw new RuntimeException("Boom!...");
                    return i;
                })
                .onErrorResume(error -> Flux.range(10, 5))
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .subscribe(val -> System.out.println("Subscriber Received "+val),
                        err -> System.out.println("Subscriber Failed with error "+err),
                        () -> System.out.println("Done"));*/

        // o ... by retrying...
        // o ... with a simple policy (max number of attempts): retry (Flux|Mono), retry(long) (Flux|Mono)
        /*Flux.range(1, 5)
                .map(i -> {
                    if (i==4) throw new RuntimeException("Boom!...");
                    return i;
                })
                .retry(1)
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .subscribe(val -> System.out.println("Subscriber Received "+val),
                        err -> System.out.println("Subscriber Failed with error "+err),
                        () -> System.out.println("Done"));*/

        // o ... triggered by a companion control Flux: retryWhen (Flux|Mono)
        /*Flux.range(1, 5)
                .map(i -> {
                    if (i==4) throw new RuntimeException("Boom!...");
                    return i;
                })
                .retryWhen(Retry.fixedDelay(1, Duration.ofMillis(1000)))
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .subscribe(val -> System.out.println("Subscriber Received "+val),
                        err -> System.out.println("Subscriber Failed with error "+err),
                        () -> System.out.println("Done"));

        Thread.sleep(1500);*/
        // o ... using a standard back-off strategy (exponential backoff with jitter): retryWhen(Retry.backoff(...)) (Flux|Mono) (see also other factory methods)
        // o ... backoff
        /*Flux.range(1, 5)
                .map(i -> {
                    if (i==4) {
                        System.out.println("Current time " +now());
                        throw new RuntimeException("Boom!...");}
                    return i;
                })
                .retryWhen(Retry.backoff(2, Duration.ofMillis(3000)))
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .subscribe(val -> System.out.println("Subscriber Received "+val),
                        err -> System.out.println("Subscriber Failed with error "+err),
                        () -> System.out.println("Done"));

        Thread.sleep(10000);*/
        // o ...
        /*Flux.range(1, 5)
                .map(i -> {
                    if (i==4) throw new RuntimeException("Boom!...");
                    return i;
                })
                .retryWhen(Retry.withThrowable(signal -> Flux.just("A")))
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .subscribe(val -> System.out.println("Subscriber Received "+val),
                        err -> System.out.println("Subscriber Failed with error "+err),
                        () -> System.out.println("Done"));

        Thread.sleep(1500);*/

        // . I want to deal with backpressure "errors" (request max from upstream and apply the strategy when downstream does not produce enough requests)...
        // o ... by throwing a special IllegalStateException: Flux#onBackPressureError

        /*Flux.range(1, 10)
                .log()
                .onBackpressureError()
                .publishOn(Schedulers.single(), 1)
                .doOnNext(i -> {
                    System.out.println("Processing "+i);
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                })
                .subscribe(
                        v -> System.out.println("Received " + v),
                                e -> System.err.println("Failed with error "+e));

        Thread.sleep(5000);*/

        // o ... except the last one seen: Flux#onBackPressureLatest

        /*Flux.range(1, 10)
                .log()
                .onBackpressureLatest()
                .publishOn(Schedulers.single(), 1)
                .doOnNext(i -> {
                    System.out.println("Processing "+i);
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                })
                .doOnDiscard(Integer.class, val -> System.out.println("Discarding" +val))
                .subscribe(
                        v -> System.out.println("Received " + v),
                        e -> System.err.println("Failed with error "+e));

        Thread.sleep(6000);*/

        // o ... by buffering excess values (bounded or unbounded): Flux#onBackPressureBuffer
        /*Flux.range(1, 10)
                .log()
                .onBackpressureBuffer(3)
                .publishOn(Schedulers.single(), 1)
                .doOnNext(i -> {
                    System.out.println("Processing "+i);
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                })
                .doOnDiscard(Integer.class, val -> System.out.println("Discarding" +val))
                .subscribe(
                        v -> System.out.println("Received " + v),
                        e -> System.err.println("Failed with error "+e));

        Thread.sleep(6000);*/

        // o ... and applying a strategy when bounded buffer also overflows: Flux#onBackPressureBuffer with a BufferOverflowStrategy
        /*Flux.range(1, 10)
                .log()
                .onBackpressureBuffer(3, BufferOverflowStrategy.DROP_LATEST)
                .publishOn(Schedulers.single(), 1)
                .doOnNext(i -> {
                    System.out.println("Processing "+i);
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                })
                .doOnDiscard(Integer.class, val -> System.out.println("Discarding " +val))
                .subscribe(
                        v -> System.out.println("Received " + v),
                        e -> System.err.println("Failed with error "+e));

        Thread.sleep(6000);*/

    }
}
