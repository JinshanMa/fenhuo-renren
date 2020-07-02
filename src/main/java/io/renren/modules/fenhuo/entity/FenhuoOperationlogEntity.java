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
 * @date 2020-05-28 16:03:28
 */
@Data
@TableName("fenhuo_operationlog")
public class FenhuoOperationlogEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long logid;
	/**
	 * 
	 */
	private Date opdatetime;
	/**
	 * 操作人员id
	 */
	private Long opersonid;
	/**
	 * 操作人真实姓名
	 */
	private String opersoname;
	/**
	 * 操作名称
	 */
	private String opname;
	/**
	 * 操作的项目id
	 */
	private Long projectid;
	/**
	 * 操作项目名称
	 */
	private String projectname;

	/**
	 * 是否删除，1删除，2未删除
	 */
	private Integer isdelete;

}
