package suffixtree;

import java.util.ArrayList;
import java.util.Collection;

public class Node1 implements TreeNode {

    public ArrayList<Short> label;
    public ArrayList<Node1> children;
    public boolean isroot;
    public boolean isleaf;
    public int index;

    public Node1(boolean isroot, boolean isleaf, ArrayList<Short> label) {
        this.isroot = isroot;
        this.isleaf = isleaf;
        this.label = label;
        this.children = new ArrayList<Node1>();
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
    public ArrayList<Node1> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<Node1> arr) {
        this.children = arr;
    }

    public void addChild(TreeNode treenode) {
        children.add((Node1) treenode);
    }

    public void setLabel(ArrayList<Short> label) {
        this.label = label;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setLeaf(boolean isleaf) {
        this.isleaf = isleaf;
    }

    public ArrayList<Short> getLabel() {
        return label;
    }
}
