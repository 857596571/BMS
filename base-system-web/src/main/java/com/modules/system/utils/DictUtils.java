package com.modules.system.utils;

import com.modules.system.entity.SysDict;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 字典工具类
 */
public class DictUtils {

    /**
     * 字典排序
     * @param list       目标字典列表
     * @param sourceList 原始字典列表
     * @param parentId   父级编号
     */
    public static void sortList(List<SysDict> list, List<SysDict> sourceList, String parentId) {
        sourceList.stream()
                .filter(dict -> dict.getParentId() != null && dict.getParentId().toString().equals(parentId))
                .forEach(dict -> {
                    list.add(dict);
                    // 判断是否还有子节点, 有则继续获取子节点
                    sourceList.stream()
                            .filter(child -> child.getParentId() != null && child.getParentId().toString().equals(dict.getId()))
                            .peek(child -> sortList(list, sourceList, dict.getId()))
                            .findFirst();
                });
    }

    /**
     * 构建树形结构
     * @param list 原始数据
     * @return 字典列表
     */
    public static List<SysDict> makeTree(List<SysDict> list) {
        // 构建一个Map,把所有原始数据的ID作为Key,原始数据对象作为VALUE
        Map<String, SysDict> dtoMap = new LinkedHashMap<>();
        for (SysDict node : list) {
            // 原始数据对象为Node，放入dtoMap中。
            dtoMap.put(node.getId(), node);
        }

        List<SysDict> result = new ArrayList<>();
        for (Map.Entry<String, SysDict> entry : dtoMap.entrySet()) {
            SysDict node = entry.getValue();
            Integer parentId = node.getParentId();
            if (dtoMap.get(parentId.toString()) == null) {
                // 如果是顶层节点，直接添加到结果集合中
                result.add(node);
            } else {
                // 如果不是顶层节点，有父节点，然后添加到父节点的子节点中
                SysDict parent = dtoMap.get(parentId.toString());
                parent.addChild(node);
            }
        }
        List<SysDict> resultSort = new ArrayList<>();
        DictUtils.sortList(resultSort, result, "0");
        return result;
    }
}
