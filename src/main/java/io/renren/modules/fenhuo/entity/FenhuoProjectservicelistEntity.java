package io.renren.modules.fenhuo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 项目维护人员表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-29 16:12:09
 */
@Data
@TableName("fenhuo_projectservicelist")
public class FenhuoProjectservicelistEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 项目id
	 */
	private String projectid;
	/**
	 * 维护人员id
	 */
	private String servicemid;
	/**
	 * 维护人员姓名
	 */
	private String servicername;
	/**
	 * 维护人员电话
	 */
	private String servicermobile;
	/**
	 * 维护人员推送id
	 */
	private String servicerpushid;
	/**
	 * 维护人员地址
	 */
	private String serviceraddress;
	/**
	 * 角色id
	 */
	private String roleid;
	/**
	 * 角色名称
	 */
	private String rolename;
	/**
	 * 备注
	 */
	private String servicermemo;

}
