package suffixtree;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Michiel De Witte
 */
public class Test {
    
    public static void main(String[] args) {
        Random r = new Random();
        r.setSeed(654136331);
        int aantal = 500;
        System.out.println(aantal);
        ArrayList<Short> a = new ArrayList<Short>();
        for (int i = 0; i < aantal; i++) {
            a.add((short) (r.nextInt(50)));
            //a.add((short) (r.nextInt(65536) - 32768));
            //System.out.println(a.get(i));
        }
        
        SuffixTree3 st3 = new SuffixTree3();
        st3.construct(a);
        
        Node3 n;
        //System.out.println(a.get(500));
        
        for(TreeNode nd: st3.getRoot().getChildren()){
            if(a.get(((Node3) nd).getStartLabel()).equals((short) 28)){
                /*
                for (TreeNode nd2: nd.getChildren()){
                    System.out.println(((Node3) nd2).getStartLabel());
                }
                System.out.println("-------------");
                */
               printTree(a, (Node3) nd, "");
            }
        }
    }
    
    	public static void printTree(ArrayList<Short> arr, Node3 nd, String prefix){
		if (!nd.isRoot()){
                        for(int i=nd.getStartLabel();i<=nd.getEndLabel();i++){
                            prefix += arr.get(i) + ",";
                        }
		}
		System.out.println(prefix + "------------" + nd.getIndex());
		for(TreeNode chldnd: nd.getChildren()){
			printTree(arr, (Node3) chldnd, prefix);
		}
	}
}
