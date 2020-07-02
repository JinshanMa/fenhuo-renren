package io.renren.modules.fenhuo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 故障申报表
 * 
 * @author majinshan
 * @email sunlightcs@gmail.com
 * @date 2020-05-29 23:42:15
 */
@Data
@TableName("fenhuo_fault")
public class FenhuoFaultEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 故障ID
	 */
	@TableId
	private String faultid;
	/**
	 * 项目ID
	 */
	private String projectid;
	/**
	 * 项目名称
	 */
	private String projectname;
	/**
	 * 项目IP
	 */
	private String ip;
	/**
	 * 设备名
	 */
	private String equipment;
	/**
	 * 故障类型ID
	 */
	private Integer faulttype;

	/**
	 * 故障类型名称
	 */
	private String faulttypename;

	/**
	 * 故障问题描述
	 */
	private String faultdesc;
	/**
	 * 故障开始时间
	 */
	private Date starttime;
	/**
	 * 当前网络状态
	 */
	private String networkstatus;
	/**
	 * 故障状态(1 创建成功，2 已取消，3 已解决)
	 */
	private Integer faultstatus;


	private String faultstatustxt;
	/**
	 * 申报人ID
	 */
	private String declarer;


	/**
	 * 申报人名称
	 */
	private String declarername;

	/**
	 * 维护计划
	 */
	private String plan;
	/**
	 * 项目负责人
	 */
	private String handlerid;

	/**
	 * 项目负责人名称
	 */
	private String handlername;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 创建时间
	 */
	private Date createtime;
	/**
	 * 
	 */
	private String declartype;

}
