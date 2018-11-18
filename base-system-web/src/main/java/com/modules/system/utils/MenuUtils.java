package com.modules.system.utils;

import cn.hutool.core.util.StrUtil;
import com.modules.system.entity.SysMenu;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 菜单工具类
 */
public class MenuUtils {
    /**
     * 菜单排序
     * @param list       目标菜单列表
     * @param sourceList 原始菜单列表
     * @param parentId   父级编号
     */
    public static void sortList(List<SysMenu> list, List<SysMenu> sourceList, Long parentId) {
        sourceList.stream()
                .filter(menu -> menu.getParentId() != null && menu.getParentId().toString()
                        .equals(parentId)).forEach(menu -> {
            list.add(menu);
            // 判断是否还有子节点, 有则继续获取子节点
            sourceList.stream()
                    .filter(child -> child.getParentId() != null && child.getParentId().toString()
                            .equals(menu.getId()))
                    .peek(child -> sortList(list, sourceList, menu.getId())).findFirst();
        });
    }

    /**
     * 构建树形结构
     * @param list 原始数据
     * @param useLevel 是否使用级别
     * @return 菜单列表
     */
    public static List<SysMenu> makeTree(List<SysMenu> list, Boolean useLevel) {
        // 构建一个Map,把所有原始数据的ID作为Key,原始数据对象作为VALUE
        Map<String, SysMenu> dtoMap = new LinkedHashMap<>();
        for (SysMenu node : list) {
            // 原始数据对象为Node，放入dtoMap中。
            String id = node.getId().toString();
            if (useLevel ? (StrUtil.isNotBlank(node.getLevel()) && !node.getLevel().equals("FUNC")) : true) {
                dtoMap.put(id, node);
            }
        }

        List<SysMenu> result = new ArrayList<>();
        for (Map.Entry<String, SysMenu> entry : dtoMap.entrySet()) {
            SysMenu node = entry.getValue();
            Integer parentId = node.getParentId();
            if (dtoMap.get(parentId.toString()) == null) {
                // 如果是顶层节点，直接添加到结果集合中
                result.add(node);
            } else {
                // 如果不是顶层节点，有父节点，然后添加到父节点的子节点中
                SysMenu parent = dtoMap.get(parentId.toString());
                parent.addChild(node);
            }
        }
        List<SysMenu> resultSort = new ArrayList<>();
        MenuUtils.sortList(resultSort, result, 1L);
        return result;
    }
}
