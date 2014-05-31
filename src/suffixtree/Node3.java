package suffixtree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class Node3 implements TreeNode {

    public int start;
    public int end;
    public ArrayList<Node3> children;
    public boolean isroot;
    public boolean isleaf;
    public int index;

    public Node3(boolean isroot, boolean isleaf, int start, int end) {
        this.isroot = isroot;
        this.isleaf = isleaf;
        this.start = start;
        this.end = end;
        this.children = new ArrayList<Node3>();
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
        return (Collection) children;
    }
    
    public ArrayList<Node3> getChildrenNodes(){
        return children;
    }

    public void addChild(Node3 treenode) {
        children.add(treenode);
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
    
    public void setChildren(ArrayList<Node3> nd){
        this.children = nd;
    }
}
