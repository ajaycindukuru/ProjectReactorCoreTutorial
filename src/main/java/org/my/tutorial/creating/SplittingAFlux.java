package org.my.tutorial.creating;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class SplittingAFlux {

    public static void main(String[] args) throws InterruptedException {

        // . I want to split a Flux<T> into a Flux<Flux<T>>, by a boundary criteria
        // o... of size: window(int)
        /*Flux.range(1, 10)
                .window(3)
                .flatMap(Flux::collectList)
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .subscribe(val -> System.out.println("Received "+val),
                        err -> System.out.println("Failed with error "+err));*/

        // o ... with overlapping or dropping window: window(int, int)
        /*Flux.range(1, 10)
                .window(3, 1)
                .flatMap(Flux::collectList)
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .subscribe(val -> System.out.println("Received "+val),
                        err -> System.out.println("Failed with error "+err));*/
        // o ... of time window(Duration)
        /*Flux.range(1, 10)
                .window(Duration.ofMillis(4))
                .flatMap(Flux::collectList)
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .subscribe(val -> System.out.println("Received "+val),
                        err -> System.out.println("Failed with error "+err));*/

        // o .. with overlapping or dropping window: window(Duration, Duration)
        /*Flux.interval(Duration.ofMillis(300))
                .take(10)
                .window(Duration.ofSeconds(1), Duration.ofMillis(800))
                .flatMap(Flux::collectList)
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .subscribe(val -> System.out.println("Received "+val),
                        err -> System.out.println("Failed with error "+err));
        Thread.sleep(4000);*/

        // o ... of size or time (window closes when count is reached or timeout elapsed): windowTimeout(int, Duration)
        /*Flux.interval(Duration.ofMillis(30))
                .windowTimeout(20, Duration.ofMillis(1000))
                .flatMap(Flux::collectList)
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .doOnDiscard(Integer.class, val -> System.out.println("Dropped "+val))
                .subscribe(val -> System.out.println("Received "+val),
                        err -> System.out.println("Failed with error "+err));
        Thread.sleep(4000);*/

        // o ... based on predicate on element: windowUntil
        /*Flux.interval(Duration.ofMillis(20))
                .windowUntil(i -> i == 150)
                .flatMap(Flux::collectList)
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .doOnDiscard(Integer.class, val -> System.out.println("Dropped "+val))
                .subscribe(val -> System.out.println("Received "+val),
                        err -> System.out.println("Failed with error "+err));
        Thread.sleep(3100);*/

        // o ... emitting the value that triggered the boundary in next window (cutBefore variant): windowUntil(Predicate, true)
        /*Flux.interval(Duration.ofMillis(20))
                .windowUntil(i -> i % 10 == 0, true)
                .flatMap(Flux::collectList)
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .doOnDiscard(Integer.class, val -> System.out.println("Dropped "+val))
                .subscribe(val -> System.out.println("Received "+val),
                        err -> System.out.println("Failed with error "+err));
        Thread.sleep(3100);*/
        // o ... keeping the window open while elements match a predicate: windowWhile (non-matching elements are not emitted)
        /*Flux.interval(Duration.ofMillis(20))
                .windowWhile(i -> i % 7 != 0)
                .flatMap(Flux::collectList)
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .doOnDiscard(Integer.class, val -> System.out.println("Dropped "+val))
                .subscribe(val -> System.out.println("Received "+val),
                        err -> System.out.println("Failed with error "+err));
        Thread.sleep(3000);*/
        // o ... driven by an arbitrary boundary represented by onNexts in a control Publisher: window(Publisher), windowWhen
        /*Flux.interval(Duration.ofMillis(200))
                .window(Flux.interval(Duration.ofSeconds(1)))
                .flatMap(Flux::collectList)
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .doOnDiscard(Integer.class, val -> System.out.println("Dropped "+val))
                .subscribe(val -> System.out.println("Received "+val),
                        err -> System.out.println("Failed with error "+err));
        Thread.sleep(3000);*/

        /*Flux.interval(Duration.ofMillis(200))
                .take(20)
                .windowWhen(Flux.interval(Duration.ofSeconds(1)), i -> Flux.interval(Duration.ofMillis(500)))
                .flatMap(Flux::collectList)
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .doOnDiscard(Integer.class, val -> System.out.println("Dropped "+val))
                .subscribe(val -> System.out.println("Received "+val),
                        err -> System.out.println("Failed with error "+err));
        Thread.sleep(3000);*/

        // o ... I want to split a Flux<T> and buffer elements within boundaries together...
        // into List
        // by a size boundary: buffer(int)
        /*Flux.range(1, 20)
                .buffer(10)
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .doOnDiscard(Integer.class, val -> System.out.println("Dropped "+val))
                .subscribe(val -> System.out.println("Received "+val),
                        err -> System.out.println("Failed with error "+err));*/

        // o ... with overlapping or dropping buffer: buffer(int, int)
        /*Flux.range(1, 25)
                .buffer(5, 3)
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .doOnDiscard(Integer.class, val -> System.out.println("Dropped "+val))
                .subscribe(val -> System.out.println("Received "+val),
                        err -> System.out.println("Failed with error "+err));*/
        // o ... by a duration boundary: buffer(Duration)
        /*Flux.range(1, 25)
                .delayElements(Duration.ofMillis(20))
                .buffer(Duration.ofMillis(100))
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .doOnDiscard(Integer.class, val -> System.out.println("Dropped "+val))
                .subscribe(val -> System.out.println("Received "+val),
                        err -> System.out.println("Failed with error "+err));
        Thread.sleep(3000);*/
        // o ... with overlapping or dropping buffer: buffer(Duration, Duration)
        /*Flux.range(1, 25)
                .delayElements(Duration.ofMillis(20))
                .buffer(Duration.ofMillis(100), Duration.ofMillis(200))
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .doOnDiscard(Integer.class, val -> System.out.println("Dropped "+val))
                .subscribe(val -> System.out.println("Received "+val),
                        err -> System.out.println("Failed with error "+err));
        Thread.sleep(3000);*/
        // o ... by size or duration boundary: bufferTimeout(int, duration)
        /*Flux.range(1, 25)
                .delayElements(Duration.ofMillis(20))
                .bufferTimeout(4, Duration.ofMillis(100))
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .doOnDiscard(Integer.class, val -> System.out.println("Dropped "+val))
                .subscribe(val -> System.out.println("Received "+val),
                        err -> System.out.println("Failed with error "+err));
        Thread.sleep(3000);*/

        // o ... by an arbitrary boundary criteria: bufferUntil(Predicate)
        /*Flux.range(1, 25)
                .bufferUntil(i -> i<10)
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .doOnDiscard(Integer.class, val -> System.out.println("Dropped "+val))
                .subscribe(val -> System.out.println("Received "+val),
                        err -> System.out.println("Failed with error "+err));
        Thread.sleep(3000);*/
        // o ... putting the element that triggered the boundary in the next buffer: bufferUntil(Predicate, true)
        /*Flux.range(1, 25)
                .bufferUntil(i -> i<10, true)
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .doOnDiscard(Integer.class, val -> System.out.println("Dropped "+val))
                .subscribe(val -> System.out.println("Received "+val),
                        err -> System.out.println("Failed with error "+err));
        Thread.sleep(3000);*/
        // o ... buffering while predicate matches and dropping the elements that triggered boundary: .bufferWhile(Predicate)
        /*Flux.range(1, 25)
                .bufferWhile(i -> i%4!=0)
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .doOnDiscard(Integer.class, val -> System.out.println("Dropped "+val))
                .subscribe(val -> System.out.println("Received "+val),
                        err -> System.out.println("Failed with error "+err));
        Thread.sleep(3000);*/
        // o ... driven by an arbitrary boundary represented by onNexts in a control Publisher: buffer(Publisher), bufferWhen
        /*Flux.interval(Duration.ofMillis(200))
                .buffer(Flux.interval(Duration.ofMillis(700)))
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .doOnDiscard(Integer.class, val -> System.out.println("Dropped "+val))
                .subscribe(val -> System.out.println("Received "+val),
                        err -> System.out.println("Failed with error "+err));
        Thread.sleep(3000);*/

        /*Flux.interval(Duration.ofMillis(200))
                .bufferWhen(Flux.interval(Duration.ofMillis(700)), i -> Flux.interval(Duration.ofSeconds(1)))
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .doOnDiscard(Integer.class, val -> System.out.println("Dropped "+val))
                .subscribe(val -> System.out.println("Received "+val),
                        err -> System.out.println("Failed with error "+err));
        Thread.sleep(3000);*/
    }
}
