package generics;

import java.util.List;

public class Test {
    public static void main(String[] args) {
        FruitGenerator fg = new FruitGenerator();
        VegetableGenerator vg = new VegetableGenerator();
        System.out.println(fg.next());
        System.out.println(vg.next());

        // List<String>[] lsa = new List<String>[10]; // not allowed
        List<?>[] lsa = new List<?>[10]; // allowed
    }
}
