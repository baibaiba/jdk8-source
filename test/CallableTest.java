import java.util.concurrent.*;

public class CallableTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Callable<String> callable = () -> {
            System.out.println("进入 callable 的call 方法");

            TimeUnit.SECONDS.sleep(5L);
            System.out.println("callable 的call 方法 执行完毕");
            return "hello callable call";
        };

        System.out.println("提交任务到线程池");
        Runnable runnable = () -> {
            System.out.println("进入 callable 的call 方法");

            try {
                TimeUnit.SECONDS.sleep(5L);
                System.out.println("callable 的call 方法 执行完毕");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Future<String> submit = executorService.submit(callable);
        System.out.println("主线程继续执行");

        long startTime = System.nanoTime();
//        while (!submit.isDone()) {
//            System.out.println("task is still not done...");
//            TimeUnit.SECONDS.sleep(1L);
//
//            double elapsedTimeInSec = (System.nanoTime() - startTime) / 1000000000.0;
//            // 如果程序运行时间大于 1s，则取消子线程的运行
//            if (elapsedTimeInSec > 1) {
////                submit.cancel(true);
//            }
//        }

        System.out.println("主线程等待获取 future结果");
        if (!submit.isCancelled()) {
            System.out.println("子线程任务已完成");
            String result = submit.get();
            System.out.println("主线程获取到 Future 结果: " + result);
        } else {
            System.out.println("子线程任务被取消");
        }


        executorService.shutdown();
    }
}
