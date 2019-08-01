package generics;

public class VegetableGenerator implements Generator<String> {
    @Override
    public String next() {
        return "test vegetable";
    }
}
