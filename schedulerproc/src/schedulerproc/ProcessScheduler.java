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
        ArrayList<SimpleProcess> newQueue = this.readyQueue;
        ArrayList newQueuePreEmpt = new ArrayList<>(newQueue.size());
        for(int i = 0; i < newQueue.size(); i++){
            if ((newQueue.get(i).getNextBurst()/this.rrQuantum) < 1 ){
                newQueuePreEmpt.set(i, 1);
            }
            else{
              newQueuePreEmpt.set(i, (newQueue.get(i).getNextBurst()/this.rrQuantum));
            }
        }
        for(int i = 0; i < newQueuePreEmpt.size(); i++){
            System.out.println(newQueuePreEmpt.get(i));
        }
        return 5.5;

    }
    

}