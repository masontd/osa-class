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
        for(int i = 0; i < this.readyQueue.size()-1; i++){
            SimpleProcess derp = this.readyQueue.get(i);
            waittime += waittime + derp.getNextBurst();
        }
        return waittime / this.readyQueue.size();
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
        for(int i = 0; i < this.readyQueue.size()-1; i++){
            SimpleProcess derp = this.readyQueue.get(i);
            waittime += waittime + derp.getNextBurst();
        }
        return waittime / this.readyQueue.size();
    }

    /**
     * Priority scheduling algorithm implementation
     * 
     * @return average waiting time for all processes
     */
    public double usePriorityScheduling() {
        throw new UnsupportedOperationException();
    }

    /**
     * Round-Robin scheduling algorithm implementation
     * 
     * @return average waiting time for all processes
     */
    public double useRoundRobin() {
        throw new UnsupportedOperationException();
    }
    

}