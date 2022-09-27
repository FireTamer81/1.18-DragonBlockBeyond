package io.firetamer.dragonblockbeyond.util.library_candidates.gui_stuff.objects.V2_window_gui;

import io.firetamer.dragonblockbeyond.util.TreeNode;

import java.util.Iterator;

public class WindowNodeIter implements Iterator<WindowNode> {

    enum ProcessStages {
        ProcessParent, ProcessChildCurNode, ProcessChildSubNode
    }

    private WindowNode treeNode;

    public WindowNodeIter(WindowNode treeNode) {
        this.treeNode = treeNode;
        this.doNext = ProcessStages.ProcessParent;
        this.childrenCurNodeIter = treeNode.children.iterator();
    }

    private ProcessStages doNext;
    private WindowNode next;
    private Iterator<WindowNode> childrenCurNodeIter;
    private Iterator<WindowNode> childrenSubNodeIter;

    @Override
    public boolean hasNext() {

        if (this.doNext == ProcessStages.ProcessParent) {
            this.next = this.treeNode;
            this.doNext = ProcessStages.ProcessChildCurNode;
            return true;
        }

        if (this.doNext == ProcessStages.ProcessChildCurNode) {
            if (childrenCurNodeIter.hasNext()) {
                WindowNode childDirect = childrenCurNodeIter.next();
                childrenSubNodeIter = childDirect.iterator();
                this.doNext = ProcessStages.ProcessChildSubNode;
                return hasNext();
            }

            else {
                this.doNext = null;
                return false;
            }
        }

        if (this.doNext == ProcessStages.ProcessChildSubNode) {
            if (childrenSubNodeIter.hasNext()) {
                this.next = childrenSubNodeIter.next();
                return true;
            }
            else {
                this.next = null;
                this.doNext = ProcessStages.ProcessChildCurNode;
                return hasNext();
            }
        }

        return false;
    }

    @Override
    public WindowNode next() {
        return this.next;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

}
