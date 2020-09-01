package io.renren.modules.fenhuo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 项目附件
 * 
 * @author majinshan
 * @email sunlightcs@gmail.com
 * @date 2020-08-31 16:19:46
 */
@Data
@TableName("fenhuo_projectfile")
public class FenhuoProjectfileEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long fileid;
	/**
	 * 项目id
	 */
	private Integer projectid;
	/**
	 * 文件名
	 */
	private String filename;
	/**
	 * 文件路径
	 */
	private String filepath;
	/**
	 * 文件类型
	 */
	private String filetype;
	/**
	 * 文件大小
	 */
	private Long filesize;
	/**
	 * 项目文件类型，1，技术文档；2，项目文档；3，故障附件
	 */
	private Long type;

}
