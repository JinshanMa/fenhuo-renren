/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.app.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import reactor.util.annotation.Nullable;

import javax.validation.constraints.NotBlank;

/**
 * 注册表单
 *
 * @author Mark sunlightcs@gmail.com
 */
@Data
@ApiModel(value = "注册表单")
public class RegisterForm {
    @ApiModelProperty(value = "手机号")
    @NotBlank(message="手机号不能为空")
    private String mobile;



    @ApiModelProperty(value = "密码")
    @NotBlank(message="密码不能为空")
    private String password;

    @Nullable
    private String valid;

    /**
     * 真实姓名
     */
    @Nullable
    private String realname;
    /**
     * 角色id
     */
    @Nullable
    private String roleid;
    /**
     * 角色名称
     */
    @Nullable
    private String rolename;
    /**
     * 联系方式
     */
    @Nullable
    private String contactstel;
    /**
     * 毕业院校
     */
    @Nullable
    private String university;


    @Nullable
    private String orgname;

    @Nullable
    private String intention;

    @Nullable
    private String companyname;

    /**
     * 工作经验
     */
    @Nullable
    private String experience;
    /**
     * 个人技能
     */
    @Nullable
    private String skill;
//    /**
//     * 意向
//     */
//    private String intention;
    /**
     * 个人证书
     */
    @Nullable
    private String certificate;
    /**
     * 补充说明
     */
    @Nullable
    private String remark;
    /**
     * 性别
     */
    @Nullable
    private String sex;
    /**
     * 省
     */
    @Nullable
    private String provice;
    /**
     * 市
     */
    @Nullable
    private String city;
    /**
     * 区
     */
    @Nullable
    private String area;
    /**
     * 详细地址
     */
    @Nullable
    private String address;

}
