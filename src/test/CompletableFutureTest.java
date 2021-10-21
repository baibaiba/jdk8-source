package jdk8.source;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import static java.util.logging.Logger.getGlobal;

public class CompletableFutureTest {
    private static final Logger logger = getGlobal();

    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        CompletableFuture<String> completableFuture = new CompletableFuture<>();
//        completableFuture.complete("Future's Result Here Manually");
//        System.out.println(completableFuture.get());
//
//
//        CompletableFuture<Void> completableFuture1 = CompletableFuture.runAsync(() -> {
//            try {
//                TimeUnit.SECONDS.sleep(3L);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            System.out.println("任务运行在一个单独的线程中");
//        });
//
//        // 这里调用get方法，如果任务没有完成，还是会阻塞主线程
//        Void aVoid = completableFuture1.get();
//        System.out.println("主线程获取结果为：" + aVoid);

        // 真正的异步处理，我们传入回调函数，在future结束时自动调用该回调函数，这样我们就不用等待结果
        // 证明
        long l = System.currentTimeMillis();
        CompletableFuture<String> completableFuture2 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            logger.info("hhhhh");

            return "谁";
        }).thenApply(firstResult -> {
            logger.info("在看");
            try {
                TimeUnit.SECONDS.sleep(1L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return firstResult + "在看";
        }).thenApply(secondResult -> {
            try {
                TimeUnit.SECONDS.sleep(2L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return secondResult + ",请点赞";
        }).exceptionally(ex -> {
            logger.info(ex.getMessage());
            // exceptionally 就相当于 catch，出现异常，将会跳过 thenApply 的后续操作，直接捕获异常，进行一场处理
            return "异常结束";
        });

        logger.info("这是主线程");
        logger.info(completableFuture2.get());
        logger.info(String.valueOf(System.currentTimeMillis() - l));

        // thenCompose 前
        CompletableFuture<CompletableFuture<CreditRating>> completableFutureCompletableFuture =
                getUserInfo(1L).thenApply(CompletableFutureTest::getUserCredit);
        // thenCompose 后
        CompletableFuture<CreditRating> creditRatingCompletableFuture =
                getUserInfo(1L).thenCompose(CompletableFutureTest::getUserCredit);
        CreditRating creditRating = creditRatingCompletableFuture.get();
        System.out.println(creditRating);

        // 1.听说 ForkJoinPool 线程池效率更高，为什么呢？
        // 2.如果批量处理异步程序，有什么可用的方案吗？
    }

    public static CompletableFuture<User> getUserInfo(Long userId) {
        return CompletableFuture.supplyAsync(() -> new User(1L, "张三"));
    }

    public static CompletableFuture<CreditRating> getUserCredit(User user) {
        return CompletableFuture.supplyAsync(() -> new CreditRating(BigDecimal.valueOf(3.4)));
    }

    public static class User {
        private Long id;
        private String name;

        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }

        public User(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class CreditRating {

        @Override
        public String toString() {
            return "CreditRating{" +
                    "rating=" + rating +
                    '}';
        }

        private BigDecimal rating;

        public CreditRating(BigDecimal rating) {
            this.rating = rating;
        }

        public BigDecimal getRating() {
            return rating;
        }

        public void setRating(BigDecimal rating) {
            this.rating = rating;
        }
    }
}
