package generics;

// cannot write as "class FruitGenerator implements Generator<T>" ---> cannot resolve symbol T for the method next
// or "class FruitGenerator<E> implements Generator<T>" ---> cannot resolve symbol T for the method next
// or "class FruitGenerator<T> implements Generator<E>" ---> T and E clashes
class FruitGenerator<T> implements Generator<T> {
    @Override
    public T next() {
        return (T)"test fruit";
    }
}
