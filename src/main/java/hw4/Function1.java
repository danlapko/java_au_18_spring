package hw4;

@FunctionalInterface
public interface Function1<T, R> {
    R apply(T t);

    default <V> Function1<V, R> compose(Function1<? super V, ? extends T> before) {
        return (V v) -> apply(before.apply(v));
    }
}
