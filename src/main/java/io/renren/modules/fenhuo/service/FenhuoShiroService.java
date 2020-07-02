package io.renren.modules.fenhuo.service;

import io.renren.modules.fenhuo.entity.FenhuoUserTokenEntity;
import io.renren.modules.fenhuo.entity.FenhuoUsersEntity;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.entity.SysUserTokenEntity;

import java.util.Set;

public interface FenhuoShiroService {


    FenhuoUserTokenEntity queryByToken(String token);

    /**
     * 根据用户ID，查询用户
     * @param userId
     */
    FenhuoUsersEntity queryUser(Long userId);


    /**
     * 获取用户权限列表
     */
    Set<String> getFenhuoUserPermissions(long userId);

}
