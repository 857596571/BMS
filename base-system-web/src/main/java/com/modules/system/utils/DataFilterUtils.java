package com.modules.system.utils;

import cn.hutool.core.util.StrUtil;
import com.common.api.DataEntity;
import com.common.security.util.AuthUserUtil;
import com.google.common.collect.Lists;
import com.modules.system.entity.SysRole;
import com.modules.system.security.model.AuthUser;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * 数据权限工具类
 */
public class DataFilterUtils {

    /**
     * 数据范围过滤
     * @param entity 查询bean
     * @param orgAlias sql中机构别名
     * @param userAlias sql中用户别名
     * @return 过滤sql
     */
    public static void dataScopeFilter(DataEntity entity, String orgAlias, String userAlias) {

        AuthUser user = AuthUserUtil.getCurrentUser();

        StringBuilder sqlString = new StringBuilder();

        // 进行权限过滤，多个角色权限范围之间为或者关系。
        List<String> dataScope = Lists.newArrayList();

        // 超级管理员，跳过权限过滤
        if (!user.isAdmin()){
            boolean isDataScopeAll = false;
            for (SysRole r : user.getRoles()){
                for (String oa : StrUtil.split(orgAlias, ",")){
                    if (!dataScope.contains(r.getDataScope()) && StrUtil.isNotEmpty(oa)){
                        //全部数据
                        if ("SCOPE_ALL".equals(r.getDataScope())){
                            isDataScopeAll = true;
                        }
                        //所在机构及以下
                        else if ("SCOPE_ORG_ALL".equals(r.getDataScope())){
                            String sql = "select o.id from sys_org t join sys_org o on o.left_num between t.left_num and t.right_num and o.state = 'ON' and o.del_flag = '0' where t.id = "+ user.getOrgId() +" and t.state = 'ON' and t.del_flag = '0'";
                            sqlString.append(" OR " + oa + ".id in (" + sql +")");
                        }
                        //所在机构
                        else if ("SCOPE_ORG".equals(r.getDataScope())){
                            sqlString.append(" OR " + oa + ".id = '" + user.getOrgId() + "'");
                        }
                        //明细
                        else if ("SCOPE_DETAIL".equals(r.getDataScope())){
                            sqlString.append(" OR EXISTS (SELECT 1 FROM sys_role_org WHERE role_id = '" + r.getId() + "'");
                            sqlString.append(" AND org_id = " + oa +".id)");
                        }
                        dataScope.add(r.getDataScope().toString());
                    }
                }
            }
            // 如果没有全部数据权限，并设置了用户别名，则当前权限为本人；如果未设置别名，当前无权限为已植入权限
            if (!isDataScopeAll){
                if (StringUtils.isNotBlank(userAlias)){
                    for (String ua : StringUtils.split(userAlias, ",")){
                        sqlString.append(" OR " + ua + ".id = '" + user.getId() + "'");
                    }
                }else {
                    for (String oa : StringUtils.split(orgAlias, ",")){
                        //sqlString.append(" OR " + oa + ".id  = " + user.getOffice().getId());
                        sqlString.append(" OR " + oa + ".id IS NULL");
                    }
                }
            }else{
                // 如果包含全部权限，则去掉之前添加的所有条件，并跳出循环。
                sqlString = new StringBuilder();
            }
        }
        if (StringUtils.isNotBlank(sqlString.toString())){
            entity.setSqlMap(" AND (" + sqlString.substring(4) + ")");
        }
    }
}
