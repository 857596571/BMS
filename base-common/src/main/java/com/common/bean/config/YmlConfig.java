package com.common.bean.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 读取
 */
@Component
@ConfigurationProperties(prefix = "system")
public class YmlConfig<T> {

    /**
     * 属性列表
     */
    private Map<String, Object> yml = new HashMap<>();

    public Map<String, Object> getYml() {
        return yml;
    }

    public void setYaml(Map<String, Object> yaml) {
        this.yml = yaml;
    }

    /**
     * 返回字符串结果
     * @param key
     * @return
     */
    public String getStr(String key) {
        return yml.get(key).toString();
    }

    /**
     * 返回数组结果
     * @param key
     * @return
     */
    public T[] getArr(String key) {
        return (T[]) yml.get(key);
    }

    /**
     * 返回Map结果
     * @param key
     * @return
     */
    public Map<String, T> getMap(String key, T t) {
        return (Map<String, T>) yml.get(key);
    }

    /**
     * 返回List结果
     * @param key
     * @return
     */
    public List<T> getList(String key) {
        return (List<T>) yml.get(key);
    }

}
