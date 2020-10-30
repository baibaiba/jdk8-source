package jdk8.source;

import java.util.ArrayList;

public class ArrayListSourceTest {
    public static void main(String[] args) {
        ArrayList<Object> objects = new ArrayList<>();
        objects.add("a");
        objects.add("a");
        objects.add("a");
        System.out.println(objects);
    }
}
