package com.common.utils.tree.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.common.base.BaseRecords;
import com.common.base.service.BaseService;
import com.common.utils.tree.TreeDao;
import com.common.utils.tree.TreeService;
import com.common.utils.tree.model.Tree;
import com.common.utils.tree.model.TreeNode;
import com.common.utils.tree.model.TreeNodeRelation;

/**
 * 以treenode/treenoderelation为模型的一种实现,支持treenode/treenoderelation的子类
 *
 * @author DJ
 */
public abstract class TreeServiceImpl<E extends TreeNode, R extends TreeNodeRelation>
        extends BaseService implements TreeService<E, R> {

    /**
     * 添加节点,并绑定为已有节点的子节点
     */
    @Override
    public E addBindChildrenNode(E pnode, E newnode) {
        E nodenew = addNode(newnode);
        bindChildrenNode(pnode, nodenew);
        return nodenew;
    }

    /**
     * 添加节点,并绑定为已有节点的父节点
     */
    @Override
    public E addBindParentNode(E snode, E newnode) {
        E nodenew = addNode(newnode);
        bindParentNode(snode, nodenew);
        return nodenew;
    }

    /**
     * 将某节点sid绑定节点pid的子节点
     */
    @Override
    public boolean bindChildrenNode(E pnode, E snode) {
        if (ifNodeEqual(pnode, snode)) {
            return false;
        }

        // 是否已有关联
        if (ifTwoNodeHasRelation(pnode, snode)
                || ifTwoNodeHasRelation(snode, pnode)) {
            return false;
        }

        // 防止节点回路,检查子节点的子节点列表中是否有父节点
        List<E> subpermsubs = findChildrenNodes_r(snode);
        for (int i = 0; i < subpermsubs.size(); i++) {
            if (ifNodeEqual(subpermsubs.get(i), pnode)) {
                return false;
            }
        }

        return bindTwoNode(pnode, snode);
    }

    /**
     * 将某节点pid绑定节点sid的父节点
     */
    @Override
    public boolean bindParentNode(E snode, E pnode) {
        return bindChildrenNode(pnode, snode);
    }

    /**
     * 删除子节点sid与父节点pid的绑定关系
     */
    @Override
    public void delBindParentNode(E snode, E pnode) {
        delBindChildrenNode(pnode, snode);
    }

    /**
     * 删除节点id与所有父子节点的绑定关系
     */
    @Override
    public void delBindNodes(E node) {
        delBindParentNodes(node);
        delBindChildrenNodes(node);
    }

    /**
     * 判断节点是否为叶子节点
     */
    @Override
    public boolean ifNodeLeaf(E node) {
        BaseRecords<E> children = this.findChildrenNodes(node);
        if (children == null || children.getData() == null
                || children.getData().size() <= 0)
            return true;
        return false;
    }

    /**
     * 判断节点是否为根节点
     */
    @Override
    public boolean ifNodeRoot(E node) {
        BaseRecords<E> parent = this.findParentNodes(node);
        if (parent == null || parent.getData() == null
                || parent.getData().size() <= 0)
            return true;
        return false;
    }

    /****************************************************/

    /**
     * 获取treedao的实现
     */
    public abstract TreeDao<E, R> getTreeDao();

    /**
     * 判断两个节点是否相同
     */
    @Override
    public boolean ifNodeEqual(E nodea, E nodeb) {
        return getTreeDao()._ifNodeEqual(nodea, nodeb);
    }

    /**
     * 判断两个节点是否有父子关联
     */
    @Override
    public boolean ifTwoNodeHasRelation(E pnode, E snode) {
        return getTreeDao()._ifTwoNodeHasRelation(pnode, snode);
    }

    /**
     * 绑定两个的父子关系
     */
    @Override
    public boolean bindTwoNode(E pnode, E snode) {
        return getTreeDao()._bindTwoNode(pnode, snode);
    }

    /**
     * 添加节点
     */
    @Override
    public E addNode(E node) {
        return getTreeDao()._addNode(node);
    }

    /**
     * 删除父节点pid与子节点sid的绑定关系
     */
    @Override
    public void delBindChildrenNode(E pnode, E snode) {
        getTreeDao()._delbindTwoNode(pnode, snode);
    }

    /**
     * 删除节点id与所有子节点的绑定关系
     */
    @Override
    public void delBindChildrenNodes(E node) {
        getTreeDao()._delBindChildrenNodes(node);
    }

    /**
     * 删除节点id与所有父节点的绑定关系
     */
    @Override
    public void delBindParentNodes(E node) {
        getTreeDao()._delBindParentNodes(node);
    }

    /**
     * 修改节点内容
     */
    @Override
    public void modifyNode(E node) {
        getTreeDao()._modifyNode(node);
    }

    /**
     * 删除节点，同时删除节点与父子节点的绑定关系
     */
    @Override
    public void delNode(E node) {
        getTreeDao()._delNode(node);
        delBindNodes(node);
    }

    /**
     * 删除节点，同时删除所有的子节点及关联关系
     */
    @Override
    public void delNode_r(E node) {
        List<E> chiledsall = findChildrenNodes_r(node);
        for (int i = 0; i < chiledsall.size(); i++) {
            delNode(chiledsall.get(i));
        }
        delNode(node);
    }

    /**
     * 查询某一节点
     */
    @Override
    public E findNode(E node) {
        return (E) getTreeDao()._findNode(node);
    }

    /**
     * 查询所有根节点列表
     */
    @SuppressWarnings("unchecked")
    @Override
    public BaseRecords<E> findRootNodes() {
        return (BaseRecords<E>) getTreeDao()._findRootNodes();
    }

    /**
     * 分页查询所有根节点列表
     */
    @SuppressWarnings("unchecked")
    @Override
    public BaseRecords<E> findRootNodes(int page, int rows) {
        return (BaseRecords<E>) getTreeDao()._findRootNodes(page, rows);
    }

    /**
     * 查询所有类型为type的根节点列表
     */
    @SuppressWarnings("unchecked")
    @Override
    public BaseRecords<E> findRootNodes(int type) {
        return (BaseRecords<E>) getTreeDao()._findRootNodes(type);
    }

    /**
     * 分页查询所有类型为type的根节点列表
     */
    @SuppressWarnings("unchecked")
    @Override
    public BaseRecords<E> findRootNodes(int type, int page, int rows) {
        return (BaseRecords<E>) getTreeDao()._findRootNodes(type, page,
                rows);
    }

    /**
     * 查询名字模糊匹配name所有根节点列表
     */
    @SuppressWarnings("unchecked")
    @Override
    public BaseRecords<E> findRootNodes(String name) {
        return (BaseRecords<E>) getTreeDao()._findRootNodes(name);
    }

    /**
     * 分页查询名字模糊匹配name所有根节点列表
     */
    @SuppressWarnings("unchecked")
    @Override
    public BaseRecords<E> findRootNodes(String name, int page, int rows) {
        return (BaseRecords<E>) getTreeDao()._findRootNodes(name, page,
                rows);
    }

    /**
     * 查询所有名字模糊匹配name，类型为type的根节点列表
     */
    @SuppressWarnings("unchecked")
    @Override
    public BaseRecords<E> findRootNodes(String name, int type) {
        return (BaseRecords<E>) getTreeDao()._findRootNodes(name, type);
    }

    /**
     * 分页查询所有名字模糊匹配name，类型为type的根节点列表
     */
    @SuppressWarnings("unchecked")
    @Override
    public BaseRecords<E> findRootNodes(String name, int type, int page,
                                        int rows) {
        return (BaseRecords<E>) getTreeDao()._findRootNodes(name, type,
                page, rows);
    }

    /**
     * 查询所有叶子节点列表
     */
    @SuppressWarnings("unchecked")
    @Override
    public BaseRecords<E> findLeafNodes() {
        return (BaseRecords<E>) getTreeDao()._findLeafNodes();
    }

    /**
     * 分页查询所有叶子节点列表
     */
    @SuppressWarnings("unchecked")
    @Override
    public BaseRecords<E> findLeafNodes(int page, int rows) {
        return (BaseRecords<E>) getTreeDao()._findLeafNodes(page, rows);
    }

    /**
     * 查询所有类型为type的叶子节点列表
     */
    @SuppressWarnings("unchecked")
    @Override
    public BaseRecords<E> findLeafNodes(int type) {
        return (BaseRecords<E>) getTreeDao()._findLeafNodes(type);
    }

    /**
     * 分页查询所有类型为type的叶子节点列表
     */
    @SuppressWarnings("unchecked")
    @Override
    public BaseRecords<E> findLeafNodes(int type, int page, int rows) {
        return (BaseRecords<E>) getTreeDao()._findLeafNodes(type, page,
                rows);
    }

    /**
     * 查询名字模糊匹配name的所有叶子节点列表
     */
    @SuppressWarnings("unchecked")
    @Override
    public BaseRecords<E> findLeafNodes(String name) {
        return (BaseRecords<E>) getTreeDao()._findLeafNodes(name);
    }

    /**
     * 分页查询名字模糊匹配name的所有叶子节点列表
     */
    @SuppressWarnings("unchecked")
    @Override
    public BaseRecords<E> findLeafNodes(String name, int page, int rows) {
        return (BaseRecords<E>) getTreeDao()._findLeafNodes(name, page,
                rows);
    }

    /**
     * 查询所有类型为type，名字模糊匹配name的叶子节点列表
     */
    @SuppressWarnings("unchecked")
    @Override
    public BaseRecords<E> findLeafNodes(String name, int type) {
        return (BaseRecords<E>) getTreeDao()._findLeafNodes(name, type);
    }

    /**
     * 分页查询所有类型为type，名字模糊匹配name的叶子节点列表
     */
    @SuppressWarnings("unchecked")
    @Override
    public BaseRecords<E> findLeafNodes(String name, int type, int page,
                                        int rows) {
        return (BaseRecords<E>) getTreeDao()._findLeafNodes(name, type,
                page, rows);
    }

    /**
     * 查询系统所有节点
     */
    @SuppressWarnings("unchecked")
    @Override
    public BaseRecords<E> findNodes() {
        return (BaseRecords<E>) getTreeDao()._findNodes();
    }

    /**
     * 分页查询系统所有节点
     */
    @SuppressWarnings("unchecked")
    @Override
    public BaseRecords<E> findNodes(int page, int rows) {
        return (BaseRecords<E>) getTreeDao()._findNodes(page, rows);
    }

    /**
     * 查询系统所有类型为type的节点
     */
    @SuppressWarnings("unchecked")
    @Override
    public BaseRecords<E> findNodes(int type) {
        return (BaseRecords<E>) getTreeDao()._findNodes(type);
    }

    /**
     * 分页查询系统所有类型为type的节点
     */
    @SuppressWarnings("unchecked")
    @Override
    public BaseRecords<E> findNodes(int type, int page, int rows) {
        return (BaseRecords<E>) getTreeDao()._findNodes(type, page, rows);
    }

    /**
     * 查询系统所有名字模糊匹配name节点
     */
    @SuppressWarnings("unchecked")
    @Override
    public BaseRecords<E> findNodes(String name) {
        return (BaseRecords<E>) getTreeDao()._findNodes(name);
    }

    /**
     * 分页查询系统所有名字模糊匹配name节点
     */
    @SuppressWarnings("unchecked")
    @Override
    public BaseRecords<E> findNodes(String name, int page, int rows) {
        return (BaseRecords<E>) getTreeDao()._findNodes(name, page, rows);
    }

    /**
     * 查询系统名字模糊匹配name，所有类型为type的节点
     */
    @SuppressWarnings("unchecked")
    @Override
    public BaseRecords<E> findNodes(String name, int type) {
        return (BaseRecords<E>) getTreeDao()._findNodes(name, type);
    }

    /**
     * 分页查询系统名字模糊匹配name，所有类型为type的节点
     */
    @SuppressWarnings("unchecked")
    @Override
    public BaseRecords<E> findNodes(String name, int type, int page,
                                    int rows) {
        return (BaseRecords<E>) getTreeDao()._findNodes(name, type, page,
                rows);
    }

    /**
     * 查询节点id的子节点列表，不包括子节点下的子节点
     */
    @SuppressWarnings("unchecked")
    @Override
    public BaseRecords<E> findChildrenNodes(E node) {
        return (BaseRecords<E>) getTreeDao()._findChildrenNodes(node);
    }

    /**
     * 分页查询节点id的子节点列表，不包括子节点下的子节点
     */
    @SuppressWarnings("unchecked")
    @Override
    public BaseRecords<E> findChildrenNodes(E node, int page, int rows) {
        return (BaseRecords<E>) getTreeDao()._findChildrenNodes(node,
                page, rows);
    }

    /**
     * 查询节点id的子节点中类型为type的所有子节点，不包括子节点下的子节点
     */
    @SuppressWarnings("unchecked")
    @Override
    public BaseRecords<E> findChildrenNodes(E node, int type) {
        return (BaseRecords<E>) getTreeDao()
                ._findChildrenNodes(node, type);
    }

    /**
     * 分页查询节点id的子节点中类型为type的所有子节点，不包括子节点下的子节点
     */
    @SuppressWarnings("unchecked")
    @Override
    public BaseRecords<E> findChildrenNodes(E node, int type, int page,
                                            int rows) {
        return (BaseRecords<E>) getTreeDao()._findChildrenNodes(node,
                type, page, rows);
    }

    /**
     * 查询节点id的子节点中名字模糊匹配name的列表，不包括子节点下的子节点
     */
    @SuppressWarnings("unchecked")
    @Override
    public BaseRecords<E> findChildrenNodes(E node, String name) {
        BaseRecords<E> result = (BaseRecords<E>) getTreeDao()
                ._findChildrenNodes(node, name);

        return result;
    }

    /**
     * 查询节点id的子节点中名字模糊匹配name的列表，不包括子节点下的子节点
     */
    @SuppressWarnings("unchecked")
    @Override
    public BaseRecords<E> findChildrenNodes(E node, String name, int type) {
        return (BaseRecords<E>) getTreeDao()._findChildrenNodes(node,
                name, type);
    }

    /**
     * 查询节点id的子节点中名字模糊匹配name的列表，不包括子节点下的子节点
     */
    @SuppressWarnings("unchecked")
    @Override
    public BaseRecords<E> findChildrenNodes(E node, String name, int type,
                                            int page, int rows) {
        return (BaseRecords<E>) getTreeDao()._findChildrenNodes(node,
                name, type, page, rows);
    }

    /**
     * 查询节点id的子节点中名字模糊匹配name的列表，不包括子节点下的子节点
     */
    @SuppressWarnings("unchecked")
    @Override
    public BaseRecords<E> findChildrenNodes(E node, String name, int page,
                                            int rows) {
        return (BaseRecords<E>) getTreeDao()._findChildrenNodes(node,
                name, page, rows);
    }

    /**
     * 分页查找节点的父节点
     */
    @SuppressWarnings("unchecked")
    @Override
    public BaseRecords<E> findParentNodes(E node) {
        return (BaseRecords<E>) getTreeDao()._findParentNodes(node);
    }

    /**
     * 分页查询节点id的父节点列表，不包括父节点上的父节点
     */
    @SuppressWarnings("unchecked")
    @Override
    public BaseRecords<E> findParentNodes(E node, int page, int rows) {
        return (BaseRecords<E>) getTreeDao()._findParentNodes(node, page,
                rows);
    }

    /**
     * 查询节点id的父节点中类型为type的所有父节点，不包括父节点上的父节点
     */
    @SuppressWarnings("unchecked")
    @Override
    public BaseRecords<E> findParentNodes(E node, int type) {
        return (BaseRecords<E>) getTreeDao()._findParentNodes(node, type);
    }

    /**
     * 分页查询节点id的父节点中类型为type的所有父节点，不包括父节点上的父节点
     */
    @SuppressWarnings("unchecked")
    @Override
    public BaseRecords<E> findParentNodes(E node, int type, int page,
                                          int rows) {
        return (BaseRecords<E>) getTreeDao()._findParentNodes(node, type,
                page, rows);
    }

    /**
     * 查询节点id的父节点中名字模糊匹配name的列表，不包括父节点上的父节点
     */
    @SuppressWarnings("unchecked")
    @Override
    public BaseRecords<E> findParentNodes(E node, String name) {
        return (BaseRecords<E>) getTreeDao()._findParentNodes(node, name);
    }

    /**
     * 查询节点id的父节点中名字模糊匹配name，并且类型为type的列表，不包括父节点上的父节点
     */
    @SuppressWarnings("unchecked")
    @Override
    public BaseRecords<E> findParentNodes(E node, String name, int type) {
        return (BaseRecords<E>) getTreeDao()._findParentNodes(node, name,
                type);
    }

    /**
     * 分页查询节点id的父节点中名字模糊匹配name的列表，不包括父节点上的父节点
     */
    @SuppressWarnings("unchecked")
    @Override
    public BaseRecords<E> findParentNodes(E node, String name, int page,
                                          int rows) {
        return (BaseRecords<E>) getTreeDao()._findParentNodes(node, name,
                page, rows);
    }

    /**
     * 分页查询节点id的父节点中名字模糊匹配name，并且类型为type的列表，不包括父节点上的父节点
     */
    @SuppressWarnings("unchecked")
    @Override
    public BaseRecords<E> findParentNodes(E node, String name, int type,
                                          int page, int rows) {
        return (BaseRecords<E>) getTreeDao()._findParentNodes(node, name,
                type, page, rows);
    }

    /**
     * 查找从根节点到当前节点的唯一路径
     * 只有当节点到根节点有唯一路径时才返回数据，否则返回null
     */
    @Override
    public Tree<E> findOnlyParentPath(E node) {
        Tree<E> tree = new Tree<>();
        tree.setNode(node);

        Tree<E> current = tree;
        while (true) {
            List<E> parents = findParentNodes(current.getNode()).getData();
            if (parents.size() > 0) {
                Tree<E> parentTree = new Tree<E>();
                parentTree.setNode(parents.get(0));
                List<Tree<E>> childTreeList = new ArrayList<>();
                childTreeList.add(current);
                parentTree.setChildren(childTreeList);
                current = parentTree;
                continue;
            } else {
                break;
            }
        }
        return current;
    }

    private List<E> removeRepeat(List<E> list) {
        Map<String, E> map = new HashMap<String, E>();
        Iterator<E> it = list.iterator();
        while (it.hasNext()) {
            E node = it.next();

            if (map.containsKey("" + node.getId())) {
                it.remove();
            } else {
                map.put("" + node.getId(), node);
            }
        }
        return list;
    }

    /**
     * 递归查询节点id的子节点列表，包括子节点下的子节点，无法作分页
     * <p/>
     * 注： 该递归查询不穿透不符合条件的节点到这些节点的子节点中去查询
     */
    @Override
    public List<E> findChildrenNodes_r(E nd) {
        List<E> childs = new ArrayList<>();

        // 查询所有子节点
        List<E> listsall = findChildrenNodes(nd).getData();

        // 添加符合条件的所有子节点
        childs.addAll(listsall);
        for (int i = 0; i < listsall.size(); i++) {
            childs.addAll(findChildrenNodes_r(listsall.get(i)));
        }

        return removeRepeat(childs);
    }

    /**
     * 递归查询节点id的子节点中类型为type的所有子节点，包括子节点下的子节点，无法作分页
     * <p/>
     * 注： 该递归查询不穿透不符合条件的节点到这些节点的子节点中去查询
     */
    @Override
    public List<E> findChildrenNodes_r(E nd, int type) {
        List<E> childs = new ArrayList<>();

        // 查询所有子节点
        List<E> listsall = findChildrenNodes(nd).getData();

        // 添加符合条件的所有子节点
        for (E e : listsall) {
            if (e.getType() == type) {
                childs.add(e);
            }
        }
        for (int i = 0; i < listsall.size(); i++) {
            childs.addAll(findChildrenNodes_r(listsall.get(i), type));
        }
        return removeRepeat(childs);
    }

    /**
     * 递归查询节点id的子节点中名字模糊匹配name的列表，包括子节点下的子节点，无法作分页
     * <p/>
     * 注： 该递归查询不穿透不符合条件的节点到这些节点的子节点中去查询
     */
    @Override
    public List<E> findChildrenNodes_r(E nd, String name) {
        List<E> childs = new ArrayList<>();

        // 查询所有子节点
        List<E> listsall = findChildrenNodes(nd).getData();

        // 添加符合条件的所有子节点
        for (E e : listsall) {
            if (e.getName() != null && e.getName().contains(name)) {
                childs.add(e);
            }
        }

        for (int i = 0; i < listsall.size(); i++) {
            childs.addAll(findChildrenNodes_r(listsall.get(i), name));
        }
        return removeRepeat(childs);
    }

    /**
     * 递归查询节点id的子节点中类型为type的所有子节点中名字模糊匹配name的，包括子节点下的子节点，无法作分页
     * <p/>
     * 注： 该递归查询不穿透不符合条件的节点到这些节点的子节点中去查询
     */
    @Override
    public List<E> findChildrenNodes_r(E nd, String name, int type) {
        List<E> childs = new ArrayList<>();

        // 查询所有子节点
        List<E> listsall = findChildrenNodes(nd).getData();

        // 添加符合条件的所有子节点
        for (E e : listsall) {
            if (e.getType() == type && e.getName() != null && e.getName().contains(name)) {
                childs.add(e);
            }
        }

        for (int i = 0; i < listsall.size(); i++) {
            childs.addAll(findChildrenNodes_r(listsall.get(i), name, type));
        }
        return removeRepeat(childs);
    }

    /**
     * 递归查询节点id的所有父节点
     * <p/>
     * 注： 该递归查询不穿透不符合条件的节点到这些节点的子节点中去查询
     */
    @Override
    public List<E> findParentNodes_r(E nd) {
        List<E> parents = new ArrayList<>();

        // 查询符合条件的所有父节点
        List<E> listsall = findParentNodes(nd).getData();

        // 添加符合条件的所有父节点
        parents.addAll(listsall);

        for (int i = 0; i < listsall.size(); i++) {
            parents.addAll(findParentNodes_r(listsall.get(i)));
        }
        return removeRepeat(parents);
    }

    /**
     * 递归查询节点id的父节点中类型为type的所有父节点，包括父节点上的父节点，无法作分页
     * <p/>
     * 注： 该递归查询不穿透不符合条件的节点到这些节点的子节点中去查询
     */
    public List<E> findParentNodes_r(E nd, int type) {
        List<E> parents = new ArrayList<>();

        // 查询所有父节点
        List<E> listsall = findParentNodes(nd).getData();

        // 添加符合条件的所有父节点
        for (E e : listsall) {
            if (e.getType() == type) {
                parents.add(e);
            }
        }
        for (int i = 0; i < listsall.size(); i++) {
            parents.addAll(findParentNodes_r(listsall.get(i), type));
        }
        return removeRepeat(parents);
    }

    /**
     * 递归查询节点id的父节点中名字模糊匹配name的列表，包括父节点上的父节点，无法作分页
     * <p/>
     * 注： 该递归查询不穿透不符合条件的节点到这些节点的子节点中去查询
     */
    public List<E> findParentNodes_r(E nd, String name) {
        List<E> parents = new ArrayList<>();

        // 查询所有子节点
        List<E> listsall = findParentNodes(nd).getData();

        // 添加符合条件的所有父节点
        for (E e : listsall) {
            if (e.getName() != null && e.getName().contains(name)) {
                parents.add(e);
            }
        }

        for (int i = 0; i < listsall.size(); i++) {
            parents.addAll(findParentNodes_r(listsall.get(i), name));
        }
        return removeRepeat(parents);
    }

    /**
     * 递归查询节点id的父节点中类型为type的所有父节点中名字模糊匹配name的，包括父节点上的父节点，无法作分页
     * <
     * 注： 该递归查询不穿透不符合条件的节点到这些节点的子节点中去查询
     */
    public List<E> findParentNodes_r(E nd, String name, int type) {
        List<E> parents = new ArrayList<>();

        // 查询所有父节点
        List<E> listsall = findParentNodes(nd).getData();

        // 添加符合条件的所有父节点
        for (E e : listsall) {
            if (e.getType() == type && e.getName() != null && e.getName().contains(name)) {
                parents.add(e);
            }
        }
        for (int i = 0; i < listsall.size(); i++) {
            parents.addAll(findParentNodes_r(listsall.get(i), name, type));
        }
        return removeRepeat(parents);
    }

    /**
     * 查询节点id对应的子树, lv = -1 搜索到叶子节点， lv=0 返回该节点， lv=1返回该节点与子节点，lv=2.....
     * lv 查询层次
     */
    @Override
    public Tree<E> findNodeTree(E nd, int lv) {
        Tree<E> tree = new Tree<E>();
        // 先查询本节点
        E node = this.findNode(nd);
        tree.setNode(node);
        if (lv == -1 || lv > 0) {
            List<E> children = this.findChildrenNodes(nd).getData();

            if (children != null) {
                for (int i = 0; i < children.size(); i++) {
                    E child = children.get(i);
                    // 查询第一个子节点的树
                    Tree<E> subTree = findNodeTree(child, lv == -1 ? -1 : lv - 1);
                    tree.getChildren().add(subTree);
                    subTree.getParents().add(tree);
                }
            }
        }
        return tree;
    }
}
