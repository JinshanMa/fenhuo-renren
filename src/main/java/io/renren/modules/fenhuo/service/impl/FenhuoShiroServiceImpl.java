package io.renren.modules.fenhuo.service.impl;

import io.renren.modules.fenhuo.dao.FenhuoUserTokenDao;
import io.renren.modules.fenhuo.dao.FenhuoUsersDao;
import io.renren.modules.fenhuo.entity.FenhuoUserTokenEntity;
import io.renren.modules.fenhuo.entity.FenhuoUsersEntity;
import io.renren.modules.fenhuo.service.FenhuoShiroService;
import io.renren.modules.fenhuo.service.FenhuoUserTokenService;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.entity.SysUserTokenEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class FenhuoShiroServiceImpl implements FenhuoShiroService {

    @Autowired
    FenhuoUserTokenDao fenhuoUserTokenDao;

    @Autowired
    FenhuoUsersDao fenhuoUsersDao;
    @Override
    public FenhuoUserTokenEntity queryByToken(String token) {
        return fenhuoUserTokenDao.queryByToken(token);
    }

    @Override
    public FenhuoUsersEntity queryUser(Long userId) {
        return fenhuoUsersDao.selectById(userId);
    }

    @Override
    public Set<String> getFenhuoUserPermissions(long userId) {
        List<String> permsList = fenhuoUsersDao.queryFenhuoAllPerms(userId);
        //用户权限列表
        Set<String> permsSet = new HashSet<>();
        for(String perms : permsList){
            if(StringUtils.isBlank(perms)){
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }
        return permsSet;
    }
}
