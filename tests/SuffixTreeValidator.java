/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import org.junit.*;
import static org.junit.Assert.*;
import org.junit.Test;
import suffixtree.*;

/**
 *
 * @author Michiel De Witte
 */
public class SuffixTreeValidator {
    
        public int aantal;
        public SuffixTree1 st1;
        public SuffixTree2 st2;
        public SuffixTree3 st3;
        public ArrayList arr;
    
    public SuffixTreeValidator() {
    }
        
    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
        Random r = new Random();
        //r.setSeed(654136331);
        aantal = 5000;
        ArrayList a = new ArrayList<Short>();
        for (int i = 0; i < aantal; i++) {
            a.add((short) (r.nextInt(50)));
            //a.add((short) (r.nextInt(65536) - 32768));
            //System.out.println(a.get(i));
        }
        
        ArrayList a2 = new ArrayList<Short>(a);
        ArrayList a3 = new ArrayList<Short>(a);
        
        arr = a;
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
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void minstens2kinderen(){
        for(TreeNode nd: st1.getRoot().getChildren()){
            subminstens2kinderen1(nd);
        }
        for(TreeNode nd: st2.getRoot().getChildren()){
            subminstens2kinderen2(nd);
        }
        for(TreeNode nd: st3.getRoot().getChildren()){
            subminstens2kinderen3(nd);
        }
    }
    
    public void subminstens2kinderen1(TreeNode nd){
        if ((!((Node1)nd).isLeaf() ) && (nd.getChildren().size() < 2)){
            assert(!((Node1)nd).getLabel().isEmpty());
            assert(false);
        }
        for(TreeNode ndd:nd.getChildren()){
            assert(!((Node1)ndd).getLabel().isEmpty());
            subminstens2kinderen1(ndd);
        }
    }
    
    public void subminstens2kinderen2(TreeNode nd){
        if ((!((Node2)nd).isLeaf() ) && (nd.getChildren().size() < 2)){
            assert(((Node2)nd).getStartLabel()<=((Node2)nd).getEndLabel());
            assert(false);
        }
        for(TreeNode ndd:nd.getChildren()){
            assert(((Node2)ndd).getStartLabel()<=((Node2)ndd).getEndLabel());
            subminstens2kinderen2(ndd);
        }
    }
    
    public void subminstens2kinderen3(TreeNode nd){
        if ((!((Node3)nd).isLeaf() ) && (nd.getChildren().size() < 2)){
            assert(((Node3)nd).getStartLabel()<=((Node3)nd).getEndLabel());
            assert(false);
        }
        for(TreeNode ndd:nd.getChildren()){
            assert(((Node3)ndd).getStartLabel()<=((Node3)ndd).getEndLabel());
            subminstens2kinderen3(ndd);
        }
    }
    
    @Test
    public void countLeafs(){
        assert(aantal == subCountLeafs1((Node1)st1.getRoot()));
        assert(aantal == subCountLeafs2((Node2)st2.getRoot()));
        assert(aantal == subCountLeafs3((Node3)st3.getRoot()));
    }
    
    public int subCountLeafs1(Node1 nd){
        if(nd.isLeaf()){
            return 1;
        }
        int i=0;
        for(Node1 ndd: nd.getChildren()){
            i+=subCountLeafs1(ndd);
        }
        return i;
    }
    
    public int subCountLeafs2(Node2 nd){
        if(nd.isLeaf()){
            return 1;
        }
        int i=0;
        for(TreeNode ndd: nd.getChildren()){
            i+=subCountLeafs2((Node2) ndd);
        }
        return i;
    }
    
    public int subCountLeafs3(Node3 nd){
        if(nd.isLeaf()){
            return 1;
        }
        int i=0;
        for(TreeNode ndd: nd.getChildren()){
            i+=subCountLeafs3((Node3)ndd);
        }
        return i;
    }
    
    @Test
    public void firstShortTest(){
        subFirstShortTest1((Node1)st1.getRoot());
        subFirstShortTest2((Node3)st3.getRoot());
    }
    
    public void subFirstShortTest1(Node1 nd){
        HashSet a = new HashSet();
        for(Node1 ndd: nd.getChildren()){
            a.add(ndd.getLabel().get(0));
        }
        assert(a.size() == nd.getChildren().size());
        for(Node1 ndd: nd.getChildren()){
            subFirstShortTest1(ndd);
        }
    }
    
    public void subFirstShortTest2(Node3 nd){
        HashSet a = new HashSet();
        for(TreeNode ndd: nd.getChildren()){
            a.add(arr.get(((Node3)ndd).getStartLabel()));
        }
        assert(a.size() == nd.getChildren().size());
        for(TreeNode ndd: nd.getChildren()){
            subFirstShortTest2((Node3) ndd);
        }
    }
}
