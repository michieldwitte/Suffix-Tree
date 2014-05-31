package TimeTests;

import java.util.ArrayList;
import java.util.Random;
import suffixtree.*;

/**
 *
 * @author Michiel De Witte
 */
public class Timer3 {
    public static void main(String[] args) {
        int j;
        for(int i=0;i<=20;i++){
            j=i*500;
            //constructTest(j);
            ContainsTest(j);
            //LocateTest(j);
            //CountTest(j);
        }
    }
    
    public static void constructTest(int aantal){

        AltIntervalSearcher ais = new AltIntervalSearcher();
        SuffixTree1 st1 = new SuffixTree1();
        SuffixTree2 st2 = new SuffixTree2();
        SuffixTree3 st3 = new SuffixTree3();
        long t1,t2;
        ArrayList a = new ArrayList<Short>();
        
        //groot alfabet == 50000
        //klein alfabet == 50
        
        Random r = new Random();
        //r.setSeed(654136331);
        //int aantal = 6500;
        //System.out.println(aantal);
        a = new ArrayList<Short>();
        for (int i = 0; i < aantal; i++) {
            a.add((short) (r.nextInt(50)));
            //a.add((short) (r.nextInt(65536) - 32768));
            //System.out.println(a.get(i));
        }
        
        t1 = System.currentTimeMillis();
        st1.construct(a);
        t2 = System.currentTimeMillis();
        System.out.print(aantal + ":" + (t2-t1));
        a.remove(a.size()-1);
        System.gc();
        
        t1 = System.currentTimeMillis();
        st1.construct(a);
        t2 = System.currentTimeMillis();
        System.out.print(","+(t2-t1));
        a.remove(a.size()-1);
        System.gc();
        
        t1 = System.currentTimeMillis();
        st1.construct(a);
        t2 = System.currentTimeMillis();
        System.out.println(","+(t2-t1));
        System.gc();
        
    }
    
    public static void CountTest(int aantal){
        AltIntervalSearcher ais = new AltIntervalSearcher();
        SuffixTree1 st1 = new SuffixTree1();
        SuffixTree2 st2 = new SuffixTree2();
        SuffixTree3 st3 = new SuffixTree3();
        long t1,t2;
        ArrayList a = new ArrayList<Short>();
        
        Random r = new Random();
        //r.setSeed(654136331);
        //int aantal = 6500;
        //System.out.println(aantal);
        a = new ArrayList<Short>();
        ArrayList b = new ArrayList<Short>();
        for (int i = 0; i < aantal; i++) {
            a.add((short) (r.nextInt(50)));
            //a.add((short) (r.nextInt(65536) - 32768));
            //System.out.println(a.get(i));
        }
        
        ais.construct(a);
        t1 = System.currentTimeMillis();
        for(int i=0;i<a.size();i++){
            b.add(a.get(i));
            ais.count(b);
	}
        t2 = System.currentTimeMillis();
        System.out.print(aantal + ":" + (t2-t1));
        b = new ArrayList<Short>();
        System.gc();
        
        
        st1.construct(a);
        t1 = System.currentTimeMillis();
        for(int i=0;i<a.size();i++){
            b.add(a.get(i));
            st1.count(b);
	}
        t2 = System.currentTimeMillis();
        System.out.print(","+(t2-t1));
        b = new ArrayList<Short>();
        a.remove(a.size()-1);
        System.gc();
        
        st2.construct(a);
        t1 = System.currentTimeMillis();
        for(int i=0;i<a.size();i++){
            b.add(a.get(i));
            st2.count(b);
	}
        t2 = System.currentTimeMillis();
        System.out.print(","+(t2-t1));
        b = new ArrayList<Short>();
        a.remove(a.size()-1);
        System.gc();
        
        st3.construct(a);
        t1 = System.currentTimeMillis();
        for(int i=0;i<a.size();i++){
            b.add(a.get(i));
            st3.count(b);
	}
        t2 = System.currentTimeMillis();
        System.out.println(","+(t2-t1));
        b = new ArrayList<Short>();
        a.remove(a.size()-1);
        System.gc();
        
        
    }
    
        public static void ContainsTest(int aantal){
        AltIntervalSearcher ais = new AltIntervalSearcher();
        SuffixTree1 st1 = new SuffixTree1();
        SuffixTree2 st2 = new SuffixTree2();
        SuffixTree3 st3 = new SuffixTree3();
        long t1,t2;
        ArrayList a = new ArrayList<Short>();
        
        Random r = new Random();
        //r.setSeed(654136331);
        //int aantal = 6500;
        //System.out.println(aantal);
        a = new ArrayList<Short>();
        ArrayList b = new ArrayList<Short>();
        for (int i = 0; i < aantal; i++) {
            a.add((short) (r.nextInt(50000)));
            //a.add((short) (r.nextInt(65536) - 32768));
            //System.out.println(a.get(i));
        }
        
        ais.construct(a);
        t1 = System.currentTimeMillis();
        for(int i=0;i<a.size();i++){
            b.add(a.get(i));
            ais.contains(b);
	}
        t2 = System.currentTimeMillis();
        System.out.print(aantal + ":" + (t2-t1));
        b = new ArrayList<Short>();
        System.gc();
        
        
        st1.construct(a);
        t1 = System.currentTimeMillis();
        for(int i=0;i<a.size();i++){
            b.add(a.get(i));
            st1.contains(b);
	}
        t2 = System.currentTimeMillis();
        System.out.print(","+(t2-t1));
        b = new ArrayList<Short>();
        a.remove(a.size()-1);
        System.gc();
        
        st2.construct(a);
        t1 = System.currentTimeMillis();
        for(int i=0;i<a.size();i++){
            b.add(a.get(i));
            st2.contains(b);
	}
        t2 = System.currentTimeMillis();
        System.out.print(","+(t2-t1));
        b = new ArrayList<Short>();
        a.remove(a.size()-1);
        System.gc();
        
        st3.construct(a);
        t1 = System.currentTimeMillis();
        for(int i=0;i<a.size();i++){
            b.add(a.get(i));
            st3.contains(b);
	}
        t2 = System.currentTimeMillis();
        System.out.println(","+(t2-t1));
        b = new ArrayList<Short>();
        a.remove(a.size()-1);
        System.gc();
        
    }
        
        public static void LocateTest(int aantal){
        AltIntervalSearcher ais = new AltIntervalSearcher();
        SuffixTree1 st1 = new SuffixTree1();
        SuffixTree2 st2 = new SuffixTree2();
        SuffixTree3 st3 = new SuffixTree3();
        long t1,t2;
        ArrayList a = new ArrayList<Short>();
        
        Random r = new Random();
        //r.setSeed(654136331);
        //int aantal = 6500;
        //System.out.println(aantal);
        a = new ArrayList<Short>();
        ArrayList b = new ArrayList<Short>();
        for (int i = 0; i < aantal; i++) {
            a.add((short) (r.nextInt(50000)));
            //a.add((short) (r.nextInt(65536) - 32768));
            //System.out.println(a.get(i));
        }
        
        ais.construct(a);
        t1 = System.currentTimeMillis();
        for(int i=0;i<a.size();i++){
            b.add(a.get(i));
            ais.locate(b);
	}
        t2 = System.currentTimeMillis();
        System.out.print(aantal + ":" + (t2-t1));
        b = new ArrayList<Short>();
        System.gc();
        
        
        st1.construct(a);
        t1 = System.currentTimeMillis();
        for(int i=0;i<a.size();i++){
            b.add(a.get(i));
            st1.locate(b);
	}
        t2 = System.currentTimeMillis();
        System.out.print(","+(t2-t1));
        b = new ArrayList<Short>();
        a.remove(a.size()-1);
        System.gc();
        
        st2.construct(a);
        t1 = System.currentTimeMillis();
        for(int i=0;i<a.size();i++){
            b.add(a.get(i));
            st2.locate(b);
	}
        t2 = System.currentTimeMillis();
        System.out.print(","+(t2-t1));
        b = new ArrayList<Short>();
        a.remove(a.size()-1);
        System.gc();
        
        st3.construct(a);
        t1 = System.currentTimeMillis();
        for(int i=0;i<a.size();i++){
            b.add(a.get(i));
            st3.locate(b);
	}
        t2 = System.currentTimeMillis();
        System.out.println(","+(t2-t1));
        b = new ArrayList<Short>();
        a.remove(a.size()-1);
        System.gc();
    }
}
