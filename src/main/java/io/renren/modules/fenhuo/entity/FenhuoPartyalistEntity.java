package io.renren.modules.fenhuo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 项目甲方负责人表
 * 
 * @author majinshan
 * @email sunlightcs@gmail.com
 * @date 2020-05-26 16:32:02
 */
@Data
@TableName("fenhuo_partyalist")
public class FenhuoPartyalistEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private String id;
	/**
	 * 项目id
	 */
	private String projectid;
	/**
	 * 甲方负责人id
	 */
	private String partyaid;
	/**
	 * 甲方姓名
	 */
	private String partyaname;
	/**
	 * 甲方联系方式
	 */
	private String partyamobile;
	/**
	 * 备注
	 */
	private String remark;

}
