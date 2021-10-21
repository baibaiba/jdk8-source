import java.util.ArrayList;
import java.util.List;

public class NotifyAndNotifyAllTest {
    private Buffer mBuf = new Buffer();

    public void produce() {
        synchronized (this) {
//            if (mBuf.isFull()) {
//                try {
//                    wait();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
            while (mBuf.isFull()) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            mBuf.add();
            notify();
        }
    }

    public void consume() {
        synchronized (this) {
//            if (mBuf.isEmpty()) {
//                try {
//                    wait();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
            while (mBuf.isEmpty()) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            mBuf.remove();
            notify();
        }
    }

    private static class Buffer {
        private static final int MAX_CAPACITY = 1;
        private List<Object> innerList = new ArrayList<>(MAX_CAPACITY);

        void add() {
            if (isFull()) {
                throw new IndexOutOfBoundsException();
            } else {
                innerList.add(new Object());
            }
            System.out.println(Thread.currentThread().getName() + " add");

        }

        void remove() {
            if (isEmpty()) {
                throw new IndexOutOfBoundsException();
            } else {
                innerList.remove(MAX_CAPACITY - 1);
            }
            System.out.println(Thread.currentThread().getName() + " remove");
        }

        boolean isEmpty() {
            return innerList.isEmpty();
        }

        boolean isFull() {
            return innerList.size() == MAX_CAPACITY;
        }
    }

    public static void main(String[] args) {
        NotifyAndNotifyAllTest sth = new NotifyAndNotifyAllTest();
        Runnable runProduce = new Runnable() {
            int count = 4;

            @Override
            public void run() {
                while (count-- > 0) {
                    sth.produce();
                }
            }
        };
        Runnable runConsume = new Runnable() {
            int count = 4;

            @Override
            public void run() {
                while (count-- > 0) {
                    sth.consume();
                }
            }
        };
        for (int i = 1; i <= 2; i++) {
            new Thread(runConsume, "消费者" + i).start();
        }
        for (int i = 0; i < 2; i++) {
            new Thread(runProduce, "生产者" + i).start();
        }
    }
}
