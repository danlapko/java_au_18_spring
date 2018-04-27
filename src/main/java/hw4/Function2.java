package hw4;

@FunctionalInterface
public interface Function2<V, T, R> {
    R apply(V v, T t);

    default <R1> Function2<V, T, R1> compose(Function1<? super R, R1> after) {
        return (V v, T t) -> after.apply(apply(v, t));
    }

    default Function1<T, R> bind1(V v) {
        return (T t) -> apply(v, t);
    }

    default Function1<V, R> bind2(T t) {
        return (V v) -> apply(v, t);
    }

    default Function1<V, Function1<T, R>> curry() {
        return (V v) -> (T t) -> apply(v, t);
    }
}
