/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.controller;

import io.renren.modules.fenhuo.entity.FenhuoUsersEntity;
import io.renren.modules.sys.entity.SysUserEntity;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller公共组件
 *
 * @author Mark sunlightcs@gmail.com
 */
public abstract class AbstractController {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	protected Object getUser() {
//		 if( instanceof SysUserEntity){
//			 return (SysUserEntity)SecurityUtils.getSubject().getPrincipal();
//		 }else{
//			 return (FenhuoUsersEntity)SecurityUtils.getSubject().getPrincipal();
//		 }
		return SecurityUtils.getSubject().getPrincipal();
	}

	protected Long getUserId() {
		Object gettedUser = getUser();
		if( gettedUser instanceof SysUserEntity)
			return ((SysUserEntity)gettedUser).getUserId();
		else
			return ((FenhuoUsersEntity)gettedUser).getUserid();
	}
}
