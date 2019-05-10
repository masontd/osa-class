package schedulerdisk;
import java.util.Comparator;
import java.util.HashMap;
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
        String[] commaSeparatedArr = requestQueue.split("\\s*,\\s*");
        SimpleRequest current = new SimpleRequest(this.currentCylinder);
        ArrayList<SimpleRequest> Requests = new ArrayList<SimpleRequest>();
        for(int i=0; i<commaSeparatedArr.length; i++){
            Requests.add(new SimpleRequest(Integer.parseInt(commaSeparatedArr[i])));
        }
        System.out.println("HERE");
        for(int i=0; i<Requests.size(); i++){
            System.out.println("i" + i);
            ArrayList<Integer> Vals = new ArrayList<Integer>();
            Vals.add(current.getCylinder());
            Vals.add(Requests.get(i).getCylinder());
            int minIndex = Vals.indexOf(Collections.min(Vals));
            int maxIndex = Vals.indexOf(Collections.max(Vals));
            int distance = Vals.get(maxIndex) - Vals.get(minIndex);
            System.out.println(Vals.get(maxIndex));
            System.out.println(Vals.get(minIndex));
            System.out.println(distance);
            this.totalMoves += distance;
            current = Requests.get(i);
            System.out.println("CUrrent" + current.getCylinder());
            Requests.remove(Integer.valueOf(i));
            System.out.println("LIST");
            for(int j = 0; j<Requests.size();j++){
              System.out.print(Requests.get(j) + " ");
            }
            System.out.println("");
        }
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
            //System.out.println("CURRENT " + current.getCylinder());
            //for(int i = 0; i<Requests.size();i++){
                //System.out.print(Requests.get(i).getCylinder() + " ");
            //}
            //System.out.println("");
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
            //System.out.println("Minimum distance:" + minDistReq.getCylinder());
            this.totalMoves += minDistInt;
            current = minDistReq;
            Requests.remove(minDistReq);
        }        
    }

    public void useLOOK(String requestQueue) {
        String[] commaSeparatedArr = requestQueue.split("\\s*,\\s*");
        SimpleRequest current = new SimpleRequest(this.currentCylinder);
        String direction;
        ArrayList<Integer> Requests = new ArrayList<Integer>();
        for(int i=0; i<commaSeparatedArr.length; i++){
            Requests.add(Integer.parseInt(commaSeparatedArr[i]));
        }
        
        System.out.println("HERE1");
        for(int i = 0; i<Requests.size();i++){
                System.out.print(Requests.get(i) + " ");
        }
        System.out.println("");
        
        Collections.sort(Requests);

        System.out.println("HERE2");
        //for(int i = 0; i<Requests.size();i++){
        //    System.out.print(Requests.get(i).getCylinder() + " ");
        //}
        for(int i = 0; i<Requests.size();i++){
                System.out.print(Requests.get(i) + " ");
        }
        System.out.println("HERE3");
        HashMap<Integer, SimpleRequest> Dict = new HashMap<Integer, SimpleRequest>();
        for(int i=0; i<Requests.size(); i++){
            Dict.put(Requests.get(i), new SimpleRequest(Requests.get(i)));
        }
        System.out.println("HERE4");
        System.out.println(Dict.keySet());
        if(this.currentCylinder > this.previousCylinder){
           int overmax = Collections.max(Requests);
            System.out.println("HERE5");
           System.out.println(overmax);
           for(int i = this.currentCylinder; i<=overmax; i++){
               if (Requests.contains(i)){
                   ArrayList<Integer> Vals = new ArrayList<Integer>();
                   Vals.add(current.getCylinder());
                   Vals.add(Dict.get(i).getCylinder());
                   int minIndex = Vals.indexOf(Collections.min(Vals));
                   int maxIndex = Vals.indexOf(Collections.max(Vals));
                   int distance = Vals.get(maxIndex) - Vals.get(minIndex);
                   this.totalMoves+=distance;
                   current = Dict.get(i);
                   Requests.remove(Integer.valueOf(i));
               }
           }
           for(int i = overmax; i>=Collections.min(Requests); i--){
               if (Requests.contains(i)){
                   ArrayList<Integer> Vals = new ArrayList<Integer>();
                   Vals.add(current.getCylinder());
                   Vals.add(Dict.get(i).getCylinder());
                   int minIndex = Vals.indexOf(Collections.min(Vals));
                   int maxIndex = Vals.indexOf(Collections.max(Vals));
                   int distance = Vals.get(maxIndex) - Vals.get(minIndex);
                   this.totalMoves+=distance;
                   current = Dict.get(i);
                   Requests.remove(Integer.valueOf(i));
                   if(Requests.size() == 0){
                       break;
                   }
               }
           }
        }
        else{
           System.out.println("HERE6");
           int undermax = Collections.min(Requests);
           System.out.println("UNDERMAX" + undermax);
           for(int i = this.currentCylinder; i>=undermax; i--){
               if (Requests.contains(i)){
                   System.out.println("i" + i);
                   ArrayList<Integer> Vals = new ArrayList<Integer>();
                   Vals.add(current.getCylinder());
                   Vals.add(Dict.get(i).getCylinder());
                   int minIndex = Vals.indexOf(Collections.min(Vals));
                   int maxIndex = Vals.indexOf(Collections.max(Vals));
                   int distance = Vals.get(maxIndex) - Vals.get(minIndex);
                   System.out.println(Vals.get(maxIndex));
                   System.out.println(Vals.get(minIndex));
                   System.out.println("Distance" + distance);
                   this.totalMoves+=distance;
                   current = Dict.get(i);
                   System.out.println("CUrrent" + current.getCylinder());
                   Requests.remove(Integer.valueOf(i));
                   System.out.println("LIST");
                   for(int j = 0; j<Requests.size();j++){
                        System.out.print(Requests.get(j) + " ");
                   }
                   System.out.println("");
               }
           }
            System.out.println("CURRENTMID" + current.getCylinder());
            System.out.println(Requests.toString());
           for(int i = undermax; i<=Collections.max(Requests); i++){
               if (Requests.contains(i)){
                   System.out.println("i" + i);
                   ArrayList<Integer> Vals = new ArrayList<Integer>();
                   Vals.add(current.getCylinder());
                   Vals.add(Dict.get(i).getCylinder());
                   int minIndex = Vals.indexOf(Collections.min(Vals));
                   int maxIndex = Vals.indexOf(Collections.max(Vals));
                   int distance = Vals.get(maxIndex) - Vals.get(minIndex);
                   System.out.println(Vals.get(maxIndex));
                   System.out.println(Vals.get(minIndex));
                   System.out.println("Distance" + distance);
                   this.totalMoves+=distance;
                   current = Dict.get(i);
                   System.out.println("CUrrent" + current.getCylinder());
                   Requests.remove(Integer.valueOf(i));
                   System.out.println("LIST");
                   for(int j = 0; j<Requests.size();j++){
                        System.out.print(Requests.get(j) + " ");
                   }
                   System.out.println("");
                   if(Requests.size() == 0){
                       break;
                   }
               }
           }
        }
    }

    public void useCLOOK(String requestQueue) {
        String[] commaSeparatedArr = requestQueue.split("\\s*,\\s*");
        SimpleRequest current = new SimpleRequest(this.currentCylinder);
        String direction;
        ArrayList<Integer> Requests = new ArrayList<Integer>();
        for(int i=0; i<commaSeparatedArr.length; i++){
            Requests.add(Integer.parseInt(commaSeparatedArr[i]));
        }
        
        System.out.println("HERE1");
        for(int i = 0; i<Requests.size();i++){
                System.out.print(Requests.get(i) + " ");
        }
        System.out.println("");
        
        Collections.sort(Requests);

        System.out.println("HERE2");
        //for(int i = 0; i<Requests.size();i++){
        //    System.out.print(Requests.get(i).getCylinder() + " ");
        //}
        for(int i = 0; i<Requests.size();i++){
                System.out.print(Requests.get(i) + " ");
        }
        System.out.println("HERE3");
        HashMap<Integer, SimpleRequest> Dict = new HashMap<Integer, SimpleRequest>();
        for(int i=0; i<Requests.size(); i++){
            Dict.put(Requests.get(i), new SimpleRequest(Requests.get(i)));
        }
        System.out.println("HERE4");
        System.out.println(Dict.keySet());
        if(this.currentCylinder > this.previousCylinder){
           int overmax = Collections.max(Requests);
            System.out.println("HERE5");
           System.out.println(overmax);
           for(int i = this.currentCylinder; i<=overmax; i++){
               if (Requests.contains(i)){
                   ArrayList<Integer> Vals = new ArrayList<Integer>();
                   Vals.add(current.getCylinder());
                   Vals.add(Dict.get(i).getCylinder());
                   int minIndex = Vals.indexOf(Collections.min(Vals));
                   int maxIndex = Vals.indexOf(Collections.max(Vals));
                   int distance = Vals.get(maxIndex) - Vals.get(minIndex);
                   this.totalMoves+=distance;
                   current = Dict.get(i);
                   Requests.remove(Integer.valueOf(i));
               }
           }
           for(int i = Collections.min(Requests); i<=this.currentCylinder; i++){
               if (Requests.contains(i)){
                   ArrayList<Integer> Vals = new ArrayList<Integer>();
                   Vals.add(current.getCylinder());
                   Vals.add(Dict.get(i).getCylinder());
                   int minIndex = Vals.indexOf(Collections.min(Vals));
                   int maxIndex = Vals.indexOf(Collections.max(Vals));
                   int distance = Vals.get(maxIndex) - Vals.get(minIndex);
                   this.totalMoves+=distance;
                   current = Dict.get(i);
                   Requests.remove(Integer.valueOf(i));
                   if(Requests.size() == 0){
                       break;
                   }
               }
           }
        }
        else{
           System.out.println("HERE6");
           int undermax = Collections.min(Requests);
           System.out.println("UNDERMAX" + undermax);
           for(int i = this.currentCylinder; i>=undermax; i--){
               if (Requests.contains(i)){
                   System.out.println("i" + i);
                   ArrayList<Integer> Vals = new ArrayList<Integer>();
                   Vals.add(current.getCylinder());
                   Vals.add(Dict.get(i).getCylinder());
                   int minIndex = Vals.indexOf(Collections.min(Vals));
                   int maxIndex = Vals.indexOf(Collections.max(Vals));
                   int distance = Vals.get(maxIndex) - Vals.get(minIndex);
                   System.out.println(Vals.get(maxIndex));
                   System.out.println(Vals.get(minIndex));
                   System.out.println("Distance" + distance);
                   this.totalMoves+=distance;
                   current = Dict.get(i);
                   System.out.println("CUrrent" + current.getCylinder());
                   Requests.remove(Integer.valueOf(i));
                   System.out.println("LIST");
                   for(int j = 0; j<Requests.size();j++){
                        System.out.print(Requests.get(j) + " ");
                   }
                   System.out.println("");
               }
           }
            System.out.println("CURRENTMID" + current.getCylinder());
            System.out.println(Requests.toString());
           for(int i = Collections.max(Requests); i>=this.currentCylinder; i--){
               if (Requests.contains(i)){
                   System.out.println("i" + i);
                   ArrayList<Integer> Vals = new ArrayList<Integer>();
                   Vals.add(current.getCylinder());
                   Vals.add(Dict.get(i).getCylinder());
                   int minIndex = Vals.indexOf(Collections.min(Vals));
                   int maxIndex = Vals.indexOf(Collections.max(Vals));
                   int distance = Vals.get(maxIndex) - Vals.get(minIndex);
                   System.out.println(Vals.get(maxIndex));
                   System.out.println(Vals.get(minIndex));
                   System.out.println("Distance" + distance);
                   this.totalMoves+=distance;
                   current = Dict.get(i);
                   System.out.println("CUrrent" + current.getCylinder());
                   Requests.remove(Integer.valueOf(i));
                   System.out.println("LIST");
                   for(int j = 0; j<Requests.size();j++){
                        System.out.print(Requests.get(j) + " ");
                   }
                   System.out.println("");
                   if(Requests.size() == 0){
                       break;
                   }
               }
           }
        }
    }

}
