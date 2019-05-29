package TreeTest;

import com.alibaba.fastjson.annotation.JSONField;
import com.self.common.old.TreeNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 自定义树节点,实现树排序接口
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyTreeNode implements TreeNode {

    /**
     * 当前节点ID
     */
    private Integer id;

    /**
     * 展示的名称
     */
    private String name;

    /**
     * 父节点ID
     */
    private Integer parentId;

    /**
     * 是否可选中(这个是因为使用Element UI树组件需要这个属性,用不到可以忽略)
     */
    private Boolean disabled;


    private List<TreeNode> children;


    @Override
    public int getCurrentId() {
        return this.id;
    }

    @Override
    public int getParentId() {
        return this.parentId;
    }

    @JSONField(serialize=false)
    @Override
    public List<TreeNode> getChildrens() {
        return this.children;
    }

    @Override
    public void setDisabled(boolean flag) {
        this.disabled = flag;
    }

    @Override
    public boolean getDisabled() {
        return this.disabled;
    }


    @Override
    public void setChildrens(List<TreeNode> list) {
        this.children = list;
    }
}
