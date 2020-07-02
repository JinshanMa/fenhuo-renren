package io.renren.modules.fenhuo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 角色表
 * 
 * @author majinshan
 * @email sunlightcs@gmail.com
 * @date 2020-04-30 09:15:45
 */
@Data
@TableName("fenhuo_role")
public class FenhuoRoleEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 角色id
	 */
	@TableId
	private String roleid;
	/**
	 * 角色名称
	 */
	private String rolename;
	/**
	 * 角色权限等级
	 */
	private Integer rolelevel;

}
