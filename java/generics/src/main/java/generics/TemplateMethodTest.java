package generics;

public class TemplateMethodTest {
    static abstract class GenericWithCreate<T> {
        final T element;
        GenericWithCreate() {
            element = create();
        }
        abstract T create();
    }

    static class X {}

    static class Creator extends GenericWithCreate<X> {
        X create() {
            return new X();
        }
    }

    public static void main(String[] args) {
        Creator c = new Creator();
        System.out.println(c.element.getClass().getSimpleName());
    }
}
