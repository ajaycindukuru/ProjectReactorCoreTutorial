package org.my.tutorial.creating;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

public class WorkingWithTime {

    public static void main(String[] args) throws InterruptedException {
        // . I want to associate emissions with a timing measured...
        // o ... with best available precision and versatility of provided data: timed (Flux|Mono)
        /*Flux.just("A", "B", "C")
                .delayElements(Duration.ofSeconds(1))
                .timed()
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .doOnDiscard(Integer.class, val -> System.out.println("Discarded "+val))
                .subscribe(val -> System.out.println("Received value :: "+val.get()+ " Timestamp :: " +val.timestamp() + " elapsed time " +val.elapsed()),
                        err -> System.out.println("Failed with error "+err));
        Thread.sleep(3300);*/
        // o ... Timed<T>#elapsed for Duration since last onNext
        /*Flux.just("A", "B", "C")
                .delayElements(Duration.ofSeconds(1))
                .elapsed()
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .doOnDiscard(Integer.class, val -> System.out.println("Discarded "+val))
                .subscribe(val -> System.out.println("Received value :: "+val.getT2()+ " elapsed time :: " +val.getT1() + "ms"),
                        err -> System.out.println("Failed with error "+err));
        Thread.sleep(3300);*/
        // o ... Timed<T>#timestamp() for Instant representation of epoch timestamp (milliseconds resolution)
        /*Flux.just("A", "B", "C")
                .delayElements(Duration.ofSeconds(1))
                .timestamp()
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .doOnDiscard(Integer.class, val -> System.out.println("Discarded "+val))
                .subscribe(val -> System.out.println("Received value :: "+val.getT2()+ " time taken in epoch since last onNext :: " +val.getT1()),
                        err -> System.out.println("Failed with error "+err));
        Thread.sleep(3300);*/
        // o ... Timestamp<T>#elapsedSinceSubscription() for Duration since subscription (rather than last onNext)
        /*Flux.just("A", "B", "C")
                .delayElements(Duration.ofSeconds(1))
                .timed()
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .doOnDiscard(Integer.class, val -> System.out.println("Discarded "+val))
                .subscribe(val -> System.out.println("Received value :: "+val+ " elapsed time since subscription :: " +val.elapsedSinceSubscription() + "ms"),
                        err -> System.out.println("Failed with error "+err));
        Thread.sleep(3300);*/

        // . I want my sequence to be interrupted if there is too much delay between emissions: timeout (Flux|Mono)
        /*Flux.range(1, 10)
                .delayElements(Duration.ofMillis(400))
                .timeout(Duration.ofMillis(300), Flux.range(11, 10))
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .doOnDiscard(Integer.class, val -> System.out.println("Discarded "+val))
                .subscribe(val -> System.out.println("Received value :: "+val),
                        err -> System.out.println("Failed with error "+err));
        Thread.sleep(3300);*/

        // . I want to get ticks from a clock, regular time intervals: Flux#interval
        /*Flux.interval(Duration.ofSeconds(1))
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .subscribe(val -> System.out.println("Received value :: "+val));
        Thread.sleep(3300);*/
        // . I want to emit a single 0 after a initial delay: static Mono.delay
        /*Mono.delay(Duration.ofSeconds(1))
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .subscribe(val -> System.out.println("Received value :: "+val));
        Thread.sleep(3300);*/
        // . I want to introduce a delay
        // o ... between each onNext signal: Mono#delayElement, Flux#delayElements
        /*Flux.range(1, 10)
                .delayElements(Duration.ofMillis(200))
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .subscribe(val -> System.out.println("Received value :: "+val));
        Thread.sleep(2100);*/
        // o ... before te subscription happens: Flux#delaySubscription
        /*Flux.range(1, 10)
                .delaySubscription(Duration.ofMillis(2000))
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .subscribe(val -> System.out.println("Received value :: "+val));
        Thread.sleep(2100);*/
    }
}
