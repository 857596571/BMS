package com.modules.system.entity;

import com.common.api.DataTreeEntity;

import java.util.ArrayList;
import java.util.List;


/**
 * 字典管理
 */
public class SysDict extends DataTreeEntity {

    /**
     * 编码
     */
    private String code;
    /**
     * 标签
     */
    private String label;
    /**
     * 数据值
     */
    private String value;
    /**
     * 值类型[1:值，2:表达式]
     */
    private Integer type;
    /**
     * IF表达式
     */
    private String expression;
    /**
     * 描述
     */
    private String description;
    /**
     * 数据值
     */
    private Integer sort;
    /**
     * 状态[1:在用，0:停用]
     */
    private Integer state;
    /**
     * 等级[1:系统，2:业务]
     */
    private String level;
    /**
     * 子节点
     */
    private List<SysDict> children;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public List<SysDict> getChildren() {
        return children;
    }

    public void setChildren(List<SysDict> children) {
        this.children = children;
    }

    /**
     * 添加子节点
     * @param node
     */
    public void addChild(SysDict node) {
        if(this.children == null) this.children = new ArrayList<>();
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
