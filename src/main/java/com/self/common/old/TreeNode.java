package com.self.common.old;

import java.util.List;

/**
 * @author: Lien
 * @since: 2019/5/24
 * @description: 标准树节点,其中只包含了需要使用的属性
 */
public interface TreeNode {

    /**
     * 获取当前节点ID
     * @return
     */
    Integer getCurrentId();


    /**
     * 当前父节点ID
     * @return
     */
    Integer getParentId();

    /**
     * 获取所有子节点
     * @return
     */
    List<TreeNode> getChildrens();


    /**
     * 设置是否可选
     * @param flag
     */
    void setDisabled(boolean flag);
    boolean getDisabled();



    /**
     * 设置所有子节点
     * @param list
     */
    void setChildrens(List<TreeNode> list);
}
