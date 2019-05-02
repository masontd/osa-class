package schedulermem;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
/**
 * @author [name]
 */
public class MemoryScheduler {

    private int pageFaultCount;
    private int numFrames;

    public MemoryScheduler(int frames) {
        this.pageFaultCount = 0;
        this.numFrames = frames;
    }

    public int getPageFaultCount() {
        return this.pageFaultCount;
    }
    
    public int getNumFrames() {
        return this.numFrames;
    }

    public void useFIFO(String referenceString) {

        throw new UnsupportedOperationException();
    }

    public void useOPT(String referenceString) {
        System.out.println("BEGIN");
        String[] commaSeparatedArr = referenceString.split("\\s*,\\s*");
        
        /*
        for(int i = 0; i<commaSeparatedArr.length;i++){
            System.out.println(commaSeparatedArr[i]);
        }
        System.out.println("END");
        */
        HashMap<Integer, SimplePage> pages = new HashMap<Integer, SimplePage>();

        for(int i = 0; i < commaSeparatedArr.length; i ++){
            if(!(pages.containsKey(Integer.parseInt(commaSeparatedArr[i])))){
                pages.put(Integer.parseInt(commaSeparatedArr[i]), new SimplePage(Integer.parseInt(commaSeparatedArr[i])));
            }
        }

        List<SimplePage> frames = new ArrayList<SimplePage>(this.numFrames);
        for (int i = 0; i < this.numFrames; i++){
            frames.add(null);
        }
        System.out.println("FRAME SIZE");
        System.out.println(frames.size());
        int frameidx = 0;
        
        for(int i = 0; i < commaSeparatedArr.length; i++){
            System.out.println("TIME: " + i);
            System.out.println("CURRENT PAGE ID: " + pages.get(Integer.parseInt(commaSeparatedArr[i])).getPageId());
            System.out.println("PAGE FAULTS: " + this.pageFaultCount);
            for(int k = 0; k < frames.size(); k++){
                if(frames.get(k) == null){
                    System.out.print("NULL ");
                }
                else{
                    System.out.print(frames.get(k).getPageId() + " ");

                }
            }
            System.out.println("");
            System.out.println("");
            if(frames.contains(pages.get(Integer.parseInt(commaSeparatedArr[i])))){
                //counts.replace(Integer.parseInt(commaSeparatedArr[i]), i);
                
            }
            else{
                this.pageFaultCount ++;
                if (frames.contains(null)){
                    frames.set(frameidx, pages.get(Integer.parseInt(commaSeparatedArr[i])));
                    //counts.replace(Integer.parseInt(commaSeparatedArr[i]), i);

                    frameidx +=1;
                }
                else{
                    System.out.println("ICI");
                    HashMap<Integer, Integer> counts = new HashMap<Integer, Integer>();
                    for(int n = 0; n < frames.size(); n++){
                      counts.put(frames.get(n).getPageId(), -1);
                    }
                    for(int n = 0; n < frames.size(); n++){
                      boolean present = false;
                      for(int p=i+1; p < commaSeparatedArr.length; p++){
                        if ((counts.get(frames.get(n).getPageId())<0) && (Integer.parseInt(commaSeparatedArr[p])==frames.get(n).getPageId())){
                            present = true;
                            counts.replace(frames.get(n).getPageId(), p);
                        }

                      }
                      if(present == false){
                        counts.replace(frames.get(n).getPageId(), 2000);
                      }
                    }
                    int highestct = 0;
                    int nextframe;
                    for(int j=1; j<frames.size(); j++){
                       nextframe = j;
                       if (counts.get(frames.get(nextframe).getPageId())> counts.get(frames.get(highestct).getPageId())){
                           highestct = nextframe;
                       }
                    }
                    

                    //System.out.println(lowestct);
                    //System.out.println(frames.size());
                    //System.out.println(frames.get(lowestct).getPageId());
                    frames.set(highestct, pages.get(Integer.parseInt(commaSeparatedArr[i])));
                }
            }
        }
    }

    public void useLRU(String referenceString) {
        System.out.println("BEGIN");
        String[] commaSeparatedArr = referenceString.split("\\s*,\\s*");
        
        /*
        for(int i = 0; i<commaSeparatedArr.length;i++){
            System.out.println(commaSeparatedArr[i]);
        }
        System.out.println("END");
        */
        HashMap<Integer, SimplePage> pages = new HashMap<Integer, SimplePage>();
        HashMap<Integer, Integer> counts = new HashMap<Integer, Integer>();

        for(int i = 0; i < commaSeparatedArr.length; i ++){
            if(!(pages.containsKey(Integer.parseInt(commaSeparatedArr[i])))){
                pages.put(Integer.parseInt(commaSeparatedArr[i]), new SimplePage(Integer.parseInt(commaSeparatedArr[i])));
                counts.put(Integer.parseInt(commaSeparatedArr[i]), 0);
            }
        }

        List<SimplePage> frames = new ArrayList<SimplePage>(this.numFrames);
        for (int i = 0; i < this.numFrames; i++){
            frames.add(null);
        }
        System.out.println("FRAME SIZE");
        System.out.println(frames.size());
        int frameidx = 0;
        
        for(int i = 0; i < commaSeparatedArr.length; i++){
            System.out.println("TIME: " + i);
            System.out.println("CURRENT PAGE ID: " + pages.get(Integer.parseInt(commaSeparatedArr[i])).getPageId());
            System.out.println("PAGE FAULTS: " + this.pageFaultCount);
            for(int k = 0; k < frames.size(); k++){
                if(frames.get(k) == null){
                    System.out.print("NULL ");
                }
                else{
                    System.out.print(frames.get(k).getPageId() + " ");

                }
            }
            System.out.println("");
            System.out.println("");
            if(frames.contains(pages.get(Integer.parseInt(commaSeparatedArr[i])))){
                counts.replace(Integer.parseInt(commaSeparatedArr[i]), i);
            }
            else{
                this.pageFaultCount ++;
                if (frames.contains(null)){
                    frames.set(frameidx, pages.get(Integer.parseInt(commaSeparatedArr[i])));
                    counts.replace(Integer.parseInt(commaSeparatedArr[i]), i);

                    frameidx +=1;
                }
                else{
                    System.out.println("ICI");
                    int lowestct = 0;
                    int nextframe;
                    for(int j=1; j<frames.size(); j++){
                       nextframe = j;
                       if (counts.get(frames.get(nextframe).getPageId())< counts.get(frames.get(lowestct).getPageId())){
                           lowestct = nextframe;
                       }
                    }
                    //System.out.println(lowestct);
                    //System.out.println(frames.size());
                    //System.out.println(frames.get(lowestct).getPageId());
                    frames.set(lowestct, pages.get(Integer.parseInt(commaSeparatedArr[i])));
                    counts.replace(Integer.parseInt(commaSeparatedArr[i]), i);
                }
            }
        }
    }
    public static void main(String[] args) {
        System.out.println("useLRU");
        MemoryScheduler instance3 = new MemoryScheduler(3);
        MemoryScheduler instance4 = new MemoryScheduler(4);
        String referenceString = "7,0,1,2,0,3,0,4,2,3,0,3,0,3,2,1,2,0,1,7,0,1";

        instance3.useOPT(referenceString);
        instance4.useOPT(referenceString);

    }

}
