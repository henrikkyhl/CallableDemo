
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CallableDemo {

    public static void main(String[] args) {
        System.out.println("Running one Counter as a thread");
        
        // Callables are functional interfaces just like runnables but instead 
        // of being void they return a value.
        Callable<Integer> task = new CallableCounter(300);
        
        // In this example we use an executor with a thread pool of size one.
        ExecutorService executor = Executors.newFixedThreadPool(1);
        
        // The executor returns a special result of type Future which can be 
        // used to retrieve the actual result at a later point in time.
        Future<Integer> future = executor.submit(task);
                        
        System.out.println("Future is done? " + future.isDone());
        
        try {
            // Calling the method get() blocks the current thread and waits 
            // until the callable completes before returning the actual result.
            Integer result = future.get();
            
            // The following line will wait for a result for the specified
            // duration. If a result is not available after this duration, a
            // "java.util.concurrent.TimeoutException" is thrown.            
            //Integer result = future.get(2, TimeUnit.SECONDS);
            
            System.out.println("Counter value:" + result);
        }
        catch (Exception ex){
            Logger.getLogger("ThreadDemo").log(Level.SEVERE, null, ex);
        }
        finally {
            executor.shutdownNow();
        }
                
        
    }
}

class CallableCounter implements Callable<Integer> {
    private int limit;
    
    public CallableCounter(int limit){
        this.limit = limit;
    }
    public Integer call() throws Exception {
        int count = 0;
        for (int i=1; i <= limit; i++){
            count = i;
            TimeUnit.MILLISECONDS.sleep(10);
        }
        return count;
    }
    
}