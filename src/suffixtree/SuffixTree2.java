package suffixtree;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SuffixTree2 implements IntervalSearcher {

    public Node2 tn;
    public int currentindex;
    public List<Short> sequence;
    public Set<Integer> templocate;

    public SuffixTree2() {
        tn = new Node2(true, false, 0, 0);
    }

    @Override
    public void construct(List<Short> sequence) {
        this.sequence = sequence;
        currentindex = sequence.size() - 1;
        this.sequence.add(SENTINEL);
        for (int i = sequence.size() - 2; i > -1; i--) {

            add(sequence, i, tn);
        }
    }

    public void add(List<Short> sequence, int pos, Node2 nd) { //kijkt bij welke node hij de sequentie moet toevoegen        
        Node2 chld = nd.hashGet(sequence.get(pos));
        
        if (chld == null){  //als er geen kind is met als eerste element de gezochte short
            Node2 newnode = new Node2(false, true, pos, sequence.size()-1);
            nd.addChild(sequence.get(pos), newnode);
            newnode.setIndex(currentindex);
            currentindex--;
            return;
        }
        
        for(int i=1;i<=(chld.getEndLabel()-chld.getStartLabel());i++){ //alle shorts overige overlopen in de label van het kind
            if(!sequence.get(pos+i).equals(sequence.get(chld.getStartLabel()+i))){    //als er geen match is

                //chld is ouder (en eerste deel originele label)
                //newChild is kind (en 2e deel originele label)
                //newChild2 is kind (en rest van de toe te voegen suffix)
                
                Node2 newChild = new Node2(false,chld.isLeaf(),chld.getStartLabel()+i,chld.getEndLabel());
                newChild.setHashMap(chld.getHashMap());
                newChild.setIndex(chld.getIndex());
                
                chld.setEndLabel(chld.getStartLabel()+i-1);
                chld.setLeaf(false);                
                chld.setHashMap(new HashMap<Short,Node2>());
                chld.addChild(sequence.get(chld.getStartLabel()+i), newChild);
                
                Node2 newChild2 = new Node2(false,true,pos+i,sequence.size()-1);
                chld.addChild(sequence.get(pos+i), newChild2);
                newChild2.setIndex(currentindex);
                currentindex--;
                
                
                return;
            }
        }
        
	//de label van de node is korter dan de label van de sequentie en de label matcht de sequentie volledig
	//dus lengte van de label optellen bij de positie en terug de add-methode toepassen op de node waar de label matcht
        add(sequence,pos + (chld.getEndLabel()-chld.getStartLabel())+1,chld);
        return;
    }

    @Override
    public boolean contains(List<Short> query) {
        if (query.isEmpty()){
            return true;
        }
        Node2 nd = tn.hashGet(query.get(0));
        if (nd != null) {
            if (query.size() - 1 == 0) {
                return true;
            }
            return subContains(query, nd, 1);
        }
        return false;
    }

    private boolean subContains(List<Short> query, Node2 nd, int index) {
        if (nd.getStartLabel() == nd.getEndLabel()) { //node label is maar 1 groot

            Node2 nd2 = nd.hashGet(query.get(index));
            if (nd2 != null) {    //kijken als er een volgende node matcht en of dat de lengte dan voldaan wordt
                if (query.size() - 1 == index) {
                    return true;
                } else {
                    return subContains(query, nd2, index + 1);
                }
            }

        }
        index--;    //om 1e effect van index++ in de for-loop tegen te gaan
        for (int i = nd.getStartLabel() + 1; i <= nd.getEndLabel(); i++) {    //label overlopen vanaf 2e positie want 1e positie is al gechecked door hashmap
            index++;
            if (index < query.size() - 1) {
                if (!query.get(index).equals(sequence.get(i))) {
                    return false;
                }
            } else if (index == query.size() - 1) {
                if (!query.get(index).equals(sequence.get(i))) {
                    return false;
                }
                return true;
            }
        }

        //alle posities zijn gechecked van deze node en matchen allemaal
        index++;
        Node2 nd2 = nd.hashGet(query.get(index));
        if (nd2 != null) {
            if (query.size() - 1 == index) {
                return true;
            }
            return subContains(query, nd2, index + 1);
        }
        return false;
    }

    @Override
    public int count(List<Short> query) {
        if(query.isEmpty()){
            return sequence.size();
        }
        
        Node2 nd = tn.hashGet(query.get(0));
        if (nd != null) {
            if (query.size() - 1 == 0) {
                return searchChildren(nd);
            }
            return subCount(query, nd, 1);
        }
        return 0;
    }

    private int subCount(List<Short> query, Node2 nd, int index) {
        if (nd.getStartLabel() == nd.getEndLabel()) { //node label is maar 1 groot
            Node2 nd2 = nd.hashGet(query.get(index));
            if (nd2 != null) {    //kijken als er een volgende node matcht en of dat de lengte dan voldaan wordt
                if (query.size() - 1 == index) {
                    return searchChildren(nd2);
                } else {
                    return subCount(query, nd2, index + 1);
                }
            }
        }
        index--;    //om 1e effect van index++ in de for-loop tegen te gaan
        for (int i = nd.getStartLabel() + 1; i <= nd.getEndLabel(); i++) {    //label overlopen vanaf 2e positie want 1e positie is al gechecked door hashmap
            index++;
            if (index < query.size() - 1) {
                if (!query.get(index).equals(sequence.get(i))) {
                    return 0;
                }
            } else if (index == query.size() - 1) {
                if (!query.get(index).equals(sequence.get(i))) {
                    return 0;
                }
                return searchChildren(nd);
            }
        }

        //alle posities zijn gechecked van deze node en matchen allemaal
        index++;
        Node2 nd2 = nd.hashGet(query.get(index));
        if (nd2 != null) {
            if (query.size() - 1 == index) {
                return searchChildren(nd2);
            }
            return subCount(query, nd2, index + 1);
        }
        return 0;
    }

    private int searchChildren(Node2 nd) {
        if (nd.isLeaf()) {
            return 1;
        }
        int i = 0;
        for (TreeNode n : nd.getChildren()) {
            if (n.isLeaf()) {
                i++;
            } else {
                i += searchChildren((Node2) n);
            }
        }
        return i;
    }

    @Override
    public Set<Integer> locate(List<Short> query) {
        templocate = new HashSet<Integer>();
        if (query.isEmpty()) {
            for (int i = 0; i < sequence.size(); i++) {
                templocate.add(i);
            }
            Set<Integer> res = templocate;
            templocate = null;
            return res;
        }
        
        Node2 nd = tn.hashGet(query.get(0));
        if (nd != null) {
            if (query.size() - 1 == 0) {
                locateChildren(nd);
            } else {
                subLocate(query, nd, 1);
            }
        }

        Set<Integer> res = templocate;
        templocate = null;
        return res;
    }

    public void subLocate(List<Short> query, Node2 nd, int index) {
        if (nd.getStartLabel() == nd.getEndLabel()) { //node label is maar 1 groot
            Node2 nd2 = nd.hashGet(query.get(index));
            if (nd2 != null) {    //kijken als er een volgende node matcht en of dat de lengte dan voldaan wordt
                if (query.size() - 1 == index) {
                    locateChildren(nd2);
                    return;
                } else {
                    subLocate(query, nd2, index + 1);
                    return;
                }
            }
        }
        index--;    //om 1e effect van index++ in de for-loop tegen te gaan
        for (int i = nd.getStartLabel() + 1; i <= nd.getEndLabel(); i++) {    //label overlopen vanaf 2e positie want 1e positie is al gechecked door hashmap
            index++;
            if (index < query.size() - 1) {
                if (!query.get(index).equals(sequence.get(i))) {
                    return;
                }
            } else if (index == query.size() - 1) {
                if (!query.get(index).equals(sequence.get(i))) {
                    return;
                }
                locateChildren(nd);
                return;
            }
        }

        //alle posities zijn gechecked van deze node en matchen allemaal
        index++;
        Node2 nd2 = nd.hashGet(query.get(index));
        if (nd2 != null) {
            if (query.size() - 1 == index) {
                locateChildren(nd2);
                return;
            }
            subLocate(query, nd2, index + 1);
            return;
        }
        return;
    }

    public void locateChildren(Node2 nd) {
        if (nd.isLeaf()) {
            templocate.add(nd.getIndex());
            return;
        }
        int i = 0;
        for (TreeNode n : nd.getChildren()) {
            if (n.isLeaf()) {
                templocate.add(n.getIndex());        //mss later uncommenten als het niet werkt
            } else {
                locateChildren((Node2) n);
            }
        }
    }

    @Override
    public TreeNode getRoot() {
        return tn;
    }

    public static void printTree(Node2 nd, String prefix, List<Short> sequence) {
        if (!nd.isRoot()) {
            for (int i = nd.getStartLabel(); i <= nd.getEndLabel(); i++) {
                prefix += sequence.get(i) + ",";
            }
        }
        System.out.println(prefix + "------------" + nd.getIndex());
        for (TreeNode t : nd.getChildren()) {
            printTree((Node2) t, prefix, sequence);
        }
    }
}
