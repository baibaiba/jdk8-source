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

        // 性能最高的拷贝
        // 方法实现：从源数组的指定位置开始复制指定长度的元素到目标数组的指定位置中
        // 第一个参数就是源数组，第二个参数是要复制的源数组中的起始位置，第三个参数是目标数组，第四个参数是要复制到的目标数组的起始位置，第五个参数是要复制的元素的长度。
        String[] sourceArr = new String[1];
        sourceArr[0] = "a";
        String[] targetArr = new String[5];
        System.arraycopy(sourceArr, 0, targetArr, 2, 1);
        System.out.println(Arrays.toString(targetArr));

        // objects2.add(index , element); 会导致元素后移
        // remove(element); 会导致元素前移


        ArrayList<String> objects3 = new ArrayList<>();
        objects3.add("a");
        objects3.clear();
        objects3.add("e");
        System.out.println("请问经过几次扩容？");
        // clear重制了size，但是没有重制elementData数组的长度，所以以上逻辑只会扩容一次

        ArrayList<Object> objects4 = new ArrayList<>();
        ArrayList<Object> objects5 = new ArrayList<>();
        System.out.println(objects5.addAll(objects4));// false
        // 一定要慎用addAll的返回值
    }
}
