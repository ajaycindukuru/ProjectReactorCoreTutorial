package org.my.tutorial.creating;

import reactor.core.publisher.Flux;

import java.util.List;

public class TransformingAnExistingSequence {

    public static void main(String[] args) {

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
    }
}
