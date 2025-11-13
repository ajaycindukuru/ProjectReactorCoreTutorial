package org.my.tutorial.creating;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class FilteringASequence {

    public static void main(String[] args) throws InterruptedException {
        // . I want to filter a sequence:
        // o ... based on an arbitrary criteria: filter (Flux|Mono)
        /*Flux.range(1, 10)
                .filter(f -> f % 2 == 0)
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .doOnDiscard(Integer.class, val -> System.out.println("Discarded "+val))
                .subscribe(val -> System.out.println("Received "+val));*/
        //o ... that is asynchronously computed:
       /* Flux.range(1, 10)
                .filterWhen(FilteringASequence::isEvenAsync)
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .doOnDiscard(Integer.class, val -> System.out.println("Discarded "+val))
                .subscribe(val -> System.out.println("Received "+val));*/
       // o ... restricting on type of the emitted object: ofType (Flux|Mono)
       /*Flux.concat(Flux.just("A", "B", "C"), Flux.just(1, 3, 4), Flux.just("*", "#"))
               .ofType(String.class)
               .doOnEach(signal -> System.out.println("Signal "+signal))
               .doOnDiscard(Integer.class, val -> System.out.println("Discarded "+val))
               .subscribe(val -> System.out.println("Received "+val));*/
       // o ... by ignoring the values altogether: ignore elements (Flux.ignoreElements|Mono.ignoreElement)
        /*Flux.range(1, 10)
                .ignoreElements()
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .doOnDiscard(Integer.class, val -> System.out.println("Discarded "+val))
                .subscribe(val -> System.out.println("Received "+val));*/
        // o ... by ignoring duplicates:
        // o... in a whole sequence (logical set): Flux#distinct
        /*Flux.just("A", "X", "E", "L", "E", "X", "U", "S")
                .distinct()
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .doOnDiscard(Integer.class, val -> System.out.println("Discarded "+val));*/

        /*Flux.just(
                new Person("Apple", "May", 32),
                new Person("Vankaye", "Pulus", 53),
                new Person("Mule", "Tesla", 32),
                new Person("Ground", "Nut", 67))
                .distinct(Person::age)
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .doOnDiscard(Integer.class, val -> System.out.println("Discarded "+val))
                .subscribe(val -> System.out.println("Received "+val));*/
        // o ... between subsequently emitted items (deduplication): Flux#distinctUntilChanged

        /*Flux.just(0, 1, 1, 2, 3, 5, 8, 13)
                .distinctUntilChanged()
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .doOnDiscard(Integer.class, val -> System.out.println("Discarded "+val))
                .subscribe(val -> System.out.println("Received "+val));*/

        // . I want to keep only subset of the sequence:
        // o ... at the beginning of sequence: Flux#take(long)
        /*Flux.range(1, 10)
                .take(5)
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .doOnDiscard(Integer.class, val -> System.out.println("Discarded "+val))
                .subscribe(val -> System.out.println("Received "+val));*/
        // o ... requesting an unbounded amount from upstream: Flux#take(long, false)
        /*Flux.range(1, 10)
                .take(3, false)
                .doOnRequest(val -> System.out.println("Requested " +val))
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .doOnDiscard(Integer.class, val -> System.out.println("Discarded "+val))
                .subscribe(val -> System.out.println("Received "+val));*/

        // o... based on duration: Flux#take(Duration)
        /*Flux.range(1, 1000)
                .take(Duration.ofMillis(5))
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .doOnDiscard(Integer.class, val -> System.out.println("Discarded "+val))
                .subscribe(val -> System.out.println("Received "+val));*/
        // o ... only the first element, as a Mono: Flux#next()
        /*Flux.range(1, 10)
                .next()
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .doOnDiscard(Integer.class, val -> System.out.println("Discarded "+val))
                .subscribe(val -> System.out.println("Received "+val));*/
        // o ... at the end of the sequence: Flux#takeLast
        /*Flux.range(1, 100)
                .takeLast(5)
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .doOnDiscard(Integer.class, val -> System.out.println("Discarded "+val))
                .subscribe(val -> System.out.println("Received "+val));*/
        // o ... until a criteria is met (inclusive): Flux#takeUntil (predictive-based), Flux#takeUntilOther (companion publisher based)
        /*Flux.range(1, 10)
                .takeUntil(i -> i>5)
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .doOnDiscard(Integer.class, val -> System.out.println("Discarded "+val))
                .subscribe(val -> System.out.println("Received "+val));*/
        /*Flux.interval( Duration.ofMillis(200))
                .takeUntilOther(Flux.interval(Duration.ofSeconds(1)))
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .doOnDiscard(Integer.class, val -> System.out.println("Discarded "+val))
                .subscribe(val -> System.out.println("Received "+val));
        Thread.sleep(1000);*/

        // o ... while a criteria is met (exclusive):Flux#takeWhile
        /*Flux.range(1, 10)
                .takeWhile(i -> i<=5)
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .doOnDiscard(Integer.class, val -> System.out.println("Discarded "+val))
                .subscribe(val -> System.out.println("Received "+val));*/

        // . by taking at most 1 element:
        // o ... at a specific position: Flux#elementAt
        /*Flux.range(1, 10)
                .elementAt(9)
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .doOnDiscard(Integer.class, val -> System.out.println("Discarded "+val))
                .subscribe(val -> System.out.println("Received "+val));*/
        // o ... at the end: takeLast(1)
        /*Flux.range(1, 10)
                .takeLast(5)
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .doOnDiscard(Integer.class, val -> System.out.println("Discarded "+val))
                .subscribe(val -> System.out.println("Received "+val));*/
        // o ... and emit an error if empty: Flux#last
        /*Flux.empty()
                .last()
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .doOnDiscard(Integer.class, val -> System.out.println("Discarded "+val))
                .subscribe(val -> System.out.println("Received "+val), err -> System.out.println("Failed with error "+err));*/
        /*Flux.range(1, 10)
                .last()
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .doOnDiscard(Integer.class, val -> System.out.println("Discarded "+val))
                .subscribe(val -> System.out.println("Received "+val));*/
        // o ... and emit a default value if empty: Flux#last(T)
        /*Flux.empty()
                .last(-1)
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .doOnDiscard(Integer.class, val -> System.out.println("Discarded "+val))
                .subscribe(val -> System.out.println("Received "+val));*/

        // o ... by skipping elements:
        // o ... at the beginning of the sequence: Flux#skip(long)
        /*Flux.range(1, 10)
                .skip(3)
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .doOnDiscard(Integer.class, val -> System.out.println("Discarded "+val))
                .subscribe(val -> System.out.println("Received "+val));*/
        // o ... based on a duration
        /*Flux.range(1, 300)
                .skip(Duration.ofMillis(5))
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .doOnDiscard(Integer.class, val -> System.out.println("Discarded "+val))
                .subscribe(val -> System.out.println("Received "+val));*/
        // o ... at the end of the sequence: Flux#skipLast
        /*Flux.range(1, 10)
                .skipLast(3)
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .doOnDiscard(Integer.class, val -> System.out.println("Discarded "+val))
                .subscribe(val -> System.out.println("Received "+val));*/
        // o ... until a criteria is met(inclusive): Flux#skipUntil(predicate-based), Flux#skipUnitOther (companion publisher-based)
        /*Flux.range(1, 10)
                .skipUntil(i -> i > 7)
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .doOnDiscard(Integer.class, val -> System.out.println("Discarded "+val))
                .subscribe(val -> System.out.println("Received "+val));*/
        /*Flux.interval(Duration.ofMillis(200))
                .skipUntilOther(Flux.interval(Duration.ofMillis(500)))
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .doOnDiscard(Integer.class, val -> System.out.println("Discarded "+val))
                .subscribe(val -> System.out.println("Received "+val),
                err -> System.out.println("Failed with error "+err));
        Thread.sleep(1000);*/
        // o ... while a criteria is met (exclusive): Flux#skipWhile
        /*Flux.range(1, 10)
                .skipWhile(i -> i < 5)
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .doOnDiscard(Integer.class, val -> System.out.println("Discarded "+val))
                .subscribe(val -> System.out.println("Received "+val));*/

        // o ... by sampling items:
        // o ... by duration: Flux#sample(Duration)
        /*Flux.interval(Duration.ofMillis(200))
                .take(10)
                .sample(Duration.ofMillis(500))
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .doOnDiscard(Integer.class, val -> System.out.println("Discarded "+val))
                .subscribe(val -> System.out.println("Received "+val));
        Thread.sleep(5000);*/
        // o ... but keeping the first element in the sampling window instead of the last sampleFirst
        /*Flux.interval(Duration.ofMillis(200))
                .take(10)
                .sampleFirst(Duration.ofMillis(500))
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .doOnDiscard(Integer.class, val -> System.out.println("Discarded "+val))
                .subscribe(val -> System.out.println("Received "+val));
        Thread.sleep(5000);*/
        // o ... by a publisher-based window: Flux#sample(Publisher)
        /*var source = Flux.interval(Duration.ofMillis(200))
                .doOnNext(i -> System.out.println("Source emitted "+i));
        var sampler = Flux.interval(Duration.ofSeconds(1))
                .doOnNext(i -> System.out.println("----- Sample tick ---- "));

        source.sample(sampler)
                //.take(5)
                .subscribe(val -> System.out.println("Sampled value "+val));
        Thread.sleep(6000);*/
        // o ... based on publisher "timeout"# Flux#sampleTimeout (each element triggers a publisher, and is emitted if that publisher does not overlap with the next)
        /*var source = Flux.just("A", "B", "C", "D")
                .delayElements(Duration.ofMillis(300))
                .doOnNext(i -> System.out.println("Source emitted "+i));
        var sampler = Flux.interval(Duration.ofMillis(400))
                .doOnNext(i -> System.out.println("----- Sample tick ---- " +i));

        source.sampleTimeout(i -> sampler)
                //.take(5)
                .subscribe(val -> System.out.println("Sampled value "+val));
        Thread.sleep(2000);*/

        // o... I expect at most 1 element (error if more than one).
        // o ... and i want an error if the sequence is empty: FLux#single()

        /*Flux.empty()
                .single()
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .doOnDiscard(Integer.class, val -> System.out.println("Discarded "+val))
                .subscribe(val -> System.out.println("Received "+val),
                        err -> System.out.println("Failed with error "+err));*/

        /*Flux.just(1, 3)
                .single()
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .doOnDiscard(Integer.class, val -> System.out.println("Discarded "+val))
                .subscribe(val -> System.out.println("Received "+val),
                        err -> System.out.println("Failed with error "+err));*/

        /*Flux.just(1)
                .single()
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .doOnDiscard(Integer.class, val -> System.out.println("Discarded "+val))
                .subscribe(val -> System.out.println("Received "+val),
                        err -> System.out.println("Failed with error "+err));*/
        // o ... and i want a default value if the sequence is empty: Flux#single(T)
        /*Flux.empty()
                .single(1)
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .doOnDiscard(Integer.class, val -> System.out.println("Discarded "+val))
                .subscribe(val -> System.out.println("Received "+val),
                        err -> System.out.println("Failed with error "+err));*/
        // o ... and i accept an empty sequence as well: Flux#singleOrEmpty
        /*Flux.empty()
                .singleOrEmpty()
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .doOnDiscard(Integer.class, val -> System.out.println("Discarded "+val))
                .subscribe(val -> System.out.println("Received "+val),
                        err -> System.out.println("Failed with error "+err));*/

    }

    private static Mono<Boolean> isEvenAsync(Integer f) {
        return Mono.fromCallable(() -> f % 2 == 0);
    }

    private record Person(String firstName, String lastName, int age) {}
}
