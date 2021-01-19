package io.renren.modules.fenhuo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;


/**
 * 项目信息表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-29 16:12:10
 */
@Data
@TableName("fenhuo_projectinfo")
public class FenhuoProjectinfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 项目id
	 */
	@TableId
	private Long projectid;
	/**
	 * 项目名称
	 */
	private String projectname;
	/**
	 * 单位名称
	 */
	private String orgname;
	/**
	 * 单位省
	 */
	private String province;
	/**
	 * 单位市
	 */
	private String city;
	/**
	 * 区
	 */
	private String county;
	/**
	 * 门牌地址
	 */
	private String address;
	/**
	 * 项目创建时间
	 */
	private Date projectcreatetime;
	/**
	 *
	 */
	private String effectivetime;
	/**
	 * 服务内容id
	 */
	private Integer serviceid;
	/**
	 * 服务内容描述
	 */
	private String serviceditemetail;
	/**
	 * 巡检项目id
	 */
	private Integer taskid;
	/**
	 * 巡检名称
	 */
	private String taskname;
	/**
	 * 服务开始时间
	 */
	private Date servicestarttime;
	/**
	 * 服务结束时间
	 */
	private Date serviceendtime;
	/**
	 * 项目负责人id
	 */
	private String headid;
	/**
	 * 项目负责人
	 */
	private String headname;
	/**
	 * 项目负责人联系方式
	 */
	private String headmobile;
	/**
	 * 
	 */
	private String partyaid;
	/**
	 * 
	 */
	private String partyaname;
	/**
	 * 
	 */
	/**
	 * 项目维护人id
	 */
	private String servicemid;


	/**
	 * 项目维护人姓名
	 */
	private String servicemname;



	private String creater;
	/**
	 * 审批状态 101未审核 102审核未通过 103激活申请 104激活 105关闭申请 106关闭
	 */
	private Integer auditstatus;

	/**
	 * 审批状态名称
	 */
	private String auditname;
	/**
	 * 删除标志
	 */
	private Integer isdelete;

	/**
	 * 项目是否激活：0未激活、1激活
	 */
	private Integer isactive;

	/**
	 *
	 */
	private String fileurl;
	/**
	 * 备注
	 */
	private String projectmemo;
	/**
	 * 
	 */
	private String log;

	/**
	 * 原项目负责人
	 */
	private String oldheadid;
	/**
	 * 原项目负责人名字
	 */
	private String oldheadname;

	/**
	 * 项目类型
	 */
	private String projectype;

	/**
	 * 项目类型名称
	 */
	private Integer projectypeid;

	/**
	 * 最近一次关闭时间
	 */
	private Date recentactivetime;

	/**
	 * 最近一次激活时间
	 */
	private Date recentclosetime;


	private Integer alerttime;

}
