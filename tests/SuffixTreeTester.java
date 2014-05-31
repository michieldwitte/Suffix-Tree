/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.Random;
import org.junit.*;
import static org.junit.Assert.*;
import suffixtree.AltIntervalSearcher;
import suffixtree.SuffixTree1;
import suffixtree.SuffixTree2;
import suffixtree.SuffixTree3;

/**
 *
 * @author Michiel De Witte
 */
public class SuffixTreeTester {

    public SuffixTree1 st1;
    public SuffixTree2 st2;
    public SuffixTree3 st3;
    public ArrayList<Short> a;
    public AltIntervalSearcher ais;

    public SuffixTreeTester() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        
    }

    @Before
    public void setUp() {
        
        a = new ArrayList<Short>();
        a.add((short) -5);
        a.add((short) 0);
        a.add((short) -5);
        a.add((short) 99);
        a.add((short) -5);
        a.add((short) 0);
        a.add((short) -5);
        a.add((short) 27);
        a.add((short) -5);
        a.add((short) 99);
        a.add((short) 27);
        
        /*
        Random r = new Random();
        r.setSeed(654136331);
        int aantal = 500;
        System.out.println(aantal);
        a = new ArrayList<Short>();
        for (int i = 0; i < aantal; i++) {
            a.add((short) (r.nextInt(50)));
            //a.add((short) (r.nextInt(65536) - 32768));
            //System.out.println(a.get(i));
        }
        */
        ArrayList<Short> a2 = new ArrayList<Short>(a);
        ArrayList<Short> a3 = new ArrayList<Short>(a);
        
        ais = new AltIntervalSearcher();
        ais.construct(a);
        
        st1 = new SuffixTree1();
        st1.construct(a);
        
        st2 = new SuffixTree2();
        st2.construct(a2);
        st3 = new SuffixTree3();
        st3.construct(a3);
    }

    @After
    public void tearDown() {
    }
    
    @Test
    public void failgeval(){
        ArrayList<Short> b = new ArrayList<>();
        //b.add((short) -5);
        //b.add((short)0);
        
        System.out.println(st1.contains(b) + " : " + ais.contains(b)); 
        System.out.println(st2.contains(b) + " : " + ais.contains(b)); 
        System.out.println(st3.contains(b) + " : " + ais.contains(b));
        
        System.out.println(st1.count(b) + " : " + ais.count(b)); 
        System.out.println(st2.count(b) + " : " + ais.count(b)); 
        System.out.println(st3.count(b) + " : " + ais.count(b));      
        
        System.out.println(st1.locate(b) + " : " + ais.locate(b)); 
        System.out.println(st2.locate(b) + " : " + ais.locate(b)); 
        System.out.println(st3.locate(b) + " : " + ais.locate(b));         
    }

    
    //@Test
    public void deelLijst(){
        ArrayList<Short> b = new ArrayList<>();
        for(int i=0;i<a.size();i++){
            System.out.println((i*100/500) + "% ready");
            b.add(a.get(i));
            for(int j=1;j<a.size()-i;j++){
                b.add(a.get((i+j)));
                //if(ais.contains(b) != st.contains(b)){
                //    System.out.println(st.contains(b) +"  "+ ais.contains(b));
                //    System.out.println(b);
                //}
                assert(ais.contains(b) == st1.contains(b));
                assert(ais.contains(b) == st2.contains(b));
                /*
                if (ais.contains(b) != st3.contains(b)){
                    System.out.println(ais.contains(b)+":ais " + st3.contains(b) + ":st");
                    System.out.println(b);
                }
                */
                
                assert(ais.contains(b) == st3.contains(b));
                //System.out.println("st:"+st.count(b)+" ais:"+ais.count(b));
                //if (ais.count(b) != st.count(b)){
                //    System.out.println(ais.count(b)+":ais " + st.count(b) + ":st");
                //    System.out.println(b);
                //}
                assert(ais.count(b) == st1.count(b));
                assert(ais.count(b) == st2.count(b));
                assert(ais.count(b) == st3.count(b));
                
                //if(ais.locate(b) != st.locate(b)){
                //    System.out.println("st:"+st.locate(b)+" ais:"+ais.locate(b));
                //}
                
                assert(ais.locate(b).containsAll(st1.locate(b)));
                assert(st1.locate(b).containsAll(ais.locate(b)));
                
                assert(ais.locate(b).containsAll(st2.locate(b)));
                assert(st2.locate(b).containsAll(ais.locate(b)));
                
                assert(ais.locate(b).containsAll(st3.locate(b)));
                assert(st3.locate(b).containsAll(ais.locate(b)));
                /*
                if(!st3.locate(b).containsAll(ais.locate(b))){
                    System.out.println(st3.locate(b));
                    System.out.println(ais.locate(b));
                    System.out.println(b);
                }
                */
            }
            b = new ArrayList<Short>();
        }
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
