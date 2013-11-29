package pl.lodz.uni.math.FiniteStateMachine;

public class TimeCounter {
    static long startTime = 0;
                  
 static long estimatedTime = 0;
 public static void start()
 {
     startTime=System.nanoTime(); 
 }
 public static long stopTime()
 {
     estimatedTime=System.nanoTime() - startTime;
     return estimatedTime;
 }
         
}
