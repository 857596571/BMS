package com.modules.system.service.impl;

import com.common.service.BaseServiceImpl;
import com.github.pagehelper.PageInfo;
import com.modules.system.entity.SysNotice;
import com.modules.system.mapper.SysNoticeMapper;
import com.modules.system.service.SysNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;



@Service
public class SysNoticeServiceImpl extends BaseServiceImpl<SysNoticeMapper, SysNotice> implements SysNoticeService {
	@Autowired
	private SysNoticeMapper sysNoticeMapper;

    @Override
    public SysNotice queryObject(String id){
        return sysNoticeMapper.get(id);
    }

	@Override
	public List<SysNotice> queryList(Map<String, Object> map){
		return sysNoticeMapper.findList(map);
	}

    @Override
    public PageInfo<SysNotice> queryPage(Paging page, Map<String, Object> query) {
        return null;
    }

    @Override
	public void deleteById(String id){
		sysNoticeMapper.deleteById(id);
	}
	
	@Override
	public void delete(SysNotice sysNotice){
		sysNoticeMapper.delete(sysNotice);
	}
	
}
