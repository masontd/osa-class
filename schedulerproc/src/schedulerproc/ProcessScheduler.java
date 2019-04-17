package schedulerproc;
/**
 * @author yasiro01
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Process scheduler
 * 
 * readyQueue is a list of processes ready for execution
 * rrQuantum is the time quantum used by round-robin algorithm
 * add() and clear() are wrappers around ArrayList methods
 */
public class ProcessScheduler {
    private final ArrayList<SimpleProcess> readyQueue;
    private final int rrQuantum;

    public ProcessScheduler() {
        this.readyQueue = new ArrayList<>();
        this.rrQuantum = 4;
    }

    public void add(SimpleProcess newProcess) {
        this.readyQueue.add(newProcess);
    }

    public void clear() {
        this.readyQueue.clear();
    }

    /**
     * FCFS scheduling algorithm implementation
     * 
     * @return average waiting time for all processes
     */
    public double useFirstComeFirstServe() {
        double waittime = 0;
        double sum = 0;
        for(int i = 0; i < this.readyQueue.size(); i++){
            sum += waittime;
            SimpleProcess derp = this.readyQueue.get(i);
            waittime += derp.getNextBurst();
        }
        return sum / this.readyQueue.size();
    }

    /**
     * SJF scheduling algorithm implementation
     * 
     * @return average waiting time for all processes
     */
    public double useShortestJobFirst() {
        ArrayList<SimpleProcess> newQueue = this.readyQueue;
        newQueue.sort(Comparator.comparing(SimpleProcess::getNextBurst));
        double waittime = 0;
        double sum = 0;
        for(int i = 0; i < this.readyQueue.size(); i++){
            sum += waittime;
            SimpleProcess derp = this.readyQueue.get(i);
            waittime += derp.getNextBurst();
        }
        return sum / this.readyQueue.size();
    }

    /**
     * Priority scheduling algorithm implementation
     * 
     * @return average waiting time for all processes
     */
    public double usePriorityScheduling() {
        ArrayList<SimpleProcess> newQueue = this.readyQueue;
        newQueue.sort(Comparator.comparing(SimpleProcess::getPriority));
        double waittime = 0;
        double sum = 0;
        for(int i = 0; i < this.readyQueue.size(); i++){
            sum += waittime;
            SimpleProcess derp = this.readyQueue.get(i);
            waittime += derp.getNextBurst();
        }
        return sum / this.readyQueue.size();    
    }

    /**
     * Round-Robin scheduling algorithm implementation
     * 
     * @return average waiting time for all processes
     */
    public double useRoundRobin() {
        
        int n = this.readyQueue.size();
        int totaltime = 0;
        int waitTimes[] = new int[n];
        int burstTimes[] = new int[n];
        for (int i = 0; i < n; i ++){
            burstTimes[i] = this.readyQueue.get(i).getNextBurst();
            
        }
        
        int t = 0; //current time
        
        boolean done = false;
        while(done == false){
            done = true;
            for (int i = 0; i < n; i ++)
            {
                if (burstTimes[i] > 0)
                {
                  done = false;
                  if(burstTimes[i] > this.rrQuantum)
                  {
                      totaltime+=this.rrQuantum;
                      burstTimes[i] -= this.rrQuantum;
                  }
                  else
                  {
                      totaltime = totaltime + burstTimes[i];
                      waitTimes[i] = totaltime - this.readyQueue.get(i).getNextBurst();
                      burstTimes[i] = 0;
                  }                  
                }
            }
        }
        int total_wt = 0;
        for (int i=0; i<n; i++)
        {
            total_wt = total_wt + waitTimes[i];
        }
        return (double)total_wt/(double)n;    

    }
    
}