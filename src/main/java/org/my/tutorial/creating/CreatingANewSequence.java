package org.my.tutorial.creating;

import org.w3c.dom.ls.LSOutput;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class CreatingANewSequence {

    // 1. Creating a New Sequence...

    public static void main(String[] args) throws InterruptedException {

    // . that emits a T, and i already have: just (Flux|Mono)

        /*Mono.just("Apple")
                .subscribe(System.out::println);*/
        /*Flux.just("Apple", "Banana", "Grapes", "Mango")
                .subscribe(System.out::println);*/

    // o ..from an Optional<T>:Mono#justOrEmpty(Optional<T>)

        /*Mono.justOrEmpty(Optional.empty()).subscribe(System.out::println);
        Mono.justOrEmpty(Optional.of("Apple")).subscribe(System.out::println);*/

    // o ..from a potentially null T:Mono.justOrEmpty(T)

        /*Mono.justOrEmpty("Apple").subscribe(System.out::println);
        Mono.justOrEmpty(null).subscribe(System.out::println);*/

    // . that emits a T returned by a method: just (Mono|Flux)

        /*Mono.just(oneChar())
                        .subscribe(System.out::println);*/
        /*var flux = Flux.just(threeChars(0), threeChars(1), threeChars(2));

        flux.subscribe(val -> System.out.println("Subscriber 1 Received "+val));
        flux.subscribe(val -> System.out.println("Subscriber 2 Received "+val));*/

    // o ... but lazily captured: use Mono#fromSupplier or wrap just (Flux|Mono) inside defer (Flux|Mono)

        //Mono.fromSupplier(CreatingANewSequence::oneChar).subscribe(System.out::println);
        //Mono.defer(() -> Mono.just(oneChar())).subscribe(System.out::println);

        /*var flux = Flux.defer(() -> Flux.just(threeChars(0), threeChars(1), threeChars(2)));
        System.out.println("Flux created");

        flux.subscribe(val -> System.out.println("Subscriber 1 Received "+val));
        flux.subscribe(val -> System.out.println("Subscriber 2 Received "+val));*/

    // . that emits several T I can explicitly enumerate Flux#just(T...)

        /*Flux.just(1, 2, 3, 4, 5)
                .subscribe(System.out::println);*/

    // . that iterates over
    // o ... any array Flux#fromArray
        /*String[] fruits = {"Apple", "Banana", "Grapes"};
        Flux.fromArray(fruits)
                .subscribe(System.out::println);*/

    // o ... a collection or Iterable Flux#fromIterable
        /*List<String> fruits = new ArrayList<>();
        fruits.add("Apple");
        fruits.add("Banana");
        fruits.add("Grapes");
        Flux.fromIterable(fruits)
                .subscribe(System.out::println);*/

    // o ... a range of integers Flux#range
        /*Flux.range(1, 5).subscribe(System.out::println);*/

    // o ... a Stream supplied for each Subscription: Flux#fromStream(Supplier<Stream>)
        /*Flux.fromStream(CreatingANewSequence::threeChars).subscribe(System.out::println);*/

    // . that emits from various single-values sources such as
    // o ... a Supplier<T>: Mono#fromSupplier
        /*Mono.fromSupplier(CreatingANewSequence::oneChar).subscribe(System.out::println);*/
        /*Mono.fromRunnable(() -> {
            System.out.println("Thread :: "+Thread.currentThread().getName());
        })
                .subscribe(System.out::println);*/

        /*Mono.fromCallable(() -> {
            System.out.println("Thread :: " +Thread.currentThread().getName());
            return oneChar();
        }).subscribe(System.out::println);*/

    // . that completes empty
        /*Mono.empty().subscribe(System.out::println);
        Flux.empty().subscribe(System.out::println);*/

    // . that errors immediately
        /*Mono.error(new RuntimeException("Boom!..")).subscribe(System.out::println, System.err::println);*/

    // o ... but lazily build the Throwable: error(Supplier<Throwable>) (Flux|Mono)

        /*var mono = Mono.error(() -> new RuntimeException("Boom!.."));

        mono.subscribe(System.out::println, System.err::println);
        mono.subscribe(System.out::println, System.err::println);*/

    // . that never does anything: never Flux|Mono
        /*Flux.never().subscribe(System.out::println);*/

        /*var flux = Flux.defer(() -> Flux.fromStream(threeChars()));

        flux.subscribe(val -> System.out.println("Subscriber 1 Received "+val));
        flux.subscribe(val -> System.out.println("Subscriber 2 Received "+val));*/

    // . that depends on a disposable resource: using (Flux|Mono)

        /*Flux.using(
                () -> {
                    System.out.println("Opening Resource");
                    return new StringBuilder("Resource");
                },
                resource -> {
                    System.out.println("Using Resource :: " +resource);
                    return Flux.just("A", "B", "C");
                },
                resource -> System.out.println("Cleaning up Resource")
        ).subscribe(
                System.out::println,
                System.err::println,
                () -> System.out.println("Done")
        );*/

    // . that generates events programmatically (can use state):
    // o ... synchronous and one by one
        /*Flux.generate(() -> 1, (state, sink) -> {
                    System.out.print("Current Thread :: " +Thread.currentThread().getName() +" <- ");
            sink.next(state);
            if (state==5) {
                sink.complete();
            }
            return state + 1;
        },
                        state -> System.out.println("Current Thread :: " +Thread.currentThread().getName() +" <- Final State :: " +state))
                .subscribe(System.out::println);*/

    // o ... asynchronous (can also be sync), multiple emissions possible in one pass : Flux#create (Mono#create without multiple emission aspect)

        /*Flux<String> flux = Flux.create(sink -> {
           new Thread(() -> {
               for (int i=1; i<=5; i++) {
                   sink.next("Event " +i+ " is running on thread "+Thread.currentThread().getName());
                   try {
                       Thread.sleep(500);
                   } catch (InterruptedException e) {
                       throw new RuntimeException(e);
                   }
               }
               sink.complete();
           }).start();
        });
        flux.subscribe(System.out::println);*/

    }

    static String oneChar() {
        return "Z";
    }

    static String threeChars(int index) {
        System.out.println("threeChars Called");
        var list = List.of("A", "B", "C");
        return list.get(index);
    }

    static Stream<String> threeChars() {
        System.out.println("threeChars Called");
        return Stream.of("A", "B", "C");
    }


}
