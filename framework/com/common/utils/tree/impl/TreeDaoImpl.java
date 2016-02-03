package com.common.utils.tree.impl;

import com.common.base.BaseRecords;
import com.common.base.dao.impl.BaseDaoDB;
import com.common.base.dao.impl.querycondition.HQL;
import com.common.utils.tree.TreeDao;
import com.common.utils.tree.model.TreeNode;
import com.common.utils.tree.model.TreeNodeRelation;

/**
 * 以treenode/treenoderelation为模型的一种实现,支持treenode/treenoderelation的子类
 *
 * @author DJ
 */
public abstract class TreeDaoImpl<E extends TreeNode, R extends TreeNodeRelation>
        extends BaseDaoDB implements TreeDao<E, R> {
    public abstract Class<?> getEntryClass();

    public abstract Class<?> getEntryRelationClass();

    public BaseRecords<?> _findRootNodes() {
        return _findRootNodes(-1, -1);
    }

    public BaseRecords<?> _findRootNodes(int page, int rows) {
        String hql = "select a from ? a where a.id not in "
                + "(select r.sid from ? r)";

        return super.find(new HQL(hql, getEntryClass().getSimpleName(),
                getEntryRelationClass().getSimpleName()).setPage(page).setRows(rows));
    }

    public BaseRecords<?> _findRootNodes(int type) {
        return _findRootNodes(type, -1, -1);
    }

    public BaseRecords<?> _findRootNodes(int type, int page, int rows) {
        String hql = "select a from ? a where a.id not in "
                + "(select r.sid from ? r)" + " and a.type=?";

        return super.find(new HQL(hql, getEntryClass().getSimpleName(),
                getEntryRelationClass().getSimpleName(), type).setPage(page).setRows(rows));
    }

    public BaseRecords<?> _findRootNodes(String name) {
        return _findRootNodes(name, -1, -1);
    }

    public BaseRecords<?> _findRootNodes(String name, int page, int rows) {
        String hql = "select a from ? a where a.id not in "
                + "(select r.sid from ? r) and a.name like '%?%'";

        return super.find(new HQL(hql, getEntryClass().getSimpleName(),
                getEntryRelationClass().getSimpleName(), name).setPage(page).setRows(rows));
    }

    public BaseRecords<?> _findRootNodes(String name, int type) {
        return _findRootNodes(name, type, -1, -1);
    }

    public BaseRecords<?> _findRootNodes(String name, int type, int page,
                                         int rows) {
        String hql = "select a from ? a where a.id not in "
                + "(select r.sid from ? r)"
                + " and a.type=? and a.name like '%?%'";

        return super.find(new HQL(hql, getEntryClass().getSimpleName(),
                getEntryRelationClass().getSimpleName(), type, name).setPage(page).setRows(rows));
    }

    public BaseRecords<?> _findLeafNodes() {
        return _findLeafNodes(-1, -1);
    }

    public BaseRecords<?> _findLeafNodes(int page, int rows) {
        String hql = "select a from ? a where a.id not in "
                + "(select r.pid from ? r)";

        return super.find(new HQL(hql, getEntryClass().getSimpleName(),
                getEntryRelationClass().getSimpleName()).setPage(page).setRows(rows));
    }

    public BaseRecords<?> _findLeafNodes(int type) {
        return _findLeafNodes(type, -1, -1);
    }

    public BaseRecords<?> _findLeafNodes(int type, int page, int rows) {
        String hql = "select a from ? a where a.id not in "
                + "(select r.pid from ? r)" + " and a.type=?";

        return super.find(new HQL(hql, getEntryClass().getSimpleName(),
                getEntryRelationClass().getSimpleName(), type).setPage(page).setRows(rows));
    }

    public BaseRecords<?> _findLeafNodes(String name) {
        return _findLeafNodes(name, -1, -1);
    }

    public BaseRecords<?> _findLeafNodes(String name, int page, int rows) {
        String hql = "select a from ? a where a.id not in "
                + "(select r.pid from ? r) and a.name like '%?%'";

        return super.find(new HQL(hql, getEntryClass().getSimpleName(),
                getEntryRelationClass().getSimpleName(), name).setPage(page).setRows(rows));
    }

    public BaseRecords<?> _findLeafNodes(String name, int type) {
        return _findLeafNodes(name, type, -1, -1);
    }

    public BaseRecords<?> _findLeafNodes(String name, int type, int page,
                                         int rows) {
        String hql = "select a from ? a where a.id not in "
                + "(select r.pid from ? r)"
                + " and a.type=? and a.name like '%?%'";

        return super.find(new HQL(hql, getEntryClass().getSimpleName(),
                getEntryRelationClass().getSimpleName(), type, name).setPage(page).setRows(rows));
    }

    public BaseRecords<?> _findNodes() {
        return _findNodes(-1, -1);
    }

    public BaseRecords<?> _findNodes(int page, int rows) {
        String hql = "select a from ? a";

        return super.find(new HQL(hql, getEntryClass().getSimpleName()).setPage(page).setRows(rows));
    }

    public BaseRecords<?> _findNodes(int type) {
        return _findNodes(type, -1, -1);
    }

    public BaseRecords<?> _findNodes(int type, int page, int rows) {
        String hql = "select a from ? a where a.type=?";

        return super.find(new HQL(hql, getEntryClass().getSimpleName(), type).setPage(page).setRows(rows));
    }

    public BaseRecords<?> _findNodes(String name) {
        return _findNodes(name, -1, -1);
    }

    public BaseRecords<?> _findNodes(String name, int page, int rows) {
        String hql = "select a from ? a where a.name like '%?%'";

        return super.find(new HQL(hql, getEntryClass().getSimpleName(), name).setPage(page).setRows(rows));
    }

    public BaseRecords<?> _findNodes(String name, int type) {
        return _findNodes(name, type, -1, -1);
    }

    public BaseRecords<?> _findNodes(String name, int type, int page,
                                     int rows) {
        String hql = "select a from ? a where a.type=? and a.name like '%?%'";

        return super.find(new HQL(hql, getEntryClass().getSimpleName(), type,
                name).setPage(page).setRows(rows));
    }

    public BaseRecords<?> _findChildrenNodes(E node) {
        return _findChildrenNodes(node, -1, -1);
    }

    public BaseRecords<?> _findChildrenNodes(E node, int page, int rows) {
        String hql = "select a from ? b,? a " + "where b.sid=a.id and b.pid=?";

        return super.find(new HQL(hql, getEntryRelationClass().getSimpleName(),
                getEntryClass().getSimpleName(), node.getId()));
    }

    public BaseRecords<?> _findChildrenNodes(E node, int type) {
        return _findChildrenNodes(node, type, -1, -1);
    }

    public BaseRecords<?> _findChildrenNodes(E node, int type, int page,
                                             int rows) {
        String hql = "select a from ? b,? a "
                + "where b.sid=a.id and b.pid=? and a.type=?";

        return super.find(new HQL(hql, getEntryRelationClass().getSimpleName(),
                getEntryClass().getSimpleName(), node.getId(), type));
    }

    public E _addNode(E node) {
        super.save(node);
        return node;
    }

    public boolean _delNode(E node) {
        super.delete(new HQL("delete from ? where id=?", getEntryClass()
                .getSimpleName(), node.getId()));
        return true;
    }

    public E _updateNode(E node) {
        super.update(node);
        return node;
    }

    @SuppressWarnings("unchecked")
    public E _findNode(E node) {
        return (E) super.findUnique(new HQL("select a from ? a where a.id=?",
                getEntryClass().getSimpleName(), node.getId()));
    }

    @SuppressWarnings("unchecked")
    public boolean _bindTwoNode(E pnode, E snode) {
        try {
            R relation;
            relation = (R) getEntryRelationClass().newInstance();
            relation.setPid(pnode.getId());
            relation.setSid(snode.getId());
            super.save(relation);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    @Override
    public boolean _ifNodeEqual(E nodea, E nodeb) {
        return nodea.getId() == nodeb.getId();
    }

    public boolean _delbindTwoNode(E pnode, E snode) {
        super.delete(new HQL("delete from ? where pid=? and sid=?",
                getEntryRelationClass().getSimpleName(), pnode.getId(), snode
                .getId()));
        return true;
    }

    public boolean _delBindChildrenNodes(E node) {
        String hql = "delete from ? where pid=?";
        super.delete(new HQL(hql, getEntryRelationClass().getSimpleName(), node
                .getId()));
        return true;
    }

    public boolean _delBindParentNodes(E node) {
        String hql = "delete from ? where sid=?";
        super.delete(new HQL(hql, getEntryRelationClass().getSimpleName(), node
                .getId()));
        return true;
    }

    public boolean _ifTwoNodeHasRelation(E pnode, E snode) {
        @SuppressWarnings("unchecked")
        R relation = (R) super.findUnique(new HQL(
                "select a from ? a where a.pid=? and a.sid=?",
                getEntryRelationClass().getSimpleName(), pnode.getId(), snode
                .getId()));
        return relation == null ? false : true;
    }

    public BaseRecords<?> _findParentNodes(E node) {
        return _findParentNodes(node, -1, -1);
    }

    public BaseRecords<?> _findParentNodes(E node, int page, int rows) {
        String hql = "select a from ? b,? a " + "where b.pid=a.id and b.sid=?";

        return super.find(new HQL(hql, getEntryRelationClass().getSimpleName(),
                getEntryClass().getSimpleName(), node.getId()).setPage(page).setRows(rows));
    }

    public BaseRecords<?> _findParentNodes(E node, int type) {
        return _findParentNodes(node, type, -1, -1);
    }

    public BaseRecords<?> _findParentNodes(E node, int type, int page,
                                           int rows) {
        String hql = "select a from ? b,? a "
                + "where b.pid=a.id and b.sid=? and a.type=?";

        return super.find(new HQL(hql, getEntryRelationClass().getSimpleName(),
                getEntryClass().getSimpleName(), node.getId(), type).setPage(page).setRows(rows));
    }

    public BaseRecords<?> _findParentNodes(E node, String name) {
        return _findParentNodes(node, name, -1, -1);
    }

    public BaseRecords<?> _findParentNodes(E node, String name, int type) {
        return _findParentNodes(node, name, type, -1, -1);
    }

    public BaseRecords<?> _findParentNodes(E node, String name, int page,
                                           int rows) {
        String hql = "select a from ? b,? a "
                + "where b.pid=a.id and b.sid=? and a.name like '%?%'";

        return super.find(new HQL(hql, getEntryRelationClass().getSimpleName(),
                getEntryClass().getSimpleName(), node.getId(), name).setPage(page).setRows(rows));
    }

    public BaseRecords<?> _findParentNodes(E node, String name, int type,
                                           int page, int rows) {
        String hql = "select a from ? b,? a "
                + "where b.pid=a.id and b.sid=? and a.name like '%?%' and a.type=?";

        return super.find(new HQL(hql, getEntryRelationClass().getSimpleName(),
                getEntryClass().getSimpleName(), node.getId(), name, type).setPage(page).setRows(rows));
    }

    @Override
    public void _modifyNode(E node) {
        super.update(node);
    }

    @Override
    public BaseRecords<?> _findChildrenNodes(E node, String name) {
        return _findChildrenNodes(node, name, -1, -1);
    }

    @Override
    public BaseRecords<?> _findChildrenNodes(E node, String name, int type) {
        return _findChildrenNodes(node, name, type, -1, -1);
    }

    @Override
    public BaseRecords<?> _findChildrenNodes(E node, String name,
                                             int type, int page, int rows) {
        String hql = "select a from ? b,? a "
                + "where b.sid=a.id and b.pid=? and a.name like '%?%' and a.type=?";

        return super.find(new HQL(hql, getEntryRelationClass().getSimpleName(),
                getEntryClass().getSimpleName(), node.getId(), name, type));
    }

    @Override
    public BaseRecords<?> _findChildrenNodes(E node, String name,
                                             int page, int rows) {
        String hql = "select a from ? b,? a "
                + "where b.sid=a.id and b.pid=? and a.name like '%?%'";

        BaseRecords<?> result = super.find(new HQL(hql,
                getEntryRelationClass().getSimpleName(), getEntryClass()
                .getSimpleName(), node.getId(), name));
        return result;
    }
}
