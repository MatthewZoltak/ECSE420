package ca.mcgill.ecse420.a1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class Deadlock {

    static ReentrantLock lock1 = new ReentrantLock();
    static ReentrantLock lock2 = new ReentrantLock();


    public static void main(String[] args) {
        // Create a thread pool with two threads
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.execute(new Thread1());
        executor.execute(new Thread2());
        executor.shutdown();

    }

    // A task for adding an amount to the account
    public static class Thread1 implements Runnable {
        public void run() {
            synchronized (lock1){
                System.out.println(Thread.currentThread().getName() + " locked reource 1");

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (lock2){
                    System.out.println(Thread.currentThread().getName() + " locked reource 2");
                }
            }
        }
    }

    // A task for subtracting an amount from the account
    public static class Thread2 implements Runnable {
        public void run() {
            synchronized (lock2){
                System.out.println(Thread.currentThread().getName() + " locked reource 2");

                synchronized (lock1){
                    System.out.println(Thread.currentThread().getName() + " locked reource 1");
                }
            }
        }
    }

}