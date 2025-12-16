public class ThreadSynchronizationDemo {
    
    // Shared counter class
    static class Counter {
        private int count = 0;
        
        // Synchronized method to increment counter
        public synchronized void increment() {
            count++;
        }
        
        public int getCount() {
            return count;
        }
    }
    
    // Thread that increments the counter 100 times
    static class CounterThread extends Thread {
        private Counter counter;
        private String threadName;
        
        public CounterThread(Counter counter, String name) {
            this.counter = counter;
            this.threadName = name;
        }
        
        @Override
        public void run() {
            System.out.println(threadName + " started");
            for (int i = 0; i < 100; i++) {
                counter.increment();
            }
            System.out.println(threadName + " completed 100 increments");
        }
    }
    
    public static void main(String[] args) {
        System.out.println("Lab Task 2: Thread Synchronization");
        System.out.println("===================================\n");
        
        // Create shared counter
        Counter counter = new Counter();
        
        // Create three threads
        CounterThread thread1 = new CounterThread(counter, "Thread-1");
        CounterThread thread2 = new CounterThread(counter, "Thread-2");
        CounterThread thread3 = new CounterThread(counter, "Thread-3");
        
        // Start all threads
        thread1.start();
        thread2.start();
        thread3.start();
        
        // Wait for all threads to complete
        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("\n=== Results ===");
        System.out.println("Final Counter Value: " + counter.getCount());
        System.out.println("Expected Value: 300");
        
        if (counter.getCount() == 300) {
            System.out.println("✓ SUCCESS: Synchronization worked correctly!");
        } else {
            System.out.println("✗ FAILURE: Race condition occurred!");
        }
        
        System.out.println("\nNote: The synchronized keyword ensures that only one thread");
        System.out.println("can execute the increment() method at a time, preventing race conditions.");
    }
}