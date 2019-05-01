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
        throw new UnsupportedOperationException();
    }

    public void useLRU(String referenceString) {
        System.out.println("BEGIN");
        String[] commaSeparatedArr = referenceString.split("\\s*,\\s*");
        for(int i = 0; i<commaSeparatedArr.length;i++){
            System.out.println(commaSeparatedArr[i]);
        }
        System.out.println("END");
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
            if(frames.contains(pages.get(Integer.parseInt(commaSeparatedArr[i])))){
                counts.replace(Integer.parseInt(commaSeparatedArr[i]), i);
            }
            else{
                this.pageFaultCount ++;
                if ((frames.contains(null))&&(frameidx<frames.size())){
                    frames.set(frameidx, pages.get(Integer.parseInt(commaSeparatedArr[i])));
                    counts.replace(Integer.parseInt(commaSeparatedArr[i]), i);

                    frameidx +=1;
                }
                else{
                    int lowestct = 0;
                    int nextframe;
                    for(int j=1; j<frames.size(); j++){
                       nextframe = j;
                       if (counts.get(frames.get(nextframe).getPageId())< counts.get(frames.get(lowestct).getPageId())){
                           lowestct = nextframe;
                       }
                    }
                    System.out.println(lowestct);
                    System.out.println(frames.size());
                    System.out.println(frames.get(lowestct).getPageId());
                    frames.set(lowestct, pages.get(Integer.parseInt(commaSeparatedArr[i])));               
                }
            }
        }
    }

}
