package io.firetamer.dragonblockbeyond.util.library_candidates.gui_stuff.objects.V2_window_gui;

import io.firetamer.dragonblockbeyond.util.TreeNodeIter;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class WindowNode implements Iterable<WindowNode> {

    public WindowNodeData data;
    public WindowNode parent;
    public List<WindowNode> children;

    public boolean isRoot() { return parent == null; }

    public boolean isLeaf() { return children.size() == 0; }

    private List<WindowNode> elementsIndex;

    public WindowNode(WindowNodeData data) {
        this.data = data;
        this.children = new LinkedList<>();
        this.elementsIndex = new LinkedList<>();
        this.elementsIndex.add(this);
    }

    public WindowNode addChild(WindowNodeData child) {
        WindowNode childNode = new WindowNode(child);
        childNode.parent = this;
        this.children.add(childNode);
        this.registerChildForSearch(childNode);
        return childNode;
    }

    public int getLevel() {
        if (this.isRoot())
            return 0;
        else
            return parent.getLevel() + 1;
    }

    private void registerChildForSearch(WindowNode node) {
        elementsIndex.add(node);
        if (parent != null)
            parent.registerChildForSearch(node);
    }

    public WindowNode findWindowNode(Comparable cmp) {
        for (WindowNode element : this.elementsIndex) {
            WindowNodeData elData = element.data;
            if (cmp.compareTo(elData) == 0)
                return element;
        }

        return null;
    }

    @Override
    public String toString() {
        return data != null ? data.toString() : "[data null]";
    }

    @Override
    public Iterator<WindowNode> iterator() {
        WindowNodeIter iter = new WindowNodeIter(this);
        return iter;
    }

}
