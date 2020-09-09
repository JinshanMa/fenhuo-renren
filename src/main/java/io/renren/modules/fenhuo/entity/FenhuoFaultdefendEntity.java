package io.renren.modules.fenhuo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 故障维护单
 * 
 * @author majinshan
 * @email sunlightcs@gmail.com
 * @date 2020-05-29 23:41:48
 */
@Data
@TableName("fenhuo_faultdefend")
public class FenhuoFaultdefendEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 维护单ID
	 */
	@TableId
	private Integer defendid;
	/**
	 * 故障ID
	 */
	private String faultid;
	/**
	 * 维护人员ID
	 */
	private String defenderid;
	/**
	 * 维护人员姓名
	 */
	private String defendername;
	/**
	 * 维护人员位置
	 */
	private String defenderposition;
	/**
	 * 定位时间
	 */
	private Date locationtime;
	/**
	 * 维护计划
	 */
	private String plan;
	/**
	 * 维护结果(0 待维护, 1 进行中，2 待检验，3 完成， -1 失败)
	 */
	private Integer defendresult;
	/**
	 * 维护结果描述
	 */
	private String resultdesc;
	/**
	 * 创建人
	 */
	private String createrid;

	/**
	 * 创建人姓名
	 */
	private String creatername;
	/**
	 * 创建时间
	 */
	private Date createtime;
	/**
	 * 维护开始时间
	 */
	private Date defendstarttime;
	/**
	 * 维护结束时间
	 */
	private Date defendendtime;


	/***
	 * 项目id
	 */
	private Long projectid;
	/**
	 * 项目名称
	 */
	private String projectname;


	private String faultdesc;

	/**
	 * 是否删除标志位
	 */
	private int isdelete;

	private String hostname;

	private String hostid;

}
