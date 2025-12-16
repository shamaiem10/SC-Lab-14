import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class BankTransactionSystemDemo {
    
    // Thread-safe Bank Account class
    static class BankAccount {
        private AtomicInteger balance;
        private String accountNumber;
        
        public BankAccount(String accountNumber, int initialBalance) {
            this.accountNumber = accountNumber;
            this.balance = new AtomicInteger(initialBalance);
        }
        
        // Synchronized deposit method
        public synchronized boolean deposit(int amount, String clientName) {
            if (amount > 0) {
                int oldBalance = balance.get();
                balance.addAndGet(amount);
                System.out.println(clientName + " deposited $" + amount + 
                                 " | Old Balance: $" + oldBalance + 
                                 " | New Balance: $" + balance.get());
                return true;
            }
            return false;
        }
        
        // Synchronized withdrawal method
        public synchronized boolean withdraw(int amount, String clientName) {
            if (amount > 0 && balance.get() >= amount) {
                int oldBalance = balance.get();
                balance.addAndGet(-amount);
                System.out.println(clientName + " withdrew $" + amount + 
                                 " | Old Balance: $" + oldBalance + 
                                 " | New Balance: $" + balance.get());
                return true;
            } else {
                System.out.println(clientName + " FAILED to withdraw $" + amount + 
                                 " | Insufficient funds (Balance: $" + balance.get() + ")");
                return false;
            }
        }
        
        public int getBalance() {
            return balance.get();
        }
        
        public String getAccountNumber() {
            return accountNumber;
        }
    }
    
    // Client thread that performs random transactions
    static class ClientThread extends Thread {
        private BankAccount account;
        private String clientName;
        private int numTransactions;
        private Random random;
        
        public ClientThread(BankAccount account, String clientName, int numTransactions) {
            this.account = account;
            this.clientName = clientName;
            this.numTransactions = numTransactions;
            this.random = new Random();
        }
        
        @Override
        public void run() {
            System.out.println(clientName + " started banking operations...");
            
            for (int i = 0; i < numTransactions; i++) {
                // Randomly choose deposit or withdrawal
                boolean isDeposit = random.nextBoolean();
                int amount = random.nextInt(500) + 100; // Random amount between 100-600
                
                if (isDeposit) {
                    account.deposit(amount, clientName);
                } else {
                    account.withdraw(amount, clientName);
                }
                
                // Small delay between transactions
                try {
                    Thread.sleep(random.nextInt(100) + 50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            
            System.out.println(clientName + " completed all transactions.");
        }
    }
    
    public static void main(String[] args) {
        System.out.println("Lab Task 4: Bank Transaction System Simulation");
        System.out.println("===============================================\n");
        
        // Create a bank account with initial balance
        final int INITIAL_BALANCE = 5000;
        BankAccount account = new BankAccount("ACC-12345", INITIAL_BALANCE);
        
        System.out.println("Account Number: " + account.getAccountNumber());
        System.out.println("Initial Balance: $" + INITIAL_BALANCE);
        System.out.println("\n--- Starting Transactions ---\n");
        
        // Create multiple client threads
        ClientThread client1 = new ClientThread(account, "Client-A", 5);
        ClientThread client2 = new ClientThread(account, "Client-B", 5);
        ClientThread client3 = new ClientThread(account, "Client-C", 5);
        ClientThread client4 = new ClientThread(account, "Client-D", 5);
        
        // Start all client threads
        client1.start();
        client2.start();
        client3.start();
        client4.start();
        
        // Wait for all threads to complete
        try {
            client1.join();
            client2.join();
            client3.join();
            client4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Display final results
        System.out.println("\n--- Transaction Summary ---");
        System.out.println("Initial Balance: $" + INITIAL_BALANCE);
        System.out.println("Final Balance: $" + account.getBalance());
        System.out.println("Net Change: $" + (account.getBalance() - INITIAL_BALANCE));
        
        System.out.println("\n=== Key Concepts Demonstrated ===");
        System.out.println("✓ Thread-safe operations using synchronized methods");
        System.out.println("✓ AtomicInteger for thread-safe balance updates");
        System.out.println("✓ Multiple threads accessing shared resource safely");
        System.out.println("✓ No race conditions - balance is always accurate!");
    }
}