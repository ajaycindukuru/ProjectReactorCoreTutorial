package org.my.tutorial.creating;

import reactor.core.publisher.Flux;

import java.time.Duration;

public class MulticastingAFluxToSeveralSubscribers {

    public static void main(String[] args) throws InterruptedException {
        // . I want to connect multiple Subscribers to a FLux
        // o ... and decide when to trigger the source with connect(): publish() (returns a ConnectableFlux)

        /*var flux = Flux.just(1, 2, 3)
                .delayElements(Duration.ofMillis(10 00))
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .publish();

        flux.subscribe(val -> System.out.println("Subscriber 1 Received "+val));
        Thread.sleep(800);
        flux.subscribe(val -> System.out.println("Subscriber 2 Received "+val));
        System.out.println("Connecting ...");
        flux.connect();
        Thread.sleep(5000);*/

        // o ... and trigger the source immediately (late subscribers see later data): share() Flux|Mono
        /*var flux = Flux.just(1, 2, 3)
                .delayElements(Duration.ofMillis(500))
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .share();

        flux.subscribe(val -> System.out.println("Subscriber 1 Received "+val));
        Thread.sleep(1100);
        flux.subscribe(val -> System.out.println("Subscriber 2 Received "+val));

        Thread.sleep(5000);*/

        // o ... and permanently connect the source when enough subscribers go above/below the threshold: .publish().autoConnect(n)
        /*var flux = Flux.range(1, 10)
                .delayElements(Duration.ofMillis(300))
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .publish()
                .autoConnect(2);

        System.out.println("Subscriber 1");
        flux.subscribe(val -> System.out.println("Subscriber 1 Received "+val));
        Thread.sleep(1000);
        System.out.println("Subscriber 2");
        flux.subscribe(val -> System.out.println("Subscriber 2 Received "+val));
        Thread.sleep(3000);*/

        // o ... and automatically connect and cancel the source when subscribers go above/below the threshold: publish.refCount(n)

        /*var flux = Flux.range(1, 10)
                .delayElements(Duration.ofMillis(300))
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .publish()
                .refCount(2);

        System.out.println("Subscriber 1");
        flux.subscribe(val -> System.out.println("Subscriber 1 Received "+val));
        Thread.sleep(2000);
        System.out.println("Subscriber 2");
        flux.subscribe(val -> System.out.println("Subscriber 2 Received "+val));
        Thread.sleep(3000);*/

        // o ... by giving a chance for new subscribers to come in before cancelling: publish.refCount(n, Duration)
        /*var flux = Flux.interval(Duration.ofMillis(300))
                .doOnEach(signal -> System.out.println("Signal "+signal))
                .doOnSubscribe(val -> System.out.println("Subscribed "+val))
                .doOnCancel(() -> System.out.println("Cancelled"))
                .publish()
                .refCount(2, Duration.ofMillis(500));

        System.out.println("Subscriber 1");
        var sub1 = flux.subscribe(val -> System.out.println("Subscriber 1 Received "+val));
        Thread.sleep(2000);
        System.out.println("Subscriber 2");
        var sub2 = flux.subscribe(val -> System.out.println("Subscriber 2 Received "+val));
        Thread.sleep(600);
        sub1.dispose();
        sub2.dispose();
        Thread.sleep(3000);*/

        // . I want to cache data from a Publisher and replay it to later subscribers:
        // o ... up to n elements: cache(int)
        /*var flux = Flux.range(1, 10)
                .doOnSubscribe(val -> System.out.println("Subscribed to source"))
                .cache(3);

        flux.subscribe(val -> System.out.println("Sub1 Received "+val));

        Thread.sleep(2000);
        flux.subscribe(val -> System.out.println("Sub2 Received "+val));*/

        // o ... caching latest elements seen within a Duration (Time-To-Live): cache(Duration) (Flux|mono)
        /*var flux = Flux.range(1, 10)
                .delayElements(Duration.ofMillis(200))
                .doOnSubscribe(val -> System.out.println("Subscribed to source"))
                .cache(Duration.ofMillis(1000));

        flux.subscribe(val -> System.out.println("Sub1 Received "+val));

        Thread.sleep(2000);
        flux.subscribe(val -> System.out.println("Sub2 Received "+val));*/

        // o ... but retain no more than n elements
        /*var flux = Flux.range(1, 10)
                .delayElements(Duration.ofMillis(200))
                .doOnSubscribe(val -> System.out.println("Subscribed to source"))
                .cache(2, Duration.ofMillis(1000));

        flux.subscribe(val -> System.out.println("Sub1 Received "+val));

        Thread.sleep(2000);
        flux.subscribe(val -> System.out.println("Sub2 Received "+val));*/

        // o ... but without immediately triggering the source: Flux#replay (returns a ConnectableFlux)

        /*var flux = Flux.range(1, 10)
                //.delayElements(Duration.ofMillis(200))
                .replay(4);


        flux.subscribe(val -> System.out.println("Sub 1 received "+val));
        flux.connect();
        Thread.sleep(3000);
        flux.subscribe(val -> System.out.println("Subs 2 received "+val));*/

    }
}
