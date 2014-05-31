package suffixtree;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class SuffixTree1 implements IntervalSearcher {
	public Node1 tn;
        public int currentindex;
	public Set<Integer> templocate;
        public int size;
        
	public SuffixTree1(){
		tn = new Node1(true, false, null);
	}
	
	@Override
	public void construct(List<Short> sequence) {
                currentindex = sequence.size()-1;
		sequence.add(SENTINEL);
                size = sequence.size();
		for(int i=sequence.size()-2;i>-1;i--){
			add(sequence, i, tn);
		}
		
	}
	

	public void add(List<Short> sequence, int pos, Node1 nd){
		
		for(Node1 n : nd.getChildren()){    //alle nodes overlopen
			if (sequence.get(pos) == n.getLabel().get(0)){ // kijken als de node als eerste element in label dezelfde short bevat
				for (int i=1; i<n.getLabel().size(); i++){		// kijken hoe lang de match is
					if(sequence.get(pos+i) != n.getLabel().get(i)){	// als de shorts niet meer matchen
						
						ArrayList<Short> arr = new ArrayList<Short>();	//label aanmaken voor child voor de toe te voegen suffix
						for (int j=i; (pos+j)<sequence.size();j++){
							arr.add(sequence.get(pos+j));
						}
                                                Node1 noood = new Node1(false, true, arr);
                                                noood.setIndex(currentindex);
                                                currentindex--;
						
                                                
						ArrayList<Short> arr2 = new ArrayList<Short>();	//label aanmaken uit deellabel van de node waaraan men toevoegt
						for(int j=i; j<n.getLabel().size();j++){
							arr2.add(n.getLabel().get(j));
						}
                                                Node1 noood2 = new Node1(false, n.isLeaf(),arr2);
                                                noood2.setIndex(n.getIndex());
						
                                                /*
						ArrayList<Short> arr3 = new ArrayList<Short>();	//label van de oudernode verkorten
						for(int j=0; j<i;j++){
							arr3.add(n.getLabel().get(j));
						}
						n.setLabel(arr3);
						*/
                                                for(int j=n.getLabel().size()-1;j>=i;j--){  //label van de oudernode verkorten
                                                    n.getLabel().remove(j);
                                                }
                                                
                                                noood2.setChildren(n.getChildren());
                                                n.setChildren(new ArrayList<Node1>());
                                                n.addChild(noood2);
                                                n.addChild(noood);
                                                n.setLeaf(false);
                                                
						return;
					}
				}
				//de label van de node is korter dan de label van de sequentie en de label matcht de sequentie volledig
				//dus lengte van de label optellen bij de positie en terug de add-methode toepassen op de node waar de label matcht
				add(sequence, pos+n.getLabel().size(),n);
				return;
			}
		}
		//geen van de kinderen bevat iets gelijkaardigs bij de sequentie ==> nieuwe node met de ganse sequentie als label
		ArrayList<Short> arrey = new ArrayList<Short>();
		for(int i=pos;i<sequence.size();i++){
			arrey.add(sequence.get(i));
		}
                Node1 noood3 = new Node1(false,true,arrey);
                noood3.setIndex(currentindex);
                currentindex--;
		nd.addChild(noood3);
		return;
	}
	

    @Override
    public boolean contains(List<Short> query) {
        if (query.isEmpty()){
            return true;
        }
        for(Node1 nd: tn.getChildren()){
            if(nd.getLabel().get(0).equals(query.get(0))){
                if(query.size()-1 == 0){
                    return true;
                }
                    return subContains(query, nd, 1);
                }
            }
        return false;
    }

    private boolean subContains(List<Short> query, Node1 nd, int index) {
        if (nd.getLabel().size() == 1) { //node label is maar 1 groot

            for(Node1 nod: nd.getChildren()){
                if(nod.getLabel().get(0) == query.get(index)){
                    if(query.size()-1 == index){
                        return true;
                    } else {
                        return subContains(query, nod, index+1);
                    }
                }
            }

        }
        
        index--;    //om 1e effect van index++ in de for-loop tegen te gaan
        for (int i = 0 + 1; i <= nd.getLabel().size()-1; i++) {    //label overlopen vanaf 2e positie want 1e positie is al gechecked door hashmap
            index++;
            if (index < query.size() - 1) {
                if (!query.get(index).equals(nd.getLabel().get(i))) {
                    return false;
                }
            } else if (index == query.size() - 1) {
                if (!query.get(index).equals(nd.getLabel().get(i))) {
                    return false;
                }
                return true;
            }
        }

        //alle posities zijn gechecked van deze node en matchen allemaal
        index++;
        for(Node1 nd2:nd.getChildren()){
            if(nd2.getLabel().get(0) == query.get(index)){
                if(query.size() -1 == index){
                    return true;
                }
                return subContains(query,nd2,index+1);
            }
        }
        return false;
    }

    @Override
    public int count(List<Short> query) {
        if(query.isEmpty()){
            return this.size;
        }
        for(Node1 nd: tn.getChildren()){
            if(nd.getLabel().get(0) == query.get(0)){
                if (query.size() -1 == 0){
                    return searchChildren(nd);
                }
                return subCount(query,nd,1);
            }
        }
        return 0;
    }

    private int subCount(List<Short> query, Node1 nd, int index) {
        if (nd.getLabel().size() == 1) { //node label is maar 1 groot

            for (Node1 nod : nd.getChildren()) {
                if (nod.getLabel().get(0) == query.get(index)) {
                    if (query.size() - 1 == index) {
                        return searchChildren(nod);
                    } else {
                        return subCount(query, nod, index + 1);
                    }
                }
            }
            
        }
        
        
        index--;    //om 1e effect van index++ in de for-loop tegen te gaan
        for (int i = 0 + 1; i <= nd.getLabel().size()-1; i++) {    //label overlopen vanaf 2e positie want 1e positie is al gechecked door hashmap
            index++;
            if (index < query.size() - 1) {
                if (!query.get(index).equals(nd.getLabel().get(i))) {
                    return 0;
                }
            } else if (index == query.size() - 1) {
                if (!query.get(index).equals(nd.getLabel().get(i))) {
                    return 0;
                }
                return searchChildren(nd);
            }
        }

        
        //alle posities zijn gechecked van deze node en matchen allemaal
        index++;
        for(Node1 nd2:nd.getChildren()){
            if(nd2.getLabel().get(0) == query.get(index)){
                if(query.size() -1 == index){
                    return searchChildren(nd2);
                }
                return subCount(query,nd2,index+1);
            }
        }
        return 0;
    }

    private int searchChildren(Node1 nd) {
        if (nd.isLeaf()) {
            return 1;
        }
        int i = 0;
        for (TreeNode n : nd.getChildren()) {
            if (n.isLeaf()) {
                i++;
            } else {
                i += searchChildren((Node1) n);
            }
        }
        return i;
    }

    @Override
    public Set<Integer> locate(List<Short> query) {
        
        templocate = new HashSet<Integer>();
        if(query.isEmpty()){
            for(int i = 0;i<this.size;i++){
                templocate.add(i);
            }
            Set<Integer> res = templocate;
            templocate = null;
            return res;
        }
        
        for(Node1 nd: tn.getChildren()){
            if(nd.getLabel().get(0).equals(query.get(0))){
                if (query.size() -1 == 0){
                    //templocate.add(nd.getIndex());
                    locateChildren(nd);
                } else {
                    subLocate(query,nd,1);
                }
            }
        }

        Set<Integer> res = templocate;
        templocate = null;
        return res;
    }

    public void subLocate(List<Short> query, Node1 nd, int index) {
        if (nd.getLabel().size() == 1) { //node label is maar 1 groot

            for (Node1 nod : nd.getChildren()) {
                if (nod.getLabel().get(0).equals(query.get(index))) {
                    if (query.size() - 1 == index) {
                        locateChildren(nod);
                        return;
                    } else {
                        subLocate(query, nod, index + 1);
                        return;
                    }
                }
            }
            
        }

        index--;    //om 1e effect van index++ in de for-loop tegen te gaan
        for (int i = 0 + 1; i <= nd.getLabel().size()-1; i++) {    //label overlopen vanaf 2e positie want 1e positie is al gechecked door hashmap
            index++;
            if (index < query.size() - 1) {
                if (!query.get(index).equals(nd.getLabel().get(i))) {
                    return;
                }
            } else if (index == query.size() - 1) {
                if (!query.get(index).equals(nd.getLabel().get(i))) {
                    return;
                }
                locateChildren(nd);
                return;
            }
        }

        
        //alle posities zijn gechecked van deze node en matchen allemaal
        index++;
        for(Node1 nd2:nd.getChildren()){
            if(nd2.getLabel().get(0).equals(query.get(index))){
                if(query.size() -1 == index){
                    locateChildren(nd2);
                    return;
                }
                subLocate(query,nd2,index+1);
                return;
            }
        }
        return;
    }

    public void locateChildren(Node1 nd) {
        if (nd.getChildren().isEmpty()){
            templocate.add(nd.getIndex());
            return;
        }
        for (TreeNode n : nd.getChildren()) {
            if (n.isLeaf()) {
                templocate.add(n.getIndex());        //mss later uncommenten als het niet werkt
            } else {
                locateChildren((Node1) n);
            }
        }
    }

    @Override
    public TreeNode getRoot() {
        return tn;
    }

}
