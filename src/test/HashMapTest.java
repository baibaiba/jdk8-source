package jdk8.source;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HashMapTest {
    private static Map<String, String> map = new HashMap<>();
    private static ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<>();


    public static void main(String[] args) {
        // 线程1 => t1
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 999; i++) {
                    map.put("thread1_key" + i, "thread1_value" + i);
                }
            }
        }).start();
        // 线程2 => t2
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 999; i++) {
                    map.put("thread2_key" + i, "thread2_value" + i);
                }
            }
        }).start();


        /**         // 初始化hash表
         *         if ((tab = table) == null || (n = tab.length) == 0)
         *             n = (tab = resize()).length;
         *          // 通过hash值计算在hash表中的位置，并将这个位置上的元素赋值给p，如果是空的则new一个新的node放在这个位置上
         *         if ((p = tab[i = (n - 1) & hash]) == null)
         *             tab[i] = newNode(hash, key, value, null);
         *         else {
         *             ...
         *         }
         */


        /**
         *          put和get并发时，可能导致get为null
         *          先计算新的容量和threshold，在创建一个新hash表，最后将旧hash表中元素rehash到新的hash表中
         *          resize()方法源码中：
         *             Node<K,V>[] newTab = (Node<K,V>[])new Node[newCap];
         *         table = newTab;
         *
         *         此时别的线程并发，获取为null
         */


        concurrentHashMap.put("a","b");

        /**
         *   if (tab == null || (n = tab.length) == 0)
         *                 tab = initTable();
         *             else if ((f = tabAt(tab, i = (n - 1) & hash)) == null) {
         *                 if (casTabAt(tab, i, null,  // 关注点
         *                              new Node<K,V>(hash, key, value, null)))
         *                     break;                   // no lock when adding to empty bin
         *             }else{
         *                  synchronized (f) {
         *                     if (tabAt(tab, i) == f) {
         *                         ...
         *                     }
         *                 }
         *             }
         */
    }
}
