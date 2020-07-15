package io.renren.modules.fenhuo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 项目主机表
 * 
 * @author majinshan
 * @email sunlightcs@gmail.com
 * @date 2020-05-27 11:51:03
 */
@Data
@TableName("fenhuo_zabbixhost")
public class FenhuoZabbixhostEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 项目id
	 */
	private Long projectid;
	/**
	 * 项目名称
	 */
	private String projectname;
	/**
	 * 监控主机id
	 */
	private String hostids;
	/**
	 * 监控主机名称
	 */
	private String zabbixhostnames;
	/**
	 * 
	 */
	private String hostip;

	/**
	 * 主机的操作系统名称
	 */
	private String os;
	/**
	 * zabbixhost 链接
	 */
	private String zbip;
	/**
	 * 
	 */
	private String zbusername;
	/**
	 * 
	 */
	private String zbuserpwd;
	/**
	 * 主机状态
	 */
	private String hoststatus;

	/**
	 * 是否删除
	 */
	private Integer isdeleted;

}
