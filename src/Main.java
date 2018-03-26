import java.util.Random;
import java.util.LinkedList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
  
  public static LinkedList<Job> queue = new LinkedList<Job>();
  public static Random rand = new Random();
  
  public static void main(String[] args) {
    //mean for interarrival time is 1/lambda = 13ms
    double interArrivalLambda = (1/0.013);
    
    //mean for service time is 1/lambda = 10ms
    double serviceTimeLambda = (1/0.01);
    
    //runtime and nextArrivaltime are in seconds
    double runTime = 0;
    double nextArrivalTime = exponentialRandom(interArrivalLambda);
    double latestArrival = nextArrivalTime;
    
    //number of jobs refers to number of jobs completed
    int numberOfJobs = 0;
    
    //create first job and add it to the queue
    double serviceTime = exponentialRandom(serviceTimeLambda);
    Job newJob = new Job(serviceTime, 0);
    queue.addLast(newJob);
    
    //main loop
    while (runTime < 100){
        //service front of queue. 
        if (!queue.isEmpty() && queue.getFirst().getTriggerTime() < nextArrivalTime){
            runTime = queue.getFirst().getTriggerTime();
            //If the job lasts more than 5ms, move to the back of the queue.
            if (queue.getFirst().getServiceTime() > 0.005){
                Job dummy = new Job(queue.getFirst().getServiceTime() - 0.005, queue.getLast().getEndTime());
                queue.addLast(dummy);
                queue.removeFirst();
            }
            else {
            	runTime += queue.getFirst().getServiceTime();
                queue.removeFirst();
                numberOfJobs++;
                //not entirely accurate as runTime is only updated when a job starts, not when it completes.
                if (numberOfJobs % 1000 == 0)
                	System.out.println("Time (in seconds) to complete " + numberOfJobs + " jobs: " + runTime);
            }     
        }
        
        //next arrival
        if (queue.isEmpty() || nextArrivalTime <= queue.getFirst().getTriggerTime()){
            runTime = nextArrivalTime;
            serviceTime = exponentialRandom(serviceTimeLambda);
            //new job will trigger after the last job's service is complete
            if (!queue.isEmpty()){
                newJob = new Job(serviceTime, queue.getLast().getEndTime());
                nextArrivalTime = latestArrival + exponentialRandom(interArrivalLambda);
                latestArrival = nextArrivalTime;
            }
            else {
                newJob = new Job(serviceTime, runTime);
                nextArrivalTime = runTime + exponentialRandom(interArrivalLambda);
                latestArrival = nextArrivalTime;
            }
            
            queue.addLast(newJob);
        }
    }
    //print how many jobs finished in 100s
    System.out.println("Total number of jobs completed in 100s: " + numberOfJobs);
  }
  
  public static double exponentialRandom(double lambda){
      return Math.log(1-rand.nextDouble())/(-lambda);
  }
}