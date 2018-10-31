package com.common.datasource;

public enum DataSourceEnum {

    DB1("db1", "mysql库1"), DB2("db2", "mysql库2");

    private String value;
    private String desc;


    DataSourceEnum(String value) {
        this.value = value;
    }

    DataSourceEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
}