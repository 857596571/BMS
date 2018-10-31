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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
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
     * Getter method for property <tt>typeDesc</tt>.
     *
     * @return property value of typeDesc
     */
    public String getTypeDesc() {
        return typeDesc;
    }

    /**
     * Setter method for property <tt>typeDesc</tt>.
     *
     * @param typeDesc value to be assigned to property typeDesc
     */
    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }

    /**
     * Getter method for property <tt>stateDesc</tt>.
     *
     * @return property value of stateDesc
     */
    public String getStateDesc() {
        return stateDesc;
    }

    /**
     * Setter method for property <tt>stateDesc</tt>.
     *
     * @param stateDesc value to be assigned to property stateDesc
     */
    public void setStateDesc(String stateDesc) {
        this.stateDesc = stateDesc;
    }

    /**
     * Getter method for property <tt>levelDesc</tt>.
     *
     * @return property value of levelDesc
     */
    public String getLevelDesc() {
        return levelDesc;
    }

    /**
     * Setter method for property <tt>levelDesc</tt>.
     *
     * @param levelDesc value to be assigned to property levelDesc
     */
    public void setLevelDesc(String levelDesc) {
        this.levelDesc = levelDesc;
    }

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
