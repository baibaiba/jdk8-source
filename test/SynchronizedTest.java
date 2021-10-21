class Person {

    public static synchronized void method1() {
        System.out.println("Person:method1:static-synchronized-method");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public synchronized void method2() {
        System.out.println("Person:method1:synchronized-method");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void method3() {
        synchronized (this) {
            System.out.println("Person:method1:synchronized-chunk-method");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static Person person = new Person();

    public static void main(String[] args) {
        // 1
        new Thread(new Runnable() {
            @Override
            public void run() {
                Person.method1();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Person.method1();
            }
        }).start();

        // 2
        // new Thread(new Runnable() {
        //     @Override
        //     public void run() {
        //         person.method2();
        //     }
        // }).start();
        //
        //
        // new Thread(new Runnable() {
        //     @Override
        //     public void run() {
        //         person.method2();
        //     }
        // }).start();


        // new Thread(new Runnable() {
        //     @Override
        //     public void run() {
        //         person.method3();
        //     }
        // }).start();
        //
        //
        // new Thread(new Runnable() {
        //     @Override
        //     public void run() {
        //         person.method3();
        //     }
        // }).start();
    }
}
