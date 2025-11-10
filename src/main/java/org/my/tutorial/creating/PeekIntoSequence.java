package org.my.tutorial.creating;

import org.reactivestreams.Subscription;
import reactor.core.Disposable;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Signal;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.List;

public class PeekIntoSequence {

    public static void main(String[] args) throws InterruptedException {

        // . Without modifying the final sequence, i want to:
        // o get notified of / execute additional behavior (sometimes referred to as "side-effects") on:

        // o ... emission: doOnNext (Flux|Mono)
        /*Flux.just("A", "B", "C")
                .doOnNext(signal -> System.out.println("Emitted " +signal+ " on thread " +Thread.currentThread().getName()))
                .subscribe(System.out::println);*/

        // o ... completion: Flux#doOnComplete, Mono#doOnSuccess (include the result, if any)

        /*Mono.just("A")
                .doOnSuccess(signal -> System.out.println("Emitted " +signal+ " on thread " +Thread.currentThread().getName()))
                .subscribe(System.out::println);*/
        /*Flux.just("A", "B", "C")
                .doOnComplete(() -> System.out.println("Emitted complete signal on thread " + Thread.currentThread().getName()))
                .subscribe(System.out::println);*/

        // o ... error termination: doOnError (Flux|Mono)
        /*Flux.just("A", "B", "C")
                .map(val -> {
                    if(val.equals("B")) throw new RuntimeException("Boom!..");
                    return val;
                })
                .doOnError(error -> {
                    System.err.println("Received " +error);
                    throw new IllegalStateException(error.getMessage());
                })
                .subscribe(System.out::println, System.err::println, () -> System.out.println("Done"));*/

       // o ... cancellation: doOnCancel (Flux|Mono)

       /*var flux = Flux.interval(Duration.ofMillis(500))
           .doOnSubscribe(s -> System.out.println("Subscribed"))
           .doOnCancel(() -> System.out.println("Subscription was cancelled"))
           .doFinally(signal -> System.out.println("Signal "+signal));

        Disposable subscription = flux.subscribe(
                value -> System.out.println("Received "+value),
                err -> System.out.println("Failed with error "+err)
        );

        Thread.sleep(1000);
        subscription.dispose();

        Thread.sleep(200);*/

        // o ... start of the sequence: doFirst (Flux|Mono)
        /*Mono.fromCallable(() -> {
            try {
                System.out.println("Starting long running task ...");
                Thread.sleep(3000);
                return "Done";
            } catch (InterruptedException ignore) {
                throw new RuntimeException();
            }
        })
                .doFirst(() -> {
                    System.out.println("Setting up required environment");
                })
                .subscribe(System.out::println);*/
        // o ... this is tied to Publisher#subscribe(Subscriber)
        /*Flux.just(1, 2, 3)
                .doFirst(() -> System.out.println("Do First"))
                .doOnSubscribe(val -> System.out.println("Subscribed"))
                .doOnNext(val -> System.out.println("Received "+val))
                .doFinally(signal-> System.out.println("Completed " +signal))
                .subscribe(System.out::println);*/

        // o ... post-subscription: doOnSubscribe (Flux|Mono)
        /*Flux<Integer> flux = Flux.just(1, 3, 4)
                .doOnSubscribe(subscription -> System.out.println("Subscribed"));

        flux.subscribe(val -> System.out.println("Subscriber 1 received "+val));
        flux.subscribe(val -> System.out.println("Subscriber 2 received "+val));*/

        // o ... doOnRequest (Flux|Mono)
        /*Flux<Integer> flux = Flux.range(1, 10)
                .doOnRequest(val -> System.out.println("Request "+val))
                .doOnNext(val -> System.out.println("Emitting " +val));

        flux.subscribe(new BaseSubscriber<Integer>() {
                    @Override
                    protected void hookOnSubscribe(Subscription subscription) {
                        System.out.println("Subscribed");
                        request(2);
                    }

                    @Override
                    protected void hookOnNext(Integer value) {
                        System.out.println("Received "+value);
                        if (value==2) request(3);
                    }
                });*/

        // o ... completion or error: doOnTerminate (Flux|Mono)
        /*Flux.range(1, 5)
                .map(i -> {
                    if (i==3) throw new RuntimeException("Boom");
                    return i;
                })
                .doOnNext(val -> System.out.println("Emitting "+val))
                .doOnError(err -> System.out.println("Failed with error "+err))
                .doOnTerminate(() -> System.out.println("Cleaning up on termination"))
                .subscribe();*/

        // o ... but after it has been propagated downstream: doAfterTermination (Flux|Mono)

        /*Flux.range(1, 5)
                .map(i -> {
                    if (i==3) throw new RuntimeException("Boom");
                    return i;
                })
                .doOnNext(val -> System.out.println("Emitting "+val))
                .doOnError(err -> System.out.println("Failed with error "+err))
                .doOnTerminate(() -> System.out.println("Cleaning up on termination"))
                .doAfterTerminate(() -> System.out.println("Cleaning up After termination"))
                .subscribe();*/

        // o ... any type of signal, represented as Signal: doOnEach (Flux|Mono)
        /*Flux.range(1, 5)
                .map(i -> {
                    if (i==3) throw new RuntimeException("Boom");
                    return i;
                })
                .doOnEach(val -> System.out.println("Received " +val))
                .subscribe(System.out::println);*/
        //o ... any terminating condition (complete, error, cancel): doFinally (FLux|Mono)

        /*Flux<Integer> flux = Flux.range(1, 5)
                .doFinally(signal -> System.out.println("Last know signal "+signal));

        flux.subscribe(System.out::println);
        Thread.sleep(1000);

        flux.map(i -> {
            if (i==3) throw new RuntimeException("Boom..");
            return i;
        })
                .subscribe(System.out::println);*/

        // o ... log what happened internally: log (Flux|Mono)

        /*Flux.range(1, 5)
                .log("range")
                .subscribe(new BaseSubscriber<Integer>() {
                    @Override
                    protected void hookOnNext(Integer value) {
                        System.out.println("OnNext - Emitting " +value);
                    }

                    @Override
                    protected void hookOnSubscribe(Subscription subscription) {
                        System.out.println("Subscribed");
                        requestUnbounded();
                    }
                });*/

        /*Flux.interval(Duration.ofMillis(100))
                .log("interval")
                .subscribe(System.out::println);

        Thread.sleep(1000);*/

        // . I want to know of all events:
        // o ... each represented as Signal object:
        // o ... in a callback outside the sequence: doOnEach (Flux|Mono)

        /*Flux.just("A", "B", "C")
                .map(l -> {
                    if (l.equals("C")) throw new RuntimeException("Boom");
                    return l;
                })
                .doOnEach(signal -> System.out.println("Signal :: "+signal))
                .subscribe(System.out::println);*/

        // o ... instead of the original onNext emission: materialize (Flux|Mono)

        /*Flux<String> flux = Flux.create(sink -> {
            sink.next("A");
            sink.next("B");
            sink.next("C");
            sink.complete();
        });

        flux.map(l -> {
            if (l.equals("C")) throw new RuntimeException("Boom");
            return l;
        })
        .materialize()
        .subscribe(new BaseSubscriber<Signal<String>>() {
            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                System.out.println("Subscribed");
                requestUnbounded();
            }

            @Override
            protected void hookOnNext(Signal<String> value) {
                System.out.println("Received " +value);
            }

            @Override
            protected void hookOnComplete() {
                System.out.println("Completed");
            }

            @Override
            protected void hookOnError(Throwable throwable) {
                System.out.println("Failed with error " +throwable);
            }
        });*/

        // o ... any get back to the onNexts: dematerialize (FLux|Mono)
        /*Flux.just(1, 5)
                .materialize()
                .doOnNext(signal -> System.out.println("OnNext Signal "+signal))
                .dematerialize()
                .doOnNext(val -> System.out.println("onNext Received "+val))
                .subscribe(System.out::println);*/
    }
}
