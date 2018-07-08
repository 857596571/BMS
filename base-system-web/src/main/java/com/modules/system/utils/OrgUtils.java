package com.modules.system.utils;

import com.modules.system.entity.SysMenu;
import com.modules.system.entity.SysOrg;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 机构工具类
 */
public class OrgUtils {

    /**
     * 机构排序
     * @param list       目标菜单列表
     * @param sourceList 原始菜单列表
     * @param parentId   父级编号
     */
    public static void sortList(List<SysOrg> list, List<SysOrg> sourceList, String parentId) {
        sourceList.stream()
                .filter(org -> org.getParentId() != null && org.getParentId().toString().equals(parentId))
                .forEach(menu -> {
                    list.add(menu);
                    // 判断是否还有子节点, 有则继续获取子节点
                    sourceList.stream()
                            .filter(child -> child.getParentId() != null && child.getParentId().toString().equals(menu.getId()))
                            .peek(child -> sortList(list, sourceList, menu.getId()))
                            .findFirst();
                });
    }

    /**
     * 构建树形结构
     * @param list 原始数据
     * @return 机构列表
     */
    public static List<SysOrg> makeTree(List<SysOrg> list) {
        // 构建一个Map,把所有原始数据的ID作为Key,原始数据对象作为VALUE
        Map<String, SysOrg> dtoMap = new LinkedHashMap<>();
        for (SysOrg node : list) {
            // 原始数据对象为Node，放入dtoMap中。
            dtoMap.put(node.getId(), node);
        }

        List<SysOrg> result = new ArrayList<>();
        for (Map.Entry<String, SysOrg> entry : dtoMap.entrySet()) {
            SysOrg node = entry.getValue();
            Integer parentId = node.getParentId();
            if (dtoMap.get(parentId.toString()) == null) {
                // 如果是顶层节点，直接添加到结果集合中
                result.add(node);
            } else {
                // 如果不是顶层节点，有父节点，然后添加到父节点的子节点中
                SysOrg parent = dtoMap.get(parentId.toString());
                parent.addChild(node);
            }
        }
        List<SysOrg> resultSort = new ArrayList<>();
        OrgUtils.sortList(resultSort, result, "1");
        return result;
    }
}
