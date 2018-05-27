package hw5;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.file.Files.readAllLines;

public final class SecondPartTasks {

    private SecondPartTasks() {
    }

    // Найти строки из переданных файлов, в которых встречается указанная подстрока.
    public static List<String> findQuotes(List<String> paths, CharSequence sequence) {
        return paths.stream().flatMap((String path) -> {
                    try {
                        return readAllLines(Paths.get(path)).stream();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return Stream.empty();
                }
        ).filter((String s) -> s.contains(sequence)).collect(Collectors.toList());
    }


    // В квадрат с длиной стороны 1 вписана мишень.
    // Стрелок атакует мишень и каждый раз попадает в произвольную точку квадрата.
    // Надо промоделировать этот процесс с помощью класса java.util.Random и посчитать, какова вероятность попасть в мишень.
    //    sum (a^2 + b^2 < r^2)  (where r==0.5)
    public static double piDividedBy4() {
        Random rnd = new Random();
        return rnd.doubles(1000000, -0.5, 0.5).map(x -> {
            double y = rnd.nextDouble() - 0.5;
            return x * x + y * y;
        }).filter(p -> p <= 0.25).count() / 1000000.0;
    }

    // Дано отображение из имени автора в список с содержанием его произведений.
    // Надо вычислить, чья общая длина произведений наибольшая.
    public static String findPrinter(Map<String, List<String>> compositions) {
        return compositions.entrySet().stream().max(Comparator.comparing(entry ->
                entry.getValue().stream().mapToInt(String::length).sum()))
                .orElseThrow(RuntimeException::new).getKey();
    }

    // Вы крупный поставщик продуктов. Каждая торговая сеть делает вам заказ в виде Map<Товар, Количество>.
    // Необходимо вычислить, какой товар и в каком количестве надо поставить.
    public static Map<String, Integer> calculateGlobalOrder(List<Map<String, Integer>> orders) {
        return orders.stream().flatMap(map -> map.entrySet().stream())
                .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.summingInt(Map.Entry::getValue)));
    }
}
