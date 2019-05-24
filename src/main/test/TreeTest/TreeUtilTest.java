package TreeTest;

import com.alibaba.fastjson.JSONObject;
import com.self.common.old.TreeNode;
import com.self.common.old.TreeUtil;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * 树工具节点测试类
 */
public class TreeUtilTest {

    /**
     * 测试顶级树排序
     */
    @Test
    public void test01(){
        MyTreeNode node1 = new MyTreeNode(1,"节点1",0,true,null);
        MyTreeNode node2 = new MyTreeNode(2,"节点2",1,true,null);
        MyTreeNode node3 = new MyTreeNode(3,"节点3",1,true,null);
        MyTreeNode node4 = new MyTreeNode(4,"节点4",2,true,null);
        MyTreeNode node5 = new MyTreeNode(5,"节点5",2,true,null);
        MyTreeNode node6 = new MyTreeNode(6,"节点6",5,true,null);

        List<MyTreeNode> myTreeNodes = Arrays.asList(node1, node2, node3, node4, node5, node6);


        TreeNode treeNode = new TreeUtil().toTopTree(myTreeNodes, 1);
        System.out.println(JSONObject.toJSONString(treeNode,true));
    }


    /**
     * 测试当前节点为最高节点排序,效果同上,最根节点只会有一个节点,但是返回的是一个数组
     */
    @Test
    public void test02(){
        MyTreeNode node1 = new MyTreeNode(1,"节点1",0,true,null);
        MyTreeNode node2 = new MyTreeNode(2,"节点2",1,true,null);
        MyTreeNode node3 = new MyTreeNode(3,"节点3",1,true,null);
        MyTreeNode node4 = new MyTreeNode(4,"节点4",2,true,null);
        MyTreeNode node5 = new MyTreeNode(5,"节点5",2,true,null);
        MyTreeNode node6 = new MyTreeNode(6,"节点6",5,true,null);

        List<MyTreeNode> myTreeNodes = Arrays.asList(node1, node2, node3, node4, node5, node6);


        List<TreeNode> treeNodes = new TreeUtil().toListTree(myTreeNodes, 1);
        System.out.println(JSONObject.toJSONString(treeNodes,true));

    }

    /**
     * 测试指定节点为最高节点排序,和上面不一样的是,根节点可以有多个
     */
    @Test
    public void test03(){
        MyTreeNode node1 = new MyTreeNode(1,"节点1",0,true,null);
        MyTreeNode node2 = new MyTreeNode(2,"节点2",1,true,null);
        MyTreeNode node3 = new MyTreeNode(3,"节点3",1,true,null);
        MyTreeNode node4 = new MyTreeNode(4,"节点4",2,true,null);
        MyTreeNode node5 = new MyTreeNode(5,"节点5",2,true,null);
        MyTreeNode node6 = new MyTreeNode(6,"节点6",5,true,null);

        MyTreeNode node11 = new MyTreeNode(11,"节点11",0,true,null);
        MyTreeNode node22 = new MyTreeNode(22,"节点22",11,true,null);
        MyTreeNode node33 = new MyTreeNode(33,"节点33",11,true,null);
        MyTreeNode node44 = new MyTreeNode(44,"节点44",22,true,null);
        MyTreeNode node55 = new MyTreeNode(55,"节点55",22,true,null);
        MyTreeNode node66 = new MyTreeNode(66,"节点66",55,true,null);

        MyTreeNode node111 = new MyTreeNode(111,"节点111",0,true,null);
        MyTreeNode node222 = new MyTreeNode(222,"节点222",111,true,null);
        MyTreeNode node333 = new MyTreeNode(333,"节点333",111,true,null);
        MyTreeNode node444 = new MyTreeNode(444,"节点444",222,true,null);
        MyTreeNode node555 = new MyTreeNode(555,"节点555",222,true,null);
        MyTreeNode node666 = new MyTreeNode(666,"节点666",555,true,null);

        List<MyTreeNode> myTreeNodes = Arrays.asList(node1, node2, node3, node4, node5, node6,
                node11, node22, node33, node44, node55, node66,
                node111, node222, node333, node444, node555, node666);

        List treeNodes = new TreeUtil().toListTreeByParent(myTreeNodes, 0);

        System.out.println(JSONObject.toJSONString(treeNodes,true));

    }




}
