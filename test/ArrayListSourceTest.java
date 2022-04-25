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
                       // minCapacity（size+1）minCapacity-elementData.length>0扩容  计算newCapacity      elementData最终容量（两个判断，见grow方法）
        objects.add("a"); // minCapacity=1   1-0(elementData.length)>0             0+（0/2）=0           1（扩容后）
        objects.add("b"); // minCapacity=2   2-1(elementData.length)>0             1+（1/2）=1           2（扩容后）
        objects.add("c"); // minCapacity=3   3-2(elementData.length)>0             2+（2/2）=3           3（扩容后）
        objects.add("d"); // minCapacity=4   4-3(elementData.length)>0             3+（3/2）=4           4（扩容后）
        objects.add("e"); // minCapacity=5   5-4(elementData.length)>0             4+（4/2）=6           6（扩容后）
        objects.add("f"); // minCapacity=6   6-6(elementData.length)！>0           不扩容
        objects.add("g"); // minCapacity=7   7-6(elementData.length)>0             6+（6/2）=9           9（扩容后）
        objects.add("h"); // minCapacity=8   8-9(elementData.length)！>0           不扩容
        objects.add("i"); // minCapacity=9   9-9(elementData.length)！>0           不扩容
        objects.add("j"); // minCapacity=10  10-9(elementData.length)>0            9+（9/2）=13          13（扩容后）
        System.out.println(objects);

        List<Object> objects1 = Arrays.asList();
        // 元素固定场景使用，不能做增删，但是可以修改（基于index修改）
        // 因为底层实现的是Arrays的内部类ArrayList,继承了AbstractList类，但是没有重写add、remove方法，所以直接调用的是父类的方法，
        // 而AbstractList类的add、remove方法直接抛出UnsupportedOperationException异常

        List<Object> objects2 = Collections.emptyList();
        // if else场景直接返回一个空数组使用，不会创建新空间（上面的都会使用new关键字开辟内存空间）
    }
}
