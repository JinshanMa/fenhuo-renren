package io.renren.modules.fenhuo.dao;

import io.renren.modules.fenhuo.entity.FenhuoUsersEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.sys.entity.SysUserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 系统用户表
 * 
 * @author majinshan
 * @email sunlightcs@gmail.com
 * @date 2020-05-06 09:50:33
 */
@Mapper
public interface FenhuoUsersDao extends BaseMapper<FenhuoUsersEntity> {
    /**
     * 根据用户名，查询烽火用户
     */
    FenhuoUsersEntity sysOverQueryByUserName(String username);


    /**
     * 查询烽火用户的所有权限
     * @param userId  用户ID
     */
    List<String> queryFenhuoAllPerms(Long userId);


    /**
     * 查询烽火用户的所有菜单ID
     */
    List<Long> queryAllMenuId(Long userId);

}
