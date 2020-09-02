/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.app.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.app.form.RegisterForm;
import io.renren.modules.fenhuo.entity.FenhuoUsersEntity;
import io.renren.modules.fenhuo.service.FenhuoUsersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * 注册
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("/app")
@Api("APP注册接口")
public class AppRegisterController {
    @Autowired
    private FenhuoUsersService fenhuoUsersService;

    @PostMapping("register")
    @ApiOperation("注册")
    public R register(HttpServletRequest httpRequest, @RequestBody RegisterForm form){


        final HttpSession httpSession = httpRequest.getSession();
        Object codeObj = httpSession.getAttribute(form.getMobile());
        if (codeObj == null){
            return R.error(500,"验证码过期");
        }else {
            String code = codeObj.toString();
            if (code.equals("") || code.length() == 0 || !code.equals(form.getValid())) {
                return R.error(500, "验证码无效");
            }
        }

        //表单校验
        ValidatorUtils.validateEntity(form);

        QueryWrapper<FenhuoUsersEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile", form.getMobile());
        FenhuoUsersEntity hasUser = fenhuoUsersService.getOne(queryWrapper);
        if (hasUser != null){
            return R.error(500,"号码已注册");
        }
        FenhuoUsersEntity user = new FenhuoUsersEntity();
        user.setMobile(form.getMobile());
        user.setRoleid(form.getRoleid());
        user.setRealname(form.getRealname());
        user.setPassword(DigestUtils.sha256Hex(form.getPassword()));
        user.setCreateTime(new Date());
        user.setLoginname(form.getMobile());
        user.setArea(form.getArea());
        user.setAddress(form.getAddress());
        user.setOrgname(form.getOrgname());
        user.setProvice(form.getProvice());
        user.setCity(form.getCity());
        user.setIntention(form.getIntention());
        user.setCompanyname(form.getCompanyname());
        user.setContacts(form.getContactstel());
        user.setContactstel(form.getContactstel());
        user.setSex(form.getSex());
        user.setSkill(form.getSkill());
        user.setRemark(form.getRemark());
        user.setUniversity(form.getUniversity());
        user.setExperience(form.getExperience());
        user.setStatus("0");

        fenhuoUsersService.saveFenhuoUser(user);

        return R.ok();
    }
}
