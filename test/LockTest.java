import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LockTest {

    static AtomicInteger atomicInteger = new AtomicInteger();
    static LongAdder longAdder = new LongAdder();
    static LongAccumulator longAccumulator = new LongAccumulator(Long::max, Long.MIN_VALUE);

    public static void main(String[] args) {
        atomicInteger.incrementAndGet();
        longAdder.add(1);
        // 比较value和上一次的比较值，然后存储较大者
        longAccumulator.accumulate(345L);
        System.out.println(atomicInteger.get());
        System.out.println(longAdder.intValue());
        System.out.println(longAccumulator.intValue());

        ReentrantLock reentrantLock = new ReentrantLock();
        try {
            reentrantLock.lock();
            // TODO
        } finally {
            reentrantLock.unlock();
        }


        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.ReadLock readLock = reentrantReadWriteLock.readLock();

        ReentrantReadWriteLock.WriteLock writeLock=   reentrantReadWriteLock.writeLock();
    }
}
