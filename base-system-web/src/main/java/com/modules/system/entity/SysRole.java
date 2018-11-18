package com.modules.system.entity;


import com.common.api.DataEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 角色
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class SysRole extends DataEntity {

    /**
     * 用户ID
     */
    private String userId;
    /**
     * 角色编码
     */
    private String code;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 角色类型
     */
    private String type;
    /**
     * 角色类型描述
     */
    private String typeDesc;
    /**
     * 状态
     */
    private String state;
    /**
     * 状态描述
     */
    private String stateDesc;
    /**
     * 等级
     */
    private String level;
    /**
     * 等级描述
     */
    private String levelDesc;
    /**
     * place_org_all
     * 数据范围[1：所有数据；2：所在机构及以下数据；3：所在机构数据；4：仅本人数据；5：按明细设置]
     */
    private String dataScope;
    /**
     * 数据范围描述
     */
    private String dataScopeDesc;
    /**
     * 拥有菜单列表
     */
    private List<SysMenu> menus;
    /**
     * 按明细设置数据范围
     */
    private List<SysOrg> orgs;
    /**
     * 机构ids
     */
    private String orgIds;
    /**
     * 菜单ids
     */
    private String menuIds;
    /**
     * 用户ids
     */
    private String userIds;

    public SysRole() {}

    public SysRole(Long id) {
        super(id);
    }


}
