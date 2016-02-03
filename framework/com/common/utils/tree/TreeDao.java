package com.common.utils.tree;

import com.common.base.BaseRecords;

/**
 * @param <E> 节点实体
 * @param <R> 节点实体关系
 * @author DJ
 */
public interface TreeDao<E, R> {
    public BaseRecords<?> _findRootNodes();

    public BaseRecords<?> _findRootNodes(int page, int rows);

    public BaseRecords<?> _findRootNodes(int type);

    public BaseRecords<?> _findRootNodes(int type, int page, int rows);

    public BaseRecords<?> _findRootNodes(String name);

    public BaseRecords<?> _findRootNodes(String name, int page, int rows);

    public BaseRecords<?> _findRootNodes(String name, int type);

    public BaseRecords<?> _findRootNodes(String name, int type, int page, int rows);

    public BaseRecords<?> _findLeafNodes();

    public BaseRecords<?> _findLeafNodes(int page, int rows);

    public BaseRecords<?> _findLeafNodes(int type);

    public BaseRecords<?> _findLeafNodes(int type, int page, int rows);

    public BaseRecords<?> _findLeafNodes(String name);

    public BaseRecords<?> _findLeafNodes(String name, int page, int rows);

    public BaseRecords<?> _findLeafNodes(String name, int type);

    public BaseRecords<?> _findLeafNodes(String name, int type, int page, int rows);

    public BaseRecords<?> _findNodes();

    public BaseRecords<?> _findNodes(int page, int rows);

    public BaseRecords<?> _findNodes(int type);

    public BaseRecords<?> _findNodes(int type, int page, int rows);

    public BaseRecords<?> _findNodes(String name);

    public BaseRecords<?> _findNodes(String name, int page, int rows);

    public BaseRecords<?> _findNodes(String name, int type);

    public BaseRecords<?> _findNodes(String name, int type, int page, int rows);

    public BaseRecords<?> _findChildrenNodes(E node);

    public BaseRecords<?> _findChildrenNodes(E node, int page, int rows);

    public BaseRecords<?> _findChildrenNodes(E node, int type);

    public BaseRecords<?> _findChildrenNodes(E node, int type, int page, int rows);

    public BaseRecords<?> _findChildrenNodes(E node, String name);

    public BaseRecords<?> _findChildrenNodes(E node, String name, int type);

    public BaseRecords<?> _findChildrenNodes(E node, String name, int type, int page, int rows);

    public BaseRecords<?> _findChildrenNodes(E node, String name, int page, int rows);

    public E _addNode(E node);

    public boolean _delNode(E node);

    public E _updateNode(E node);

    public E _findNode(E node);

    public boolean _bindTwoNode(E pnode, E snode);

    public boolean _delbindTwoNode(E pnode, E snode);

    public boolean _delBindChildrenNodes(E node);

    public boolean _delBindParentNodes(E node);

    public boolean _ifTwoNodeHasRelation(E pnode, E snode);

    public BaseRecords<?> _findParentNodes(E node);

    public BaseRecords<?> _findParentNodes(E node, int page, int rows);

    public BaseRecords<?> _findParentNodes(E node, int type);

    public BaseRecords<?> _findParentNodes(E node, int type, int page, int rows);

    public BaseRecords<?> _findParentNodes(E node, String name);

    public BaseRecords<?> _findParentNodes(E node, String name, int type);

    public BaseRecords<?> _findParentNodes(E node, String name, int page, int rows);

    public BaseRecords<?> _findParentNodes(E node, String name, int type, int page, int rows);

    public boolean _ifNodeEqual(E nodea, E nodeb);

    public void _modifyNode(E node);
}
