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
public class Timer1 {

    public static void main(String[] args) {
        Random r = new Random();
        r.setSeed(654136331);
        int aantal = 500;
        //System.out.println(aantal);

        ArrayList<Short> a = new ArrayList<Short>();
        
        for (int k = 0; k < 10; k++) {
            System.gc();
            
            SuffixTree1 st1 = new SuffixTree1();
            SuffixTree2 st2 = new SuffixTree2();
            SuffixTree3 st3 = new SuffixTree3();
            long t1,t2;
            
            if(!a.isEmpty()){ 
                a.remove(a.size()-1);
            }
            for (int i = 0; i < aantal; i++) {
                a.add((short) (r.nextInt(500)));
            }
            ArrayList<Short> a2 = new ArrayList<Short>(a);
            ArrayList<Short> a3 = new ArrayList<Short>(a);
            
            t1 = System.currentTimeMillis();
            st1.construct(a);
            t2 = System.currentTimeMillis();
            System.out.println("Construct st1: Aantal: " + a.size() + " tijd:" + (t2-t1));
            
            t1 = System.currentTimeMillis();
            st2.construct(a2);
            t2 = System.currentTimeMillis();
            System.out.println("Construct st2: Aantal: " + a.size() + " tijd:" + (t2-t1));
            
            
            t1 = System.currentTimeMillis();
            st3.construct(a3);
            t2 = System.currentTimeMillis();
            System.out.println("Construct st3: Aantal: " + a.size() + " tijd:" + (t2-t1));
            
            
            //Locate testen
            
            t1 = System.currentTimeMillis();
            locateTest(st1,a);
            t2 = System.currentTimeMillis();
            System.out.println("Locate st1: Aantal: " + a.size() + " tijd:" + (t2-t1));
            
            t1 = System.currentTimeMillis();
            locateTest(st2,a);
            t2 = System.currentTimeMillis();
            System.out.println("Locate st1: Aantal: " + a.size() + " tijd:" + (t2-t1));
            
            t1 = System.currentTimeMillis();
            locateTest(st3,a);
            t2 = System.currentTimeMillis();
            System.out.println("Locate st1: Aantal: " + a.size() + " tijd:" + (t2-t1));
            
            //Contains testen
            
            t1 = System.currentTimeMillis();
            containsTest(st1,a);
            t2 = System.currentTimeMillis();
            System.out.println("Contains st1: Aantal: " + a.size() + " tijd:" + (t2-t1));
            
            t1 = System.currentTimeMillis();
            containsTest(st2,a);
            t2 = System.currentTimeMillis();
            System.out.println("Contains st2: Aantal: " + a.size() + " tijd:" + (t2-t1));
            
            t1 = System.currentTimeMillis();
            containsTest(st3,a);
            t2 = System.currentTimeMillis();
            System.out.println("Contains st3: Aantal: " + a.size() + " tijd:" + (t2-t1));
            
            //Count testen
            
            t1 = System.currentTimeMillis();
            countTest(st1,a);
            t2 = System.currentTimeMillis();
            System.out.println("Count st1: Aantal: " + a.size() + " tijd:" + (t2-t1));
            
            t1 = System.currentTimeMillis();
            countTest(st2,a);
            t2 = System.currentTimeMillis();
            System.out.println("Count st2: Aantal: " + a.size() + " tijd:" + (t2-t1));
            
            t1 = System.currentTimeMillis();
            countTest(st3,a);
            t2 = System.currentTimeMillis();
            System.out.println("Count st3: Aantal: " + a.size() + " tijd:" + (t2-t1));
            
            
        }
    }
    
    public static void locateTest(IntervalSearcher st, ArrayList<Short> a) {
        ArrayList<Short> b = new ArrayList<Short>();
        for (int i = 0; i < a.size(); i++) {
            b.add(a.get(i));
            for (int j = 1; j < a.size() - i; j++) {
                b.add(a.get((i + j)));

                st.locate(b);

            }
            b = new ArrayList<Short>();
        }
    }
    
    public static void containsTest(IntervalSearcher st, ArrayList<Short> a) {
        ArrayList<Short> b = new ArrayList<Short>();
        for (int i = 0; i < a.size(); i++) {
            b.add(a.get(i));
            for (int j = 1; j < a.size() - i; j++) {
                b.add(a.get((i + j)));

                st.contains(b);

            }
            b = new ArrayList<Short>();
        }
    }
    
    public static void countTest(IntervalSearcher st, ArrayList<Short> a) {
        ArrayList<Short> b = new ArrayList<Short>();
        for (int i = 0; i < a.size(); i++) {
            b.add(a.get(i));
            for (int j = 1; j < a.size() - i; j++) {
                b.add(a.get((i + j)));

                st.count(b);

            }
            b = new ArrayList<Short>();
        }
    }
    
}
