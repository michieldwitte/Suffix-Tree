package suffixtree;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SuffixTree3 implements IntervalSearcher {

    public Node3 tn;
    public int currentindex;
    public List<Short> sequence;
    public Set<Integer> templocate;

    public SuffixTree3() {
        tn = new Node3(true, false, 0, 0);
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

    public void add(List<Short> sequence, int pos, Node3 nd) {      
        for(Node3 chld: nd.getChildrenNodes()){ //alle nodes overlopen
            if(sequence.get(pos).equals(sequence.get(chld.getStartLabel()))){ // kijken als de node als eerste element in label dezelfde short bevat
                for(int i=1; i<=(chld.getEndLabel()-chld.getStartLabel());i++){ // kijken hoe lang de match is
                    if (!sequence.get(pos + i).equals(sequence.get(chld.getStartLabel() + i))) { // als de shorts niet meer matchen
                        //chld is ouder (en eerste deel originele label)
                        //newChild is kind (en 2e deel originele label)
                        //newChild2 is kind (en rest van de toe te voegen suffix)
                        
                        Node3 newChild = new Node3(false, chld.isLeaf(), chld.getStartLabel() + i, chld.getEndLabel());
                        newChild.setChildren(chld.getChildrenNodes());
                        newChild.setIndex(chld.getIndex());

                        chld.setEndLabel(chld.getStartLabel() + i - 1);
                        chld.setLeaf(false);
                        chld.setChildren(new ArrayList<Node3>());
                        chld.addChild(newChild);

                        Node3 newChild2 = new Node3(false, true, pos + i, sequence.size() - 1);
                        chld.addChild(newChild2);
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
        }
        // geen enkele node past
        Node3 newnode = new Node3(false, true, pos, sequence.size() - 1);
        nd.addChild(newnode);
        newnode.setIndex(currentindex);
        currentindex--;
        return;
    }

    @Override
    public boolean contains(List<Short> query) {
        if (query.isEmpty()){
            return true;
        }
        for (Node3 chld : tn.getChildrenNodes()) {
            if (query.get(0).equals(sequence.get(chld.getStartLabel()))) {
                if (query.size() - 1 == 0) {
                    return true;
                }
                return subContains(query, chld, 1);
            }
        }
        return false;
    }

    private boolean subContains(List<Short> query, Node3 nd, int index) {
        if (nd.getStartLabel() == nd.getEndLabel()) { //node label is maar 1 groot
            for (Node3 nod : nd.getChildrenNodes()) {
                if (sequence.get(nod.getStartLabel()).equals(query.get(index))) {
                    if (query.size() - 1 == index) {
                        return true;
                    } else {
                        return subContains(query, nod, index + 1);
                    }
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
        for (Node3 nd2 : nd.getChildrenNodes()) {
            if (sequence.get(nd2.getStartLabel()).equals(query.get(index))) {
                if (query.size() - 1 == index) {
                    return true;
                }
                return subContains(query, nd2, index + 1);
            }
        }
        return false;
    }

    @Override
    public int count(List<Short> query) {
        if(query.isEmpty()){
            return sequence.size();
        }
        
        for (Node3 chld : tn.getChildrenNodes()) {
            if (query.get(0).equals(sequence.get(chld.getStartLabel()))) {
                if (query.size() - 1 == 0) {
                    return searchChildren(chld);
                }
                return subCount(query, chld, 1);
            }
        }
        return 0;
    }

    private int subCount(List<Short> query, Node3 nd, int index) {
        if (nd.getStartLabel() == nd.getEndLabel()) { //node label is maar 1 groot
            for (Node3 nod : nd.getChildrenNodes()) {
                if (sequence.get(nod.getStartLabel()).equals(query.get(index))) {
                    if (query.size() - 1 == index) {
                        return searchChildren(nod);
                    } else {
                        return subCount(query, nod, index + 1);
                    }
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
        for (Node3 nd2 : nd.getChildrenNodes()) {
            if (sequence.get(nd2.getStartLabel()).equals(query.get(index))) {
                if (query.size() - 1 == index) {
                    return searchChildren(nd2);
                }
                return subCount(query, nd2, index + 1);
            }
        }
        return 0;
    }

    private int searchChildren(Node3 nd) {
        if (nd.isLeaf()) {
            return 1;
        }
        int i = 0;
        for (TreeNode n : nd.getChildren()) {
            if (n.isLeaf()) {
                i++;
            } else {
                i += searchChildren((Node3) n);
            }
        }
        return i;
    }

    @Override
    public Set<Integer> locate(List<Short> query) {
        templocate = new HashSet<Integer>();
         if(query.isEmpty()){
            for(int i = 0;i<sequence.size();i++){
                templocate.add(i);
            }
            Set<Integer> res = templocate;
            templocate = null;
            return res;
        }
        
        for (Node3 chld : tn.getChildrenNodes()) {
            if (query.get(0).equals(sequence.get(chld.getStartLabel()))) {
                if (query.size() - 1 == 0) {
                    locateChildren(chld);
                } else {
                    subLocate(query, chld, 1);
                }
            }
        }
 
        Set<Integer> res = templocate;
        templocate = null;
        return res;
    }

    public void subLocate(List<Short> query, Node3 nd, int index) {
        if (nd.getStartLabel() == nd.getEndLabel()) { //node label is maar 1 groot
            for (Node3 nod : nd.getChildrenNodes()) {
                if (sequence.get(nod.getStartLabel()).equals(query.get(index))) {
                    if (query.size() - 1 == index) {
                        locateChildren(nod);
                        return;
                    } else {
                        subLocate(query,nod,index+1);
                        return;
                    }
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

        
        index++;
        for (Node3 nd2 : nd.getChildrenNodes()) {
            if (sequence.get(nd2.getStartLabel()).equals(query.get(index))) {
                if (query.size() - 1 == index) {
                    locateChildren(nd2);
                    return;
                }
                subLocate(query,nd2,index+1);
                return;
            }
        }
        return;
    }

    public void locateChildren(Node3 nd) {
        if (nd.isLeaf()) {
            templocate.add(nd.getIndex());
            return;
        }
        int i = 0;
        for (TreeNode n : nd.getChildren()) {
            if (n.isLeaf()) {
                templocate.add(n.getIndex());        //mss later uncommenten als het niet werkt
            } else {
                locateChildren((Node3) n);
            }
        }
    }

    @Override
    public TreeNode getRoot() {
        return tn;
    }

    public static void printTree(Node3 nd, String prefix, List<Short> sequence) {
        if (!nd.isRoot()) {
            for (int i = nd.getStartLabel(); i <= nd.getEndLabel(); i++) {
                prefix += sequence.get(i) + ",";
            }
        }
        System.out.println(prefix + "------------" + nd.getIndex());
        for (TreeNode t : nd.getChildren()) {
            printTree((Node3) t, prefix, sequence);
        }
    }
}
