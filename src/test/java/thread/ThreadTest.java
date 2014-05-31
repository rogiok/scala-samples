package thread;

import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Rogerio on 5/31/14 3:38 PM.
 */
public class ThreadTest {

    public static int count = 0;
    public static volatile int vCount = 0;
    public static AtomicInteger aCount = new AtomicInteger(0);

    @Test
    public void test() {

        ArrayList<Thread> threads = new ArrayList<Thread>();

        for (int i = 0; i < 2; i++)
            threads.add(new Thread() {
                @Override
                public void run() {
                    for (int j = 0; j < 10000; j++) {
                        count++;
//                    count = count + 1;
                        vCount++;
                        aCount.incrementAndGet();
                    }
                }
            });

        for (Thread t : threads)
            t.start();

        for (Thread t : threads)
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        System.out.println("count: " + count);
        System.out.println("vCount: " + vCount);
        System.out.println("aCount: " + aCount);


    }
}
