import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentDataStructuresDemo {
    
    // Thread that writes to the list
    static class ListWriter extends Thread {
        private CopyOnWriteArrayList<String> list;
        private String threadName;
        
        public ListWriter(CopyOnWriteArrayList<String> list, String name) {
            this.list = list;
            this.threadName = name;
        }
        
        @Override
        public void run() {
            for (int i = 1; i <= 5; i++) {
                String item = threadName + "-Item" + i;
                list.add(item);
                System.out.println(threadName + " added: " + item);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    // Thread that reads from the list
    static class ListReader extends Thread {
        private CopyOnWriteArrayList<String> list;
        private String threadName;
        
        public ListReader(CopyOnWriteArrayList<String> list, String name) {
            this.list = list;
            this.threadName = name;
        }
        
        @Override
        public void run() {
            for (int i = 0; i < 3; i++) {
                System.out.println(threadName + " reading list. Size: " + list.size());
                for (String item : list) {
                    System.out.println("  " + threadName + " sees: " + item);
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    // Thread that writes to the map
    static class MapWriter extends Thread {
        private ConcurrentHashMap<String, Integer> map;
        private String threadName;
        
        public MapWriter(ConcurrentHashMap<String, Integer> map, String name) {
            this.map = map;
            this.threadName = name;
        }
        
        @Override
        public void run() {
            for (int i = 1; i <= 5; i++) {
                String key = threadName + "-Key" + i;
                map.put(key, i * 10);
                System.out.println(threadName + " added: " + key + " = " + (i * 10));
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public static void main(String[] args) {
        System.out.println("Lab Task 3: Concurrent Data Structures");
        System.out.println("=======================================\n");
        
        // Part 1: CopyOnWriteArrayList Demo
        System.out.println("=== CopyOnWriteArrayList Demo ===");
        CopyOnWriteArrayList<String> sharedList = new CopyOnWriteArrayList<>();
        
        ListWriter writer1 = new ListWriter(sharedList, "Writer-1");
        ListWriter writer2 = new ListWriter(sharedList, "Writer-2");
        ListReader reader1 = new ListReader(sharedList, "Reader-1");
        
        writer1.start();
        writer2.start();
        reader1.start();
        
        try {
            writer1.join();
            writer2.join();
            reader1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("\nFinal list size: " + sharedList.size());
        System.out.println("Final list contents: " + sharedList);
        
        // Part 2: ConcurrentHashMap Demo
        System.out.println("\n\n=== ConcurrentHashMap Demo ===");
        ConcurrentHashMap<String, Integer> sharedMap = new ConcurrentHashMap<>();
        
        MapWriter mapWriter1 = new MapWriter(sharedMap, "MapWriter-1");
        MapWriter mapWriter2 = new MapWriter(sharedMap, "MapWriter-2");
        
        mapWriter1.start();
        mapWriter2.start();
        
        try {
            mapWriter1.join();
            mapWriter2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("\nFinal map size: " + sharedMap.size());
        System.out.println("Final map contents: " + sharedMap);
        
        System.out.println("\n=== Key Concepts ===");
        System.out.println("✓ CopyOnWriteArrayList: Thread-safe list for concurrent reads/writes");
        System.out.println("✓ ConcurrentHashMap: Thread-safe map without explicit synchronization");
        System.out.println("✓ No race conditions occur with these data structures!");
    }
}