package io.renren.modules.fenhuo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author majinshan
 * @email sunlightcs@gmail.com
 * @date 2020-05-12 21:40:25
 */
@Data
@TableName("fenhuo_user_sys_role")
public class FenhuoUserSysRoleEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 烽火用户ID
	 */
	private Long userId;
	/**
	 * 系统角色ID
	 */
	private Long roleId;

}
