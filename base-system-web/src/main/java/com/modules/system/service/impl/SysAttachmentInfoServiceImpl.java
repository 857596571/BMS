package com.modules.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.common.service.BaseServiceImpl;
import com.modules.system.entity.SysAttachmentInfo;
import com.modules.system.mapper.SysAttachmentInfoMapper;
import com.modules.system.service.SysAttachmentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 系统附件表
 *
 * @author xmh
 * @email 
 * @date 2018-07-29 10:41:23
 */
@Service
public class SysAttachmentInfoServiceImpl extends ServiceImpl<SysAttachmentInfoMapper, SysAttachmentInfo> implements SysAttachmentInfoService {
    @Autowired
    private SysAttachmentInfoMapper sysAttachmentInfoMapper;

}
