public class MultithreadingDemo {
    
    // Thread 1: Prints numbers from 1 to 10 using Thread class
    static class NumberPrinter extends Thread {
        @Override
        public void run() {
            for (int i = 1; i <= 10; i++) {
                System.out.println("Number: " + i);
                try {
                    Thread.sleep(100); // Small delay to observe concurrency
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    // Thread 2: Prints squares of numbers from 1 to 10 using Runnable interface
    static class SquarePrinter implements Runnable {
        @Override
        public void run() {
            for (int i = 1; i <= 10; i++) {
                System.out.println("Square of " + i + ": " + (i * i));
                try {
                    Thread.sleep(100); // Small delay to observe concurrency
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public static void main(String[] args) {
        System.out.println("Lab Task 1: Introduction to Multithreading");
        System.out.println("==========================================\n");
        
        // Create first thread using Thread class
        NumberPrinter thread1 = new NumberPrinter();
        
        // Create second thread using Runnable interface
        Thread thread2 = new Thread(new SquarePrinter());
        
        // Start both threads
        thread1.start();
        thread2.start();
        
        // Wait for both threads to complete
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("\nBoth threads completed execution.");
        System.out.println("Notice how the output is interleaved, demonstrating concurrent execution!");
    }
}