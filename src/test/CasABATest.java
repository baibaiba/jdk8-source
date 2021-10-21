package jdk8.source;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * https://www.bejson.com/runcode/java/
 * 在线运行
 */
public class CasABATest {
    private static AtomicInteger atomicInt = new AtomicInteger(100);

    private static AtomicStampedReference atomicStampedRef = new AtomicStampedReference(100, 0);

    public static void main(String[] args) {
        // 在使用 CAS 也就是使用 compareAndSet（current，next）方法进行无锁自加或者更换栈的表头之类的
        // 问题时会出现ABA问题
        /**
         * 线程1准备用CAS将变量的值由A替换为B，在此之前，线程2将变量的值由A替换为C，又由C替换为A，然后线程1执行CAS
         时发现变量的值仍然为A
         ，所以CAS成功。但实际上这时的现场已经和最初不同了，尽管CAS成功，但可能存在潜藏的问题 */
//        Thread intT1 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                atomicInt.compareAndSet(100, 101);
//                atomicInt.compareAndSet(101, 100);
//            }
//        });
//        Thread intT2  = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    TimeUnit.SECONDS.sleep(1);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                boolean c3 = atomicInt.compareAndSet(100, 101);
//                System.out.println(c3);
//            }
//        });
//
//        intT1.start(); intT2.start();
//        try {
//            intT1.join();
//            intT2.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }


        Thread refT1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                atomicStampedRef.compareAndSet(100, 101, atomicStampedRef.getStamp(), atomicStampedRef.getStamp() + 1);
                atomicStampedRef.compareAndSet(101, 100, atomicStampedRef.getStamp(), atomicStampedRef.getStamp() + 1);
            }
        });

        Thread refT2 = new Thread(new Runnable() {
            @Override
            public void run() {
                int stamp = atomicStampedRef.getStamp();
                System.out.println("before sleep : stamp = " + stamp); //  stamp = 0
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("after sleep : stamp = " + atomicStampedRef.getStamp()); // stamp = 2
                boolean c3 = atomicStampedRef.compareAndSet(100, 101, stamp, stamp + 1);
                System.out.println(c3); // false
            }
        });

        refT1.start();
        refT2.start();
        try {
            refT1.join();
            refT1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
