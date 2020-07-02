/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.oauth2;

import io.renren.modules.fenhuo.entity.FenhuoUserTokenEntity;
import io.renren.modules.fenhuo.entity.FenhuoUsersEntity;
import io.renren.modules.fenhuo.service.FenhuoShiroService;
import io.renren.modules.fenhuo.service.FenhuoZabbixhostService;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.entity.SysUserTokenEntity;
import io.renren.modules.sys.service.ShiroService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 认证
 *
 * @author Mark sunlightcs@gmail.com
 */
@Component
public class OAuth2Realm extends AuthorizingRealm {
    @Autowired
    private ShiroService shiroService;

    @Autowired
    private FenhuoShiroService fenhuoShiroService;

    @Autowired
    private FenhuoZabbixhostService zabbixhostService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof OAuth2Token;
    }

    /**
     * 授权(验证权限时调用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Object userObject = principals.getPrimaryPrincipal();
        if(userObject instanceof SysUserEntity){
            SysUserEntity user = (SysUserEntity)principals.getPrimaryPrincipal();
            Long userId = user.getUserId();
            //用户权限列表
            Set<String> permsSet = shiroService.getUserPermissions(userId);

            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            info.setStringPermissions(permsSet);
            return info;
        }else{
            FenhuoUsersEntity user = (FenhuoUsersEntity)principals.getPrimaryPrincipal();
            Long userId = user.getUserid();
            Set<String> permsSet = fenhuoShiroService.getFenhuoUserPermissions(userId);
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            info.setStringPermissions(permsSet);
            return info;
        }



    }

    /**
     * 认证(登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String accessToken = (String) token.getPrincipal();

        //根据accessToken，查询用户信息
        SysUserTokenEntity tokenEntity = shiroService.queryByToken(accessToken);
        //token失效

        FenhuoUserTokenEntity fenhuoUserToken = null;
        if(tokenEntity == null){
            fenhuoUserToken = fenhuoShiroService.queryByToken(accessToken);
            if(fenhuoUserToken == null || fenhuoUserToken.getExpireTime().getTime() < System.currentTimeMillis()){
                throw new IncorrectCredentialsException("token失效，请重新登录");
            }

            //查询用户信息
            FenhuoUsersEntity user = fenhuoShiroService.queryUser(fenhuoUserToken.getUserId());
            //账号锁定
            if(user.getStatus() == "0"){
                throw new LockedAccountException("账号已被锁定,请联系管理员");
            }

            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, accessToken, getName());
            return info;
        }else{
            if(tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()){
                throw new IncorrectCredentialsException("token失效，请重新登录");
            }

            //查询用户信息
            SysUserEntity user = shiroService.queryUser(tokenEntity.getUserId());
            //账号锁定
            if(user.getStatus() == 0){
                throw new LockedAccountException("账号已被锁定,请联系管理员");
            }

            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, accessToken, getName());
            return info;
        }

    }
}
