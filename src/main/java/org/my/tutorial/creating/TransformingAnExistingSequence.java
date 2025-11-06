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
