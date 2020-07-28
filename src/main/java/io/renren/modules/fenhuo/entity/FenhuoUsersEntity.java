package io.renren.modules.fenhuo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 系统用户表
 * 
 * @author majinshan
 * @email sunlightcs@gmail.com
 * @date 2020-05-06 09:50:33
 */
@Data
@TableName("fenhuo_users")
public class FenhuoUsersEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户id
	 */
	@TableId
	private Long userid;
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 登录名
	 */
	private String loginname;
	/**
	 * 密码md5
	 */
	private String password;

	/**
	 * 盐
	 */
	private String salt;
	/**
	 * 组织id
	 */
	private String orgid;
	/**
	 * 组织名称
	 */
	private String orgname;
	/**
	 * 推送id
	 */
	private String pushid;
	/**
	 * 真实姓名
	 */
	private String realname;
	/**
	 * 角色id
	 */
	private String roleid;
	/**
	 * 角色名称
	 */
	private String rolename;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 最后登录时间
	 */
	private Date lastLogin;
	/**
	 * 是否删除
	 */
	private Integer isdelete;
	/**
	 * 服务ID
	 */
	private Integer serviceid;
	/**
	 * 单位名称
	 */
	private String companyname;
	/**
	 * 期望服务内容
	 */
	private String servicecontext;
	/**
	 * 联系人
	 */
	private String contacts;
	/**
	 * 联系方式
	 */
	private String contactstel;
	/**
	 * 希望技术支持
	 */
	private String expectsupport;
	/**
	 * 毕业院校
	 */
	private String university;
	/**
	 * 工作经验
	 */
	private String experience;
	/**
	 * 个人技能
	 */
	private String skill;
	/**
	 * 意向
	 */
	private String intention;
	/**
	 * 个人证书
	 */
	private String certificate;
	/**
	 * 补充说明
	 */
	private String remark;
	/**
	 * 性别
	 */
	private String sex;
	/**
	 * 省
	 */
	private String provice;
	/**
	 * 市
	 */
	private String city;
	/**
	 * 区
	 */
	private String area;
	/**
	 * 详细地址
	 */
	private String address;
	/**
	 * 状态
	 */
	private String status;



}
