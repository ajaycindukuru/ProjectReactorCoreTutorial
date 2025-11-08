package org.my.tutorial.creating;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.sql.SQLOutput;
import java.time.Duration;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TransformingAnExistingSequence {

    public static void main(String[] args) throws InterruptedException {

    // . I want to transform existing data:
    // o ... on a 1-1 basis (e.g. String to their length) map: (Flux|Mono)
        /*Flux.just("Apple", "Banana", "Grapes", "Mango")
                .map(fruit -> fruit + "(" +fruit.length() + ")")
                .subscribe(System.out::println);*/
    // o ... by just casting it: cast (Flux|Mono)
        /*Flux.just("1", "2", "3")
                .cast(String.class)
                .subscribe(System.out::println);*/
    // o ... in order to materialize each source value's index: Flux#index
        /*Flux.just("Apple", "Banana", "Grapes")
                .index()
                .subscribe(System.out::println);*/

    // o ... on a 1 to n basis (e.g, string to their characters): flatMap (Flux|Mono) + use a factory method
        /*Flux.just("Apple", "Banana", "Grapes")
                .flatMap(s -> Flux.fromStream(s.chars().mapToObj(c -> (char) c)))
                .subscribe(System.out::println);*/

    // o ... on a 1 to n basis with programmatic behavior for each source element and/or state: handle (Flux|

        /*Flux.just("Apple", "Banana", "Grapes", "Mangoes", "Oranges")
                .handle((fruit, sink) -> {
                    if (fruit.contains("p")) {
                        sink.next(fruit.toUpperCase());
                    }
                })
                .subscribe(System.out::println, System.err::println, () -> System.out.println("Done"));*/

    // o ... running an asynchronous task for each source item(e.g urls to http request): flatMap (Flux|Mono) + an async publisher returning method.

        /*Flux.just("A", "B", "C")
                .flatMap(TransformingAnExistingSequence::asyncTask)
                .subscribe(System.out::println);

        Thread.sleep(1000);*/

    // . ignoring some data: conditionally return a Mono.empty() in the flatMap lambda
        /*Flux.range(1, 10)
                .flatMap(TransformingAnExistingSequence::asyncTask)
                .subscribe(val -> System.out.println("Received - "+val));

        Thread.sleep(1000);*/

    // . retaining the original sequence order: Flux#flatMapSequential (this triggers the async process immediately but reorders the results)
        /*Flux.range(1, 10)
                .flatMapSequential(TransformingAnExistingSequence::asyncTask)
                .subscribe(val -> System.out.println("Received - "+val));

        Thread.sleep(1000);*/

    // . where the async task can return multiple values, from a Mono source: Mono#flatMapMany
            /*Mono.just("User1")
                    .flatMapMany(TransformingAnExistingSequence::asyncTaskOne)
                    .subscribe(val -> System.out.println("Received - "+val));

            Thread.sleep(1000);*/

    // . I want to add pre-set elements to an existing sequence
    // o ... at the start: Flux#startWith(T...)
        /*Flux.just("Mangoes", "Pineapple", "Melon")
                .startWith("Apple", "Banana", "Grapes")
                .map(String::toUpperCase)
                .subscribe(System.out::println);*/

    // o ... at the end: Flux#concatWithValues(T..)
        /*Flux.just("Mangoes", "Pineapple", "Melon")
                .concatWithValues("Apple", "Banana", "Grapes")
                .map(String::toUpperCase)
                .subscribe(System.out::println);*/

    // . I want to aggregate a Flux: (the Flux# is assumed below)
    // o ... into a List collectList, collectSortedList

        /*Flux.just("Veggie Pizza", "Peperoni Pizza", "Hawaiian Pizza")
                .collectList()
                .subscribe(System.out::println);*/

        /*Flux.just(3, 2, 5, 8, 6)
                .collectSortedList()
                .subscribe(System.out::println);*/

        /*Flux.just("Apple", "Grapes", "Melon", "Pineapple", "Dragon Fruit")
                .collectSortedList(Comparator.comparing(String::length).reversed())
                .subscribe(System.out::println);*/

    // o ... into a Map: collectMap, collectMultiMap
        /*Flux.just("Apple", "Banana", "Grapes", "Mango", "Melon")
                .collectMap(String::toUpperCase, String::length)
                .subscribe(System.out::println);
        Thread.sleep(1000);*/

        /*Flux.just("Apple", "Banana", "Grapes", "Mango", "Melon")
                .collectMultimap(String::length, String::toLowerCase)
                .subscribe(System.out::println);*/

    // o .. into arbitrary container: collect
        /*Flux.just("A", "B", "C")
                .collect(() -> new LinkedHashMap<Integer, String>(), (map, val) -> map.put(map.size() + 1, val))
                .subscribe(System.out::println);*/

    // o ... into a size of sequence: count
        /*Flux.range(1, 10)
                .count()
                .subscribe(val -> System.out.println("Received size "+val));*/

    // o ... by applying a function between each element (e.g. running sum): reduce
        /*Flux.range(1, 20)
                .reduce(Integer::sum)
                .subscribe(System.out::println);*/

        /*Flux.range(1, 5)
                .reduce(1, (i, product) -> Math.multiplyExact(product, i))
                .subscribe(System.out::println);*/
    // o ... but emitting each intermediate value: scan
        /*Flux.range(1, 5)
                .scan(1, (i, product) -> Math.multiplyExact(product, i))
                .subscribe(System.out::println);*/

    // o ... into boolean value from a predicate:
    // o ... applied to all values (AND): all
        /*Flux.just(2, 4, 6, 8, 10)
                .all(f -> f%2==0)
                .subscribe(System.out::println);*/
    // o ... applied to at least one value (OR): any
        /*Flux.just(1, 3, 4, 5, 7, 9)
                .any(f -> f%2==0)
                .subscribe(System.out::println);*/
    // o ... testing the presence of any value: hasElements(there is Mono equivalent in hasElements)
        /*Flux.range(1, 5)
                .hasElements()
                .subscribe(System.out::println);*/
        /*Flux.empty().hasElements().subscribe(System.out::println);*/

    // o ... testing the presence of specific value: hasElement(T)
        /*Flux.just("Apple", "Banana", "Grapes")
                .hasElement("Banana")
                .subscribe(System.out::println);*/

    // . I want to combine publishers...
    // o ... in sequential order: Flux#concat or .concatWith(other) (Flux|Mono)

        /*Mono.just("Apple")
                .concatWith(Mono.just("Banana"))
                .subscribe(System.out::println);*/
        /*Flux.just("Apple", "Banana")
                .concatWith(Mono.just("Grapes"))
                .subscribe(System.out::println);*/
        /*Flux.concat(Mono.just("Apple"))
                .subscribe(System.out::println);*/

    // o ... but delaying any error until remaining publishers have been emitted: Flux#concatDelayError
        /*Flux.concatDelayError(Flux.just(1, "A", "X", "Z", 99), Flux.empty(), Flux.error(new RuntimeException()), Flux.range(10, 5))
                .subscribe(System.out::println);*/
    // o... but eagerly subscribing to subsequent publishers: Flux#mergeSequential
        /*var flux1 = Flux.range(1, 10)
                .map(f -> f * 10);

        flux1.subscribe(val -> System.out.println("Subscriber 1 Received " +val));
        var flux2 = Flux.mergeSequential(flux1, Flux.range(10, 10))
                .map(f -> f * 5);
        flux2.subscribe(val -> System.out.println("Subscriber 2 Received "+val));*/

    // o ... in emission order (combined items emitted as they come): Flux#merge / .mergeWith(other) (Flux|Mono)
        /*var flux1 = Flux.merge(Flux.just("A", "B", "C"), Flux.just("X", "Y", "Z"))
                .mergeWith(Flux.just("P", "Q", "R"))
                .map(String::toLowerCase);

        flux1.subscribe(val -> System.out.println("Subscriber 1 Received "+val));*/

        /*var flux2 = Flux.merge(Flux.just("A"), Mono.just(1));
        flux2.subscribe(val -> System.out.println("Subscriber 2 Received "+val));*/

    // o ... with different types (transforming merge): Flux#zip / Flux#zipWith

        /*Flux.zip(Flux.just("A", "B", 1), Flux.just("a", "b", "c"))
                .zipWith(Flux.just("Apple", "Banana", "Grapes", "Melon"))
                .subscribe(System.out::println);*/

        /*Flux.range(1, 5)
                .zipWith(Flux.just("Apple", "Banana", "Grapes", "Mango", "Melon"))
                .subscribe(System.out::println);*/

    // o ... by pairing values:
    // o ... from two Monos into a Tuple2: Mono#zipWith
        /*Mono.just("Apples")
                .zipWith(Mono.just(1))
                        .subscribe(System.out::println);*/

       /*Mono.zip(Mono.just(1), Mono.just("A"), Mono.just("Apple"), Mono.just("All Done"))
                .subscribe(System.out::println);*/

    // o ... by coordinating their termination:
    // o ... from 1 Mono and any sources into Mono<Void>: Mono#and
        /*var mono1 = Mono.fromCallable(() -> {
            System.out.println("Running task 1 on thread " +Thread.currentThread().getName());
            return "Task 1";
        });

        var mono2 = Mono.fromCallable(() -> {
            System.out.println("Running task 2 on thread " +Thread.currentThread().getName());
            return "Task 2";
        });

        var combined = mono1.and(mono2);

        combined.subscribe(null, System.err::println, () -> System.out.println("All tasks done!.."));*/

    // o ... from n sources when they all complete: Mono#when

       /* Mono.when(
                Mono.fromRunnable(() -> System.out.println("Task 1 started on thread " +Thread.currentThread().getName())),
                Mono.fromRunnable(() -> System.out.println("Task 2 started on thread " +Thread.currentThread().getName())),
                Mono.fromRunnable(() -> System.out.println("Task 3 started on thread " +Thread.currentThread().getName()))
        )
                .subscribe(null,
                        System.err::println,
                        () -> System.out.println("All tasks completed!.."));*/

    // o ... into an arbitrary container type:
    // o ... each time all sides have emitted: Flux#zip (up to smallest cardinality)

        /*Flux.zip(Flux.just("Apples", "Bananas", "Grapes"), Flux.just("6.50", "2.25", "6.99"),
                (fruits, price) -> fruits + " for each pound costs $" +price)
                .subscribe(System.out::println);*/

        /*var names = Flux.just("Ajay", "Vijay", "Sanjay");
        var subjects = Flux.just("Math", "Ela", "Science");
        var scores = Flux.just(100, 5, 65);

        Flux.zip(names, subjects, scores)
                .map(tuple -> tuple.getT1() + " scored " + tuple.getT3() + " in " +tuple.getT2())
                .subscribe(System.out::println);*/

    // o ... each time a new value arrives at either side: Flux#combineLatest
        /*var letters = Flux.just("A", "B", "C").delayElements(Duration.ofMillis(300));
        var numbers = Flux.just(1, 2, 3).delayElements(Duration.ofMillis(200));

        Flux.combineLatest(letters, numbers, (letter, number) -> letter+number)
                .subscribe(System.out::println);
        Thread.sleep(1500);*/

    // o ... select the first publisher which ...
    // o ... produces a value (onNext): firstWithValue (Flux|Mono)

        /*var flux1 = Flux.just("Apple").delayElements(Duration.ofMillis(200));
        var flux2 = Flux.just("Bananas").delayElements(Duration.ofMillis(200));
        var flux3 = Flux.just("Grapes").delayElements(Duration.ofMillis(250));
        var flux4 = Flux.empty().delayElements(Duration.ofMillis(100));
        var flux5 = Flux.error(new RuntimeException("Boom!..")).delayElements(Duration.ofMillis(100));

        Flux.firstWithValue(flux1, flux2, flux3, flux4, flux5)
                .subscribe(System.out::println);

        Thread.sleep(500);*/

    // o ... produce any signal: firstWithSignal (Flux|Mono)

        /*var flux1 = Flux.just("Apple").delayElements(Duration.ofMillis(200));
        var flux2 = Flux.just("Bananas").delayElements(Duration.ofMillis(200));
        var flux3 = Flux.just("Grapes").delayElements(Duration.ofMillis(250));
        var flux4 = Flux.empty().delaySubscription(Duration.ofMillis(300)).switchIfEmpty(Mono.just("Empty signaled"));
        var flux5 = Flux.error(new RuntimeException("Boom!..")).delaySubscription(Duration.ofMillis(200));

        Flux.firstWithSignal(flux1, flux2, flux3, flux4, flux5)
                .subscribe(System.out::println);

        Thread.sleep(1000);*/

    // o ... triggered by the elements in a source sequence: switchMap (each source element is mapped to a Publisher)
        /*Flux.just("A", "B", "C")
                .delayElements(Duration.ofMillis(100))
                .switchMap(alphabet ->
                        Flux.interval(Duration.ofMillis(40))
                                .map(number -> alphabet + number))
                .subscribe(System.out::println);

        Thread.sleep(500);*/

    // 0 ... triggered by the start of the next publisher in a sequence of publishers: switchOnNext
        /*var fluxOfFluxes = Flux.interval(Duration.ofMillis(200))
                .take(3)
                .map(i ->
                    Flux.interval(Duration.ofMillis(100))
                            .take(3)
                            .map(j -> "Inner " +i+ " -> "+j)
                );

        Flux.switchOnNext(fluxOfFluxes)
                .subscribe(System.out::println);
        Thread.sleep(1000);*/

    // . I want to repeat an existing sequence: repeat (Flux|Mono)
        /*Flux.just("A", "B", "C")
                .repeat(1)
                .subscribe(System.out::println);*/

    // o ... but at time intervals: Flux.interval(duration).flatMap(tick -> myExistingPublisher)

       /* Flux.interval(Duration.ofMillis(5000))
                .flatMap(d -> Flux.just(1, 2, 3, 4, 5))
                .subscribe(System.out::println);
        Thread.sleep(16000);*/

    // . I have a empty sequence
    // o ... I want a value instead: defaultIfEmpty (Flux|Mono)
        /*Flux.empty()
                .defaultIfEmpty("Flux Is Empty")
                .subscribe(System.out::println);*/

    // o ... I want another sequence instead: switchIfEmpty (Flux|Mono)
        /*Flux.empty()
                .switchIfEmpty(Flux.range(1, 5))
                .subscribe(System.out::println);*/

    // . I have a sequence but i am not interested in values: ignoreElements (Flux.ignoreElements()|Mono.ignoreElement())
        /*Flux.just("Apple", "Banana", "Grapes", "Melon")
                .ignoreElements()
                .subscribe(System.out::println, System.err::println, () -> System.out.println("Done"));*/

    // o ... and i want the completion represent Mono<Void>: then (Flux|Mono)
        /*Flux.range(1, 5)
                .ignoreElements()
                .then()
                .subscribe(System.out::println, System.err::println, () -> System.out.println("Done"));*/

    // o ... and i want to wait for another task to complete at the end: thenEmpty (Flux|Mono)
        /*Mono<Void> cleanUp = Mono.fromRunnable(() -> System.out.println("Cleaning up tasks"));
        var tasks = Flux.just("Task1 Started", "Task 2 Started", "Task 3 Started")
                .delayElements(Duration.ofMillis(1000));

        tasks.thenEmpty(cleanUp)
                .subscribe(System.out::println);
        Thread.sleep(3200);*/

    // o ... and i want to switch to another Mono at the end: Mono#then(mono)
        /*Mono<String> mono = Mono.fromCallable(() -> "Additional Task Completed at the end");
        Flux<String> tasks = Flux.just("Task 1", "Task 2", "Task 3");

        tasks.then(mono)
                .subscribe(System.out::println);*/

    // o ... and i want to emit a single value at the end: Mono#thenReturn(T)
        /*Flux<String> flux = Flux.just("A", "B", "C");

        flux.thenMany(Flux.just("I", "II", "III"))
                .subscribe(System.out::println);*/

    // . I have a Mono for which i want to defer completion ...
        /*Mono<String> original = Mono.just("Task completed")
                .doOnNext(System.out::println);

        Mono<Void> deferCompletion = Mono.delay(Duration.ofMillis(1000)).then();

        original
                .then(deferCompletion)
                .thenReturn("Now really completed")
                .subscribe(System.out::println);
        Thread.sleep(1200);*/

    // o ... until another publisher, which is derived from this value, has completed: Mono#delayUntil(Function)

        /*Flux<String> otherTask = Flux.just("I", "II", "III")
                .doOnNext(System.out::println);

        Flux.just("A", "B", "C")
                .delayUntil(v -> otherTask)
                .subscribe(System.out::println);*/

    // . I want to expand elements recursively into a graph of sequence and emit the combination..
    // o ... expanding the graph breath first: expand(Function) (Flux|Mono)
        /*Flux.just(0, 8)
                .expand(i -> {
                    if (i < 10) return Flux.just(i + 1);
                    else return Flux.empty();
                })
                .subscribe(System.out::println);*/

    // o ... expanding the graph depth first: expandDeep(Function) (Flux|Mono)

       /* Flux.just(0, 8)
                .expandDeep(i -> {
                    if (i < 10) return Flux.just(i + 1).delayElements(Duration.ofMillis(200));
                    else return Flux.empty();
                })
                .subscribe(System.out::println);

        Thread.sleep(6000);*/
    }

    static Mono<String> asyncTask(String input) {
        return Mono.delay(Duration.ofMillis(200)).map(i -> "Processed-" +input+ " on thread <- " +Thread.currentThread().getName());
    }

    static Mono<String> asyncTask(int input) {
        if (input % 2 == 0) return Mono.empty();
        return Mono.delay(Duration.ofMillis(200)).map(i -> "Processed-" +input+ " on thread <- " +Thread.currentThread().getName());
    }

    static Flux<String> asyncTaskOne(String userId) {
         return Flux.just("Item 1", "Item 2", "Item 3")
                 .delayElements(Duration.ofMillis(200))
                 .map(item -> userId + " ordered " +item);
    }
}
