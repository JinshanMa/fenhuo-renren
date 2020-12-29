/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.app.controller;


import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.app.annotation.Login;
import io.renren.modules.app.form.LoginForm;
import io.renren.modules.app.utils.JwtUtils;
import io.renren.modules.fenhuo.entity.FenhuoUsersEntity;
import io.renren.modules.fenhuo.service.FenhuoUserTokenService;
import io.renren.modules.fenhuo.service.FenhuoUsersService;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysUserService;
import io.renren.modules.sys.service.SysUserTokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * APP登录授权
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("/app")
@Api("APP登录接口")
public class AppLoginController {
    @Autowired
    private FenhuoUsersService fenhuoUsersService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    FenhuoUserTokenService fenhuoUserTokenService;

    @Autowired
    SysUserService sysUserService;

    @Autowired
    SysUserTokenService sysUserTokenService;


    public static final String USER_KEY = "userId";
    private static final String IS_SYS_USER = "isSysUser";
    /**
     * 登录
     */
    @PostMapping("login")
    @ApiOperation("登录")
    public R login(HttpServletRequest request,@RequestBody LoginForm form){


        //用户信息
        SysUserEntity user = sysUserService.queryByUserName(form.getMobile());
        ///////////////////此处处理账户不是系统用户的步骤，目前挑战到烽火账户下处理
        FenhuoUsersEntity fenhuoUsers = null;
        if(user == null){
//            fenhuoUsers = fenhuoUsersService.sysOverQueryByUserName(form.getMobile());

//            ///////////////////此处处理账户不是系统用户的步骤---end
//            //账号不存在、密码错误
//
//            /// 添加烽火账户的检测
//            if( fenhuoUsers == null || !fenhuoUsers.getPassword().equals(new Sha256Hash(form.getPassword(), fenhuoUsers.getSalt()).toHex())) {
//                return R.error("账号或密码不正确");
//            }
//
//            //账号锁定
//            if(fenhuoUsers.getStatus() == "0"){
//                return R.error("账号已被锁定,请联系管理员");
//            }
//
//            //生成token，并保存到数据库
//            R r = fenhuoUserTokenService.createToken(fenhuoUsers.getUserid());
//            return r;

            //表单校验
            ValidatorUtils.validateEntity(form);

            //用户登录
            fenhuoUsers = fenhuoUsersService.login(form);
            if (fenhuoUsers.getStatus().equals("0")){
                return R.error(500,"账号正在审核中");
            }

            if (fenhuoUsers.getIsdelete() == 1){
                return R.error(500,"账号异常");
            }

            //生成token
            String token = jwtUtils.generateToken(fenhuoUsers.getUserid());

            //生成token，并保存到数据库
            //R r = fenhuoUserTokenService.createToken(user.getUserid());

            Map<String, Object> map = new HashMap<>();
            map.put("token", token);
            map.put("expire", jwtUtils.getExpire());
            map.put("user", fenhuoUsers);
            map.put("admin",false);

            //0表示非系统用户
            request.setAttribute(IS_SYS_USER,"0");

            return R.ok(map);


        }else{
            if(!user.getPassword().equals(new Sha256Hash(form.getPassword(), user.getSalt()).toHex())) {
                return R.error("账号或密码不正确");
            }

            //账号锁定
            if(user.getStatus() == 0) {
                return R.error("账号已被锁定,请联系管理员");
            }

            //生成token
            String token = jwtUtils.generateToken(user.getUserId());

            Map<String, Object> map = new HashMap<>();
            map.put("token", token);
            map.put("expire", jwtUtils.getExpire());
            map.put("user", user);
            map.put("admin",true);
            //1表示系统用户，即管理员账号
            request.setAttribute(IS_SYS_USER,"1");

            return R.ok(map);

//            //生成token，并保存到数据库
//            R r = sysUserTokenService.createToken(user.getUserId());
//            r.put("user",user);
//            r.put("admin",true);
//            return r;
        }


    }

    @Login
    @GetMapping("logout")
    public R logout(@RequestParam String userId,@RequestParam String isSys){

        if (isSys.equals("1")){
            SysUserEntity u = sysUserService.getById(userId);

        }else {

            FenhuoUsersEntity u = fenhuoUsersService.getById(userId);
            if (u != null){
                u.setPushid("");
                fenhuoUsersService.saveOrUpdate(u);
            }

        }

        return R.ok();
    }


}
