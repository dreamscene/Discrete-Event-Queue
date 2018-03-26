public class Job {
    
  private double serviceTime;
  private double triggerTime;
  
  public Job(double serviceTime, double triggerTime){
      this.serviceTime = serviceTime;
      this.triggerTime = triggerTime;
  }
  
  public double getServiceTime(){
      return serviceTime;
  }
  
  public void setServiceTime(double serviceTime){
      this.serviceTime = serviceTime;
  }
  
  public double getTriggerTime(){
      return triggerTime;
  }
  
  public void setTriggerTime(double triggerTime){
      this.triggerTime = triggerTime;
  }
  
  public double getEndTime(){
      //Jobs serviced for longer than 5ms get added to the back of the queue
      if (serviceTime > 0.005) return (triggerTime + 0.005);
      else return (triggerTime + serviceTime);
  }

}