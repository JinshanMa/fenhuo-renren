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
 * @date 2020-06-22 11:06:23
 */
@Data
@TableName("fenhuo_pushlog")
public class FenhuoPushlogEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 
	 */
	private Long projectid;
	/**
	 *
	 */
	private String projectname;
	/**
	 * 
	 */
	private String pushid;
	/**
	 * 
	 */
	private String pushtitle;
	/**
	 * 
	 */
	private String pushtxt;
	/**
	 * 
	 */
	private Date pushtime;

	private Long userid;

	/**
	 * 推送类型  1.项目推送   2.故障推送   3.zabbix推送
	 */
	private Integer pushtype;

}
