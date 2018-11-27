package com.modules.upms.model.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 用户角色表
 * </p>
 *
 * @author lengleng
 * @since 2017-10-29
 */
@Data
@TableName("sys_user_role")
public class SysUserRole extends Model<SysUserRole> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId(type = IdType.INPUT)
	private Integer userId;
    /**
     * 角色ID
     */
	@TableId(type = IdType.INPUT)
	private Integer roleId;


	@Override
	protected Serializable pkVal() {
		return this.userId;
	}

	@Override
	public String toString() {
		return "SysUserRole{" +
			", userId=" + userId +
			", roleId=" + roleId +
			"}";
	}
}
