package hw4;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;

public final class Collections {
    public static <T, R> Iterable<R> map(Function1<? super T, ? extends R> func, Iterable<T> col) {
        return new Iterable<R>() {
            @Override
            public Iterator<R> iterator() {
                return new Iterator<R>() {
                    private final Iterator<T> it = col.iterator();

                    @Override
                    public boolean hasNext() {
                        return it.hasNext();
                    }

                    @Override
                    public R next() {
                        return func.apply(it.next());
                    }
                };
            }
        };
    }

    public static <T> Iterable<T> filter(Predicate<? super T> pred, Iterable<T> col) {
        return new Iterable<T>() {
            @Override
            public Iterator<T> iterator() {
                return new Iterator<T>() {
                    private final Iterator<T> it = col.iterator();
                    private T nextElem = null;
                    private boolean nextExists = false;

                    {
                        goNext();
                    }

                    // go to next satisfiable element in col
                    private void goNext() {
                        nextExists = false;
                        while (it.hasNext()) {
                            nextElem = it.next();
                            if (pred.apply(nextElem)) {
                                nextExists = true;
                                break;
                            }
                        }
                    }

                    @Override
                    public boolean hasNext() {
                        return nextExists;
                    }

                    @Override
                    public T next() {
                        if (!nextExists) {
                            throw (new NoSuchElementException());
                        }
                        T tmp = nextElem;
                        goNext();
                        return tmp;
                    }
                };
            }
        };
    }

    public static <T> Iterable<T> takeWhile(Predicate<? super T> pred, Iterable<T> col) {
        return new Iterable<T>() {
            @Override
            public Iterator<T> iterator() {
                return new Iterator<T>() {
                    private final Iterator<T> it = col.iterator();
                    private T nextElem = null;
                    private boolean nextExists = false;

                    {
                        checkNext();
                    }

                    private void checkNext() {
                        if (it.hasNext()) {
                            nextElem = it.next();
                            if (pred.apply(nextElem)) {
                                nextExists = true;
                            } else {
                                nextElem = null;
                                nextExists = false;
                            }
                        }
                    }

                    @Override
                    public boolean hasNext() {
                        return nextExists;
                    }

                    @Override
                    public T next() {
                        if (!nextExists) {
                            throw (new NoSuchElementException());
                        }
                        T tmp = nextElem;
                        checkNext();
                        return tmp;
                    }
                };
            }
        };
    }

    public static <T> Iterable<T> takeUnless(Predicate<? super T> pred, Iterable<T> col) {
        return takeWhile(pred.not(), col);
    }

    public static <V, T> V foldl(Function2<? super V, ? super T, ? extends V> func, V ini, Iterable<T> col) {
        for (T elem : col) {
            ini = func.apply(ini, elem);
        }
        return ini;
    }

    public static <V, T> T foldr(Function2<? super V, ? super T, ? extends T> func, T ini, Iterable<V> col) {
        Iterator<V> it = col.iterator();
        if (!it.hasNext()) {
            return ini;
        } else {
            V tmp = it.next();
            return func.apply(tmp, foldr(func, ini, () -> it));
        }
    }

}
