package schedulerdisk;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author [name]
 */
public class DiskScheduler {

    private final int cylinders;
    private int currentCylinder;
    private final int previousCylinder;
    private int totalMoves;

    public DiskScheduler(int cylinders, int currentCylinder, int previousCylinder) {
        this.cylinders = cylinders;
        this.currentCylinder = currentCylinder;
        this.previousCylinder = previousCylinder;
        this.totalMoves = 0;
    }

    public int getTotalMoves() {
        return this.totalMoves;
    }

    public void useFCFS(String requestQueue) {
        throw new UnsupportedOperationException();
    }

    public void useSSTF(String requestQueue) {
        String[] commaSeparatedArr = requestQueue.split("\\s*,\\s*");
        SimpleRequest current = new SimpleRequest(this.currentCylinder);
        ArrayList<SimpleRequest> Requests = new ArrayList<SimpleRequest>();
        for(int i=0; i<commaSeparatedArr.length; i++){
            Requests.add(new SimpleRequest(Integer.parseInt(commaSeparatedArr[i])));
        }
        
        
        while(Requests.size() > 0){
            SimpleRequest minDistReq = new SimpleRequest(1000000);
            int minDistInt = minDistReq.getCylinder();
            System.out.println("CURRENT " + current.getCylinder());
            for(int i = 0; i<Requests.size();i++){
                System.out.print(Requests.get(i).getCylinder() + " ");
            }
            System.out.println("");
            for(int i=0; i<Requests.size();i++){
                ArrayList<Integer> Vals = new ArrayList<Integer>();
                Vals.add(current.getCylinder());
                Vals.add(Requests.get(i).getCylinder());
                int minIndex = Vals.indexOf(Collections.min(Vals));
                int maxIndex = Vals.indexOf(Collections.max(Vals));
                int distance = Vals.get(maxIndex) - Vals.get(minIndex);
                if (distance < minDistInt){
                    minDistReq = Requests.get(i);
                    minDistInt = distance;
                }                  
            }
            System.out.println("Minimum distance:" + minDistReq.getCylinder());
            this.totalMoves += minDistInt;
            current = minDistReq;
            Requests.remove(minDistReq);
        }        
    }

    public void useLOOK(String requestQueue) {
        throw new UnsupportedOperationException();
    }

    public void useCLOOK(String requestQueue) {
        throw new UnsupportedOperationException();
    }

}
