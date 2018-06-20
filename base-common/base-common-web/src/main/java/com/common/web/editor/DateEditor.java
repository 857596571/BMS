package com.common.web.editor;

import cn.hutool.core.date.DateUtil;

import java.beans.PropertyEditorSupport;

/**
 */
public class DateEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) {
        setValue(DateUtil.parse(text));
    }

}
