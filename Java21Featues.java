package util;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Java21Features {

    public void createTread(){

        try(ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()){
            for(int i=0;i<5;i++){
                int taskId = i;
                executor.submit(() -> {
                    try{
                        System.out.println("Task ID: "+taskId);
                        Thread.sleep(2000);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                        System.out.println("Thread : "+Thread.currentThread());
                });
            }
            
        }

    }
    
}
