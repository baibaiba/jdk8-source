import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ArrayListSourceTest {
    public static void main(String[] args) {
        // 如果构造函数传0，然后添加10个元素，会进行几次扩容？
        // 答案：7次
        /**
         * 为什么是七次？因为使用的是EMPTY_ELEMENTDATA而非DEFAULTCAPACITY_EMPTY_ELEMENTDATA，然后在calculateCapacity没有走的以下逻辑：
         * if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
         *      return Math.max(DEFAULT_CAPACITY, minCapacity);
         * }
         * 所以每次会以近似1.5倍（奇数除以2，除不尽）进行扩容
         **/

        // 不要使用0作为构造参数构建ArrayList
        // 那么存在这个构造函数的意义是什么呢？在我们确定容量且不会进行增删的时候使用
        ArrayList<Object> objects = new ArrayList<>(0);
        objects.add("a");
        objects.add("b");
        objects.add("c");
        objects.add("d");
        objects.add("e");
        objects.add("f");
        objects.add("g");
        objects.add("h");
        objects.add("i");
        objects.add("j");
        System.out.println(objects);

        List<Object> objects1 = Arrays.asList();
        // 元素固定场景使用，不能做增删，但是可以修改（基于index修改）
        // 因为底层实现的是Arrays的内部类ArrayList,继承了AbstractList类，但是没有重写add、remove方法，所以直接调用的是父类的方法，
        // 而AbstractList类的add、remove方法直接抛出UnsupportedOperationException异常

        List<Object> objects2 = Collections.emptyList();
        // if else场景直接返回一个空数组使用，不会创建新空间（上面的都会使用new关键字开辟内存空间）
    }
}
