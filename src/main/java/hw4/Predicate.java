package hw4;

@FunctionalInterface
public interface Predicate<T> extends Function1<T, Boolean> {
    Boolean apply(T t);

    default Predicate<T> and(Predicate<? super T> other) {
        return (T t) -> apply(t) && other.apply(t);
    }

    default Predicate<T> or(Predicate<? super T> other) {
        return (T t) -> apply(t) || other.apply(t);
    }

    default Predicate<T> not() {
        return (T t) -> !apply(t);
    }

    Predicate<Object> ALWAYS_TRUE = (Object o) -> true;

    Predicate<Object> ALWAYS_FALSE = (Object o) -> false;
}
