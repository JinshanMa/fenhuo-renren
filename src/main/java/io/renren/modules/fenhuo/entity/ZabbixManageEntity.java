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
 * @date 2021-06-24 13:54:00
 */
@Data
@TableName("zabbix_manage")
public class ZabbixManageEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer zabbixId;
	/**
	 * 
	 */
	private String zabbixName;
	/**
	 * 
	 */
	private String ip;

}
