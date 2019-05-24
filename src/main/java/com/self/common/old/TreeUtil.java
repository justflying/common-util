package com.self.common.old;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 *@ClassName TreeUtil
 *@Description 整理树结构的相关工具类,把数据库中查询出来的所有数据,转换成带有层级的树形结构
 *@Author Lien
 *@Date 2019/5/24 14:07
 *@Version 1.0
 **/
public class TreeUtil  {

    private AtomicInteger length;

    public TreeUtil(){
        length = new AtomicInteger();
    }


    /**
     * 单个顶点树结构
     * @param source
     * @param topId
     * @return
     */
    public TreeNode toTopTree(List source, int topId){
        List<TreeNode> nodes = (List<TreeNode>) source;
        //记录当前树节点值
        length.set(nodes.size());

        TreeNode root = nodes.stream()
                .filter(item -> item.getCurrentId() == topId)
                .collect(Collectors.toList())
                .get(0);
        length.getAndDecrement();

        if(length.intValue() == 0){
            return root;
        }

        ArrayList<TreeNode> tops = new ArrayList<>();
        tops.add(root);
        insertTree(tops,nodes);
        return root;
    }

    /**
     * 把所有节点插入父节点组
     * @param tops
     * @param nodes
     */
    private void insertTree(List<TreeNode> tops,List<TreeNode> nodes){
        tops.stream()
                .forEach(item->{
                    LinkedList<TreeNode> list = new LinkedList<>();
                    nodes.stream()
                            .forEach(item2->{
                                if(item2.getParentId() == item.getCurrentId()){
                                    length.getAndDecrement();
                                    list.add(item2);
                                }
                            });
                    item.setChildrens(list);
                });
        if(length.intValue() == 0){
            return;
        }else {
            tops.stream()
                    .forEach(item->{
                        List<TreeNode> childrens = item.getChildrens();
                        insertTree(childrens,nodes);
                    });
        }
    }

    /**
     * 适合查询parent = 1 的所有数据
     * 适合部门数据
     *      id          dept_name           parent
     *      1              总经办              0
     *      2              人事部              1
     *      3              财务部              1
     * @param source
     * @param topId
     * @return
     */
    public List<TreeNode> toListTree(List source,int topId){
        List<TreeNode> nodes = (List<TreeNode>) source;
        //记录当前树节点值
        length.set(nodes.size());
        List<TreeNode> tops = nodes.stream()
                .filter(item -> item.getCurrentId() == topId)
                .collect(Collectors.toList());

        if(length.get() == 0){
            return tops;
        }

        insertTree(tops,nodes);
        return tops;
    }


    /**
     * 适合查询parent = 0的所有数据
     * 比如:
     *          id      name        parent
     *           1       菜单1        0
     *           2       菜单2        0
     *           3      菜单1-1       1
     * @param source
     * @param topId
     * @return
     */
    public List<TreeNode> toListTreeByParent(List source,int topId){
        List<TreeNode> nodes = (List<TreeNode>) source;
        //记录当前树节点值
        length.set(nodes.size());
        List<TreeNode> tops = nodes.stream()
                .filter(item -> item.getParentId() == topId)
                .collect(Collectors.toList());

        if(length.get() == 0){
            return tops;
        }

        insertTree(tops,nodes);
        return tops;
    }


}