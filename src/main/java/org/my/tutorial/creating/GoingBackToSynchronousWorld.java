package org.my.tutorial.creating;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class GoingBackToSynchronousWorld {

    public static void main(String[] args) throws InterruptedException {
        // Note: all of these methods except Mono#toFuture will throw an UnsupportedOperatorException if called from within a Scheduler marked
        // as "non-blocking only" (by default parallel() and single())

        // . I have a Flux<T> and i want to:
        // o ... block until i can get the first element: Flux#blockFirst
        /*Flux.just(1, 2, 3, 4)
                .delayElements(Duration.ofMillis(1000))
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .doOnDiscard(Integer.class, dropped -> System.out.println("Dropped "+dropped))
                .blockFirst();*/
        // o ... with a timeout: Flux#blockFirst(Duration)
        /*Flux.just(1, 2, 3, 4, 5)
                .delayElements(Duration.ofMillis(1000))
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .blockFirst(Duration.ofMillis(500));*/
        // o ... block until i can get last element (or null if empty):Flux#blockLast
        /*Flux.just(1,2,3,4,5)
                .delayElements(Duration.ofMillis(500))
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .blockLast();*/

        /*Flux.empty()
                .delayElements(Duration.ofMillis(500))
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .blockLast();*/
        // o ... synchronously switch to an Iterable<T>: Flux#toIterable
        /*Flux.just(1, 2, 3, 4, 5)
                .delayElements(Duration.ofMillis(500))
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .toIterable()
                .forEach(i -> System.out.println("Received "+i));*/

        // o ... synchronous switch to stream
        /*Flux.just(1,2,3,4,5)
                .delayElements(Duration.ofMillis(500))
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .toStream()
                .forEach(i -> System.out.println("Received "+i));*/

        // . I have a Mono and i want:
        // o .... to block until i can get the value# Mono#block
        /*Mono.just(1)
                .delayElement(Duration.ofMillis(1000))
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .block();*/
        // o .... with a timeout
        /*Mono.just(1)
                .delayElement(Duration.ofMillis(1000))
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .block(Duration.ofMillis(500));*/
        // o ... a CompletableFuture<T>:Mono#toFuture

        /*Mono.just(1)
                .delayElement(Duration.ofMillis(1000))
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .toFuture();

        Thread.sleep(3000);*/
    }
}
