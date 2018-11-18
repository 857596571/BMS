package com.modules.system.entity;

import com.common.api.DataTreeEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * 字典管理
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class SysDict extends DataTreeEntity {

    /**
     * 编码
     */
    private String        code;
    /**
     * 标签
     */
    private String        label;
    /**
     * 数据值
     */
    private String        value;
    /**
     * 值类型
     */
    private String        type;
    /**
     * 值类型
     */
    private String        typeDesc;
    /**
     * IF表达式
     */
    private String        expression;
    /**
     * 描述
     */
    private String        description;
    /**
     * 数据值
     */
    private Integer       sort;
    /**
     * 状态
     */
    private String        state;
    /**
     * 状态描述
     */
    private String        stateDesc;
    /**
     * 等级
     */
    private String        level;
    /**
     * 等级描述
     */
    private String        levelDesc;
    /**
     * 子节点
     */
    private List<SysDict> children;

    /**
     * 添加子节点
     * @param node
     */
    public void addChild(SysDict node) {
        if (this.children == null) {
            this.children = new ArrayList<>();
        }
        this.children.add(node);
    }

    //	public static boolean executeExpression(Map<String, Object> rep) {
    //		ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript");
    //		if(CollectionUtil.isNotEmpty(rep)) {
    //			for (Map.Entry<String, Object> entry : rep.entrySet()) {
    //				jse.put(entry.getKey(), entry.getValue());
    //			}
    //			try {
    //				if((boolean) jse.eval(getExpression())) {
    //					flag += sysDict.getValue();
    //				}
    //			} catch (ScriptException e) {
    //				e.printStackTrace();
    //			}
    //		}
    //		return false;
    //	}
}
