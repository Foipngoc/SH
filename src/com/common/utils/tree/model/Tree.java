package com.common.utils.tree.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 树的内存数据结构
 *
 * @param <E>
 */
public class Tree<E> {
    /**
     * 节点
     */
    private E node;

    /**
     * 子节点
     */
    private List<Tree<E>> children = new ArrayList<>();

    /**
     * 父节点
     */
    private List<Tree<E>> parents = new ArrayList<>();

    public E getNode() {
        return node;
    }

    public void setNode(E node) {
        this.node = node;
    }

    public List<Tree<E>> getParents() {
        return parents;
    }

    public void setParents(List<Tree<E>> parents) {
        this.parents = parents;
    }

    public List<Tree<E>> getChildren() {
        return children;
    }

    public void setChildren(List<Tree<E>> children) {
        this.children = children;
    }
}
