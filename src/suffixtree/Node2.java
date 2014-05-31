package suffixtree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class Node2 implements TreeNode {

    public int start;
    public int end;
    public HashMap<Short, Node2> children;
    public boolean isroot;
    public boolean isleaf;
    public int index;

    public Node2(boolean isroot, boolean isleaf, int start, int end) {
        this.isroot = isroot;
        this.isleaf = isleaf;
        this.start = start;
        this.end = end;
        this.children = new HashMap<Short, Node2>();
    }

    @Override
    public boolean isRoot() {
        return isroot;
    }

    @Override
    public boolean isLeaf() {
        return isleaf;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public <T extends TreeNode> Collection<T> getChildren() {
    //public ArrayList<Node> getChildren() {
        return (Collection) children.values();
    }

    public void addChild(Short shrt, Node2 treenode) {
        children.put(shrt, treenode);
    }

    public void setLabel(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setLeaf(boolean isleaf) {
        this.isleaf = isleaf;
    }

    public int getStartLabel() {
        return start;
    }

    public void setEndLabel(int end) {
        this.end = end;
    }
    public int getEndLabel() {
        return end;
    }
    
    public Node2 hashGet(Short shrt){
        return children.get(shrt);
    }
    
    public HashMap<Short,Node2> getHashMap(){
        return children;
    }
    
    public void setHashMap(HashMap<Short,Node2> hm){
        this.children = hm;
    }
}
