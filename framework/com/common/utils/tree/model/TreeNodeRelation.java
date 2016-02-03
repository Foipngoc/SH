package com.common.utils.tree.model;

/**
 * 树节点间关系
 *
 * @author DJ
 */
public class TreeNodeRelation {
    /**
     * 自增ID
     */
    private int id;
    /**
     * 父节点ID
     */
    private int pid;
    /**
     * 子节点ID
     */
    private int sid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }
}
