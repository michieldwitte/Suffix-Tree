package TimeTests;

import java.util.ArrayList;
import java.util.Random;
import suffixtree.IntervalSearcher;
import suffixtree.SuffixTree1;
import suffixtree.SuffixTree2;
import suffixtree.SuffixTree3;

/**
 *
 * @author Michiel De Witte
 */
public class Timer2 {
    public static void main(String[] args) {
        
        testSuffixTree();
    }
    
    public static void testSuffixTree(){
        Random r = new Random();
        r.setSeed(654136331);
        int increment = 10000;
        ArrayList<Short> a = new ArrayList<Short>();
        long t1,t2;
        ArrayList<Short> b;
        
        for (int i = 0; i < 20; i++) {
            SuffixTree1 st = new SuffixTree1();
            System.gc();
            
            if(!a.isEmpty()){ 
                a.remove(a.size()-1);
            }
            for (int j = 0; j < increment; j++) {
                a.add((short) (r.nextInt(50)));
            }
            b = new ArrayList<Short>();
            for(int j = (a.size()/4)*2;j<(a.size()/4)*3;j++){
                b.add(a.get(j));
            }
            
            
            t1 = System.currentTimeMillis();
            st.construct(a);
            t2 = System.currentTimeMillis();
            System.out.println("Construct: Aantal: " + a.size() + " tijd:" + (t2-t1));
            
            t1 = System.currentTimeMillis();
            st.locate(b);
            t2 = System.currentTimeMillis();
            System.out.println("Locate: Aantal: " + a.size() + " tijd:" + (t2-t1));
            
            t1 = System.currentTimeMillis();
            st.count(b);
            t2 = System.currentTimeMillis();
            System.out.println("Count: Aantal: " + a.size() + " tijd:" + (t2-t1));
            
            t1 = System.currentTimeMillis();
            st.contains(b);
            t2 = System.currentTimeMillis();
            System.out.println("Contains: Aantal: " + a.size() + " tijd:" + (t2-t1));
        } 
    }
}
